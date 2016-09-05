package chess.game.landlords.function;

import chess.game.landlords.command.GamePushType;
import chess.game.landlords.command.PushObject;
import chess.game.landlords.listener.LandlordsClient;
import chess.game.landlords.listener.LandlordsTcpService;
import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.Game;
import chess.game.landlords.mode.GameStatus;
import chess.game.landlords.mode.Seat;
import chess.game.landlords.mode.push.PushCallLandlords;
import chess.game.landlords.mode.push.PushConfirmLandlords;
import chess.game.landlords.mode.push.PushStartLandlords;

import java.util.Date;

/**
 * 叫地主超时操作
 * Created by yjh on 16-7-7.
 */
public class CallLandlordsTimeOut extends Thread {

    private LandlordsClient landlordsClient;
    private String deskNo;
    private String userName;
    private Date landlordsDate;

    public CallLandlordsTimeOut() {
    }

    public CallLandlordsTimeOut(LandlordsClient landlordsClient, String deskNo, String userName, Date landlordsDate) {
        this.landlordsClient = landlordsClient;
        this.deskNo = deskNo;
        this.userName = userName;
        this.landlordsDate = landlordsDate;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(21000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        String jsonData = landlordsClient.getRedisService().getCache(deskNo);
//        Game game = JSONObject.parseObject(jsonData, Game.class);
        Game game = LandlordsTcpService.games.get(deskNo);
        if (null == game) {
            return;
        }

        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getPlayer().getUserName().equals(userName)) {
                if (seat.getIsCallLandlord() == Action.NONE && landlordsDate.compareTo(game.getDesk().getLandlordsDate()) == 0) {
                    seat.setIsCallLandlord(Action.FALSE);
                    System.out.println(userName + "叫地主超时,不抢");
                    game.getDesk().setNextPlayer(landlordsClient.getNextPlayer(game, userName));
//                    landlordsClient.getRedisService().addCache(game.getDesk().getDeskNo(), landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

                    //是否叫地主
                    game.getDesk().getSeats().forEach(seat1 -> {
                        PushObject push = new PushObject();
                        push.setType(GamePushType.GAME_21003.getValue());//是否叫地主
                        push.setData(new PushCallLandlords(game, seat.getPlayer().getUserName()));
                        push.setCode("success");
                        landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
                    });

                    //如果三个玩家都不叫地主  洗牌重来
                    int callLandlordCount = 0;
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        Seat seat_1 = game.getDesk().getSeats().get(i);
                        if (seat_1.getIsCallLandlord() != Action.NONE && seat_1.getCallLandlordScore() == 0) {
                            callLandlordCount++;
                        }
                    }

                    if (callLandlordCount == 3) {
                        game.dealCard();
                        for (Seat seat_1 : game.getDesk().getSeats()) {
                            seat_1.setIsCallLandlord(Action.NONE);
                        }
                        game.getDesk().setNextPlayer(game.getDesk().getRandomLandlordPlayer());
//                        landlordsClient.getRedisService().addCache(game.getDesk().getDeskNo(), landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                        for (Seat seat_1 : game.getDesk().getSeats()) {
                            PushObject push = new PushObject();
                            push.setType(GamePushType.GAME_21009.getValue());//没人叫地主洗牌重来
                            push.setData(new PushStartLandlords(game, seat_1.getPlayer().getUserName()));
                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(seat_1.getPlayer().getUserName()));
                        }

                        new CallLandlordsTimeOut(landlordsClient, game.getDesk().getDeskNo(), game.getDesk().getRandomLandlordPlayer(), game.getDesk().getLandlordsDate()).start();
                        return;
                    }

                    //三个玩家都操作叫地主了
                    callLandlordCount = 0;
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        Seat seat_1 = game.getDesk().getSeats().get(i);
                        if (seat_1.getIsCallLandlord() != Action.NONE) {
                            callLandlordCount++;
                        }
                    }

                    if (callLandlordCount == 3) {
                        Seat seat_1 = game.getDesk().getSeats().get(0);
                        Seat seat_2 = game.getDesk().getSeats().get(1);
                        Seat seat_3 = game.getDesk().getSeats().get(2);

                        if (seat_1.getCallLandlordScore() > seat_2.getCallLandlordScore() && seat_1.getCallLandlordScore() > seat_3.getCallLandlordScore()) {
                            game.getDesk().getSeats().stream().filter(seat_item -> seat_item.getPlayer().getUserName().equals(seat_1.getPlayer().getUserName())).forEach(seat_item -> {
                                game.getDesk().setLandlordPlayer(seat_item.getPlayer().getUserName());//确认地主
                                seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                                seat.setCards(Game.sort(seat_item.getCards()));//排序

                                game.getDesk().setCallLandlordScore(seat_item.getCallLandlordScore());
                                game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                                game.getDesk().setGameStatus(GameStatus.DOUBLE);
//                                landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

                                //推送确认地主
                                for (Seat send_seat : game.getDesk().getSeats()) {
                                    PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                                    PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                                    landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                                }

                                new DoubleTimeOut(landlordsClient, deskNo).start();
                            });
                            return;
                        }

                        if (seat_2.getCallLandlordScore() > seat_1.getCallLandlordScore() && seat_2.getCallLandlordScore() > seat_3.getCallLandlordScore()) {
                            game.getDesk().getSeats().stream().filter(seat_item -> seat_item.getPlayer().getUserName().equals(seat_2.getPlayer().getUserName()))
                                    .forEach(seat_item -> {
                                        game.getDesk().setLandlordPlayer(seat_item.getPlayer().getUserName());//确认地主
                                        seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                                        seat.setCards(Game.sort(seat_item.getCards()));//排序

                                        game.getDesk().setCallLandlordScore(seat_item.getCallLandlordScore());
                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                                        game.getDesk().setGameStatus(GameStatus.DOUBLE);
//                                landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

                                        //推送确认地主
                                        for (Seat send_seat : game.getDesk().getSeats()) {
                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                                        }

                                        new DoubleTimeOut(landlordsClient, deskNo).start();
                                    });
                            return;
                        }

                        if (seat_3.getCallLandlordScore() > seat_1.getCallLandlordScore() && seat_3.getCallLandlordScore() > seat_2.getCallLandlordScore()) {
                            game.getDesk().getSeats().stream().filter(seat_item -> seat_item.getPlayer().getUserName().equals(seat_3.getPlayer().getUserName()))
                                    .forEach(seat_item -> {
                                        game.getDesk().setLandlordPlayer(seat_item.getPlayer().getUserName());//确认地主
                                        seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                                        seat.setCards(Game.sort(seat_item.getCards()));//排序

                                        game.getDesk().setCallLandlordScore(seat_item.getCallLandlordScore());
                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                                        game.getDesk().setGameStatus(GameStatus.DOUBLE);
//                                landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

                                        //推送确认地主
                                        for (Seat send_seat : game.getDesk().getSeats()) {
                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                                        }

                                        new DoubleTimeOut(landlordsClient, deskNo).start();
                                    });
                            return;
                        }
                    }

                    new CallLandlordsTimeOut(landlordsClient, game.getDesk().getDeskNo(), landlordsClient.getNextPlayer(game, userName),
                            game.getDesk().getLandlordsDate()).start();
                }
                break;
            }
        }
    }
}
