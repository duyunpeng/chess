package chess.game.landlords.function;

import chess.core.common.Constants;
import chess.game.landlords.command.GamePushType;
import chess.game.landlords.command.PushObject;
import chess.game.landlords.listener.LandlordsClient;
import chess.game.landlords.listener.LandlordsTcpService;
import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.Game;
import chess.game.landlords.mode.Seat;
import chess.game.landlords.mode.push.PushConfirmLandlords;
import chess.game.landlords.mode.push.PushRobLandlords;
import com.alibaba.fastjson.JSONObject;

/**
 * 抢地主超时操作
 * Created by yjh on 16-7-7.
 */
public class RobLandlordsTimeOut extends Thread {

//    private LandlordsClient landlordsClient;
//    private String deskNo;
//    private String userName;
//
//    public RobLandlordsTimeOut() {
//    }
//
//    public RobLandlordsTimeOut(LandlordsClient landlordsClient, String deskNo, String userName) {
//        this.landlordsClient = landlordsClient;
//        this.deskNo = deskNo;
//        this.userName = userName;
//    }
//
//    @Override
//    public void run() {
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        String jsonData = landlordsClient.getRedisService().getCache(deskNo);
//        Game game = JSONObject.parseObject(jsonData, Game.class);
//        if (null == game) {
//            return;
//        }
//
//        for (Seat seat : game.getDesk().getSeats()) {
//            if (seat.getPlayer().getUserName().equals(userName)) {
//                if (seat.getIsRobLandlord() == Action.NONE) {
//                    seat.setIsRobLandlord(Action.FALSE);
//
//                    game.getDesk().setNextPlayer(landlordsClient.getNextPlayer(game, userName));
//                    landlordsClient.getRedisService().addCache(game.getDesk().getDeskNo(), landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                    //是否抢地主
//                    game.getDesk().getSeats().forEach(seat1 -> {
//                        PushObject push = new PushObject();
//                        push.setType(GamePushType.GAME_21004.getValue());//是否抢地主
//                        push.setData(new PushRobLandlords(game, userName));
//                        landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
//                    });
//
//
//                    //如果有两个人不抢地主  那么地主就是叫地主那个玩家的
//                    int count = 0;
//                    for (Seat seat1 : game.getDesk().getSeats()) {
//                        if (seat1.getIsRobLandlord() == Action.FALSE) {
//                            count++;
//                        }
//                    }
//                    if (count == 2) {
//                        for (Seat seat1 : game.getDesk().getSeats()) {
//                            if (seat1.getPlayer().getUserName().equals(game.getDesk().getRandomLandlordPlayer())) {
//                                game.getDesk().setLandlordPlayer(seat1.getPlayer().getUserName());//确认地主
//                                seat1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                seat1.setCards(Game.sort(seat1.getCards()));//排序
//
//                                game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                for (Seat send_seat : game.getDesk().getSeats()) {
//                                    PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                    PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                    landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                }
//
//                                new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                return;
//                            }
//                        }
//                    }
//
//
//                    //判断玩家书否都参与抢地主
//                    count = 0;
//                    for (Seat seat1 : game.getDesk().getSeats()) {
//                        if (seat1.getIsRobLandlord() != Action.NONE) {
//                            count++;
//                        }
//                    }
//                    if (count == 3) {
//                        //玩家操作完了 来确认地主
//                        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
//                            Seat seat_1 = game.getDesk().getSeats().get(i);
//                            if (seat_1.getPlayer().getUserName().equals(game.getDesk().getRandomLandlordPlayer())) {
//                                if (i == 0) {
//                                    seat_1 = game.getDesk().getSeats().get(1);
//                                    if (seat_1.getIsRobLandlord() == Action.TRUE) {
//                                        game.getDesk().setLandlordPlayer(seat_1.getPlayer().getUserName());//确认地主
//                                        seat_1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                        seat_1.setCards(Game.sort(seat_1.getCards()));//排序
//
//                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                        landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                        for (Seat send_seat : game.getDesk().getSeats()) {
//                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                        }
//
//                                        new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                        return;
//                                    }
//
//                                    seat_1 = game.getDesk().getSeats().get(2);
//                                    if (seat_1.getIsRobLandlord() == Action.TRUE) {
//                                        game.getDesk().setLandlordPlayer(seat_1.getPlayer().getUserName());//确认地主
//                                        seat_1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                        seat_1.setCards(Game.sort(seat_1.getCards()));//排序
//
//                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                        landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                        for (Seat send_seat : game.getDesk().getSeats()) {
//                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                        }
//
//                                        new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                        return;
//                                    }
//                                } else if (i == 1) {
//                                    seat_1 = game.getDesk().getSeats().get(2);
//                                    if (seat_1.getIsRobLandlord() == Action.TRUE) {
//                                        game.getDesk().setLandlordPlayer(seat_1.getPlayer().getUserName());//确认地主
//                                        seat_1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                        seat_1.setCards(Game.sort(seat_1.getCards()));//排序
//
//                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                        landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                        for (Seat send_seat : game.getDesk().getSeats()) {
//                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                        }
//
//                                        new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                        return;
//                                    }
//
//                                    seat_1 = game.getDesk().getSeats().get(0);
//                                    if (seat_1.getIsRobLandlord() == Action.TRUE) {
//                                        game.getDesk().setLandlordPlayer(seat_1.getPlayer().getUserName());//确认地主
//                                        seat_1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                        seat_1.setCards(Game.sort(seat_1.getCards()));//排序
//
//                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                        landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                        for (Seat send_seat : game.getDesk().getSeats()) {
//                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                        }
//
//                                        new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                        return;
//                                    }
//                                } else {
//                                    seat_1 = game.getDesk().getSeats().get(0);
//                                    if (seat_1.getIsRobLandlord() == Action.TRUE) {
//                                        game.getDesk().setLandlordPlayer(seat_1.getPlayer().getUserName());//确认地主
//                                        seat_1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                        seat_1.setCards(Game.sort(seat_1.getCards()));//排序
//
//                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                        landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                        for (Seat send_seat : game.getDesk().getSeats()) {
//                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                        }
//
//                                        new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                        return;
//                                    }
//
//                                    seat_1 = game.getDesk().getSeats().get(1);
//                                    if (seat_1.getIsRobLandlord() == Action.TRUE) {
//                                        game.getDesk().setLandlordPlayer(seat_1.getPlayer().getUserName());//确认地主
//                                        seat_1.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
//                                        seat_1.setCards(Game.sort(seat_1.getCards()));//排序
//
//                                        game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
//                                        landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
//
//                                        for (Seat send_seat : game.getDesk().getSeats()) {
//                                            PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
//                                            PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
//                                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
//                                        }
//
//                                        new DoubleTimeOut(landlordsClient, deskNo).start();
//
//                                        return;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    new RobLandlordsTimeOut(landlordsClient, deskNo, game.getDesk().getNextPlayer()).start();
//                }
//                break;
//            }
//        }
//    }
}
