package chess.game.bullfight.take;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.application.user.representation.ApiUserRepresentation;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.command.GameOver;
import chess.game.bullfight.listener.BullfightClient;
import chess.game.bullfight.listener.BullfightTcpService;
import chess.game.bullfight.mode.*;
import chess.game.bullfight.push.GamePushObject;
import chess.game.bullfight.push.PairCardPushObject;
import chess.game.bullfight.push.PushObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by jm
 * Date : 16-6-20.
 */
public class Game {

    int cardTypeMultiple = 0;//牌类型的倍数
    private Desk desk = new Desk();
    private BullfightClient bullfightClient = new BullfightClient();

    public Game() {
    }

    /**
     * 初始化一场游戏
     *
     * @param userList  玩家集合
     * @param baseScore 基础分
     */
    public Game(List<ApiUserRepresentation> userList, int baseScore, String deskNo) {
        desk.setDeskNo(deskNo);
        desk.setBaseScore(baseScore);
        //初始化五个座位
        for (int i = 0; i < 5; i++) {
            Seat seat = new Seat();
            seat.setSeatNo(i);
            seat.setIsExit(Action.FALSE);
            seat.setIsConnect(Action.FALSE);
            seat.setBetYesOrNo(Action.FALSE);
            seat.setIsTheBanker(Action.FALSE);
            desk.getSeats().add(seat);
        }

        for (int i = 0; i < userList.size(); i++) {
            Player player = new Player(userList.get(i), desk.getSeats().get(i));
            desk.getSeats().get(i).setPlayer(player);
            desk.getSeats().get(i).setIsExit(Action.TRUE);
            desk.getSeats().get(i).setIsConnect(Action.TRUE);
        }
    }

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    /**
     * 发牌
     */
    public void dealCard(Game game, RedisService redisService) {
         List<Card> allCard = new ArrayList<>();
        List<Seat> seatList = new ArrayList<>();
        allCard.addAll(Card.ALL_CARD);
        List<Seat> seats = game.getDesk().getSeats();
        if (seats != null) {
            seatList.addAll(seats.stream().filter(seat -> seat.getIsExit().getValue() == 1).collect(Collectors.toList()));
            if (seatList.size() >= 2) {
                for (int i = 0; i < seats.size(); i++) {
                    if (seats.get(i).getIsExit().getValue() == 1) {
                        if (seats.get(i).getIsPlaying() != null) {
                            if (seats.get(i).getIsPlaying().getValue() == 1) {
                                List<Card> cards_i;
                                if (seats.get(i).getCards() == null) {
                                    cards_i = new ArrayList<>();
                                } else {
                                    allCard = game.getDesk().getSurplusCard();
                                    cards_i = seats.get(i).getCards();
                                }
                                for (int j = 0; j < allCard.size(); j++) {
                                    if (cards_i != null) {
                                        if (cards_i.size() == 5) {
                                            break;
                                        }
                                        if ((cards_i.size() == 4 && seats.get(i).getMultiple() != 0) || (cards_i.size() == 4 && seats.get(i).getIsTheBanker().getValue() == 1)) {
                                            //所有的玩家已完成下注，则发最后一张牌
                                            int r = (int) (Math.random() * allCard.size());
                                            cards_i.add(allCard.get(r));
                                            game.getDesk().getSeats().get(i).setCards(cards_i);
                                            PushObject push = new PushObject();
                                            push.setType(GamePushObject.NINE.getValue());
                                            PairCardPushObject pair = new PairCardPushObject();
                                            pair.setCard(allCard.get(r));
                                            pair.setSeatNo(seats.get(i).getSeatNo());
                                            push.setPairCardPushObject(pair);
                                            if (seats.get(i).getIsConnect().getValue() == 1) {
                                                bullfightClient.send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(desk.getSeats().get(i).getPlayer().getUserName()));
                                            }
                                            allCard.remove(r);
                                        } else {
                                            //给每个玩家发前4张牌
                                            if (cards_i.size() < 4 && seats.get(i).getMultiple() == 0) {
                                                int r = (int) (Math.random() * allCard.size());
                                                seats.get(i).setIsOldOrNew(Action.TRUE);
                                                redisService.addCache(game.getDesk().getDeskNo(), JSON.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                                                cards_i.add(allCard.get(r));
                                                allCard.remove(r);
                                            } else if (cards_i.size() == 4 && seats.get(i).getMultiple() == 0) {
                                                break;
                                            }
                                        }
                                    }
                                }
                                game.getDesk().setSurplusCard(allCard);
                                assert cards_i != null;
                                //推送前4张牌
                                if (cards_i.size() == 4) {
                                    game.getDesk().getSeats().get(i).setCards(cards_i);
                                    PushObject push = new PushObject();
                                    push.setType(GamePushObject.NINE.getValue());
                                    PairCardPushObject pair = new PairCardPushObject();
                                    pair.setCards(cards_i);
                                    pair.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                                    push.setPairCardPushObject(pair);
                                    if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 1) {
                                        bullfightClient.send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取牌型
     *
     * @return CardType
     */
    public CardType getCardType(List<Card> user_cards) {
        int Three_card_sum = 0;//任意的三张牌的和
        int All_card_sum = 0;//该玩家所有牌的和
        int Two_card_sum = 0;//剩下两张牌的和
        List<Integer> Three_list = new ArrayList<>();
        List<Integer> Two_list = new ArrayList<>();
        boolean b = false;
        //判断是否为5小牛
        for (Card card : user_cards) {
            All_card_sum += card.getValue();
            if (card.getValue() <= 5) {
                b = true;
            } else {
                b = false;
                All_card_sum = 0;
                break;
            }
        }
        if (b && All_card_sum <= 10) {
            return CardType.FiveLittleCattle;//五小牛
        }
        //判断炸弹
        int repeatCardCount = 0;//重复牌的张数
        for (Card card : user_cards) {
            for (Card user_card : user_cards) {
                if (card.getValue() == user_card.getValue()) {
                    repeatCardCount++;
                }
            }
        }
        if (repeatCardCount == 16) {
            return CardType.Bomb;//炸弹
        }
        //金牛
        boolean boo = false;
        for (Card card : user_cards) {
            if (card.getValue() > 10) {
                boo = true;
            } else {
                boo = false;
                break;
            }
        }
        if (boo) {
            return CardType.GoldCattle;//金牛
        }
        //银牛
        int c = 0;
        for (Card card : user_cards) {
            if (card.getValue() > 10) {
                c++;
            }
        }
        if (c == 4) {
            for (int i = user_cards.size() - 1; i >= 0; i--) {
                if (user_cards.get(i).getValue() == 10) {
                    return CardType.SilverCattle;//银牛
                }
            }
        }
        int count = 0;
        //判断为牛几
        for (int i = 0; i < user_cards.size() - 2; i++) {
            if (user_cards.get(i).getValue() > 10) {
                user_cards.get(i).setValue(10);
            }
            for (int j = i + 1; j < user_cards.size() - 1; j++) {
                if (user_cards.get(j).getValue() > 10) {
                    user_cards.get(j).setValue(10);
                }
                for (int l = j + 1; l < user_cards.size(); l++) {
                    if (user_cards.get(l).getValue() > 10) {
                        user_cards.get(l).setValue(10);
                    }
                    if ((user_cards.get(i).getValue() + user_cards.get(j).getValue() + user_cards.get(l).getValue()) % 10 == 0) {
                        Three_card_sum = (user_cards.get(i).getValue() + user_cards.get(j).getValue() + user_cards.get(l).getValue());
                        Three_list.add(Three_card_sum);
                        count++;
                    }
                }
            }
        }
        //无牛
        if (count == 0) {
            return CardType.NoCattle;//无牛
        } else {
            for (Card user_card : user_cards) {
                All_card_sum += user_card.getValue();
            }
            if (All_card_sum % 10 == 0) {
                return CardType.CattleCattle;//牛牛
            }
            if (count >= 2) {
                for (Integer aThree_list : Three_list) {
                    int a = All_card_sum - aThree_list;
                    Two_list.add(a);
                }
                for (Integer aTwo_list : Two_list) {
                    if (Two_card_sum < aTwo_list) {
                        Two_card_sum = aTwo_list;
                    }
                }
            } else {
                Two_card_sum = All_card_sum - Three_card_sum;
            }
            if (Two_card_sum % 10 == 0) {
                return CardType.CattleCattle;//牛牛
            } else if (Two_card_sum % 10 == 9) {
                return CardType.CattleNine;
            } else if (Two_card_sum % 10 == 8) {
                return CardType.CattleEight;
            } else if (Two_card_sum % 10 == 7) {
                return CardType.CattleSeven;
            } else if (Two_card_sum % 10 == 6) {
                return CardType.CattleSix;
            } else if (Two_card_sum % 10 == 5) {
                return CardType.CattleFive;
            } else if (Two_card_sum % 10 == 4) {
                return CardType.CattleFour;
            } else if (Two_card_sum % 10 == 3) {
                return CardType.CattleThree;
            } else if (Two_card_sum % 10 == 2) {
                return CardType.CattleTwo;
            } else if (Two_card_sum % 10 == 1) {
                return CardType.CattleOne;
            }
        }
        return null;
    }

    /**
     * 比牌(庄家和闲家比牌)
     */
    public void compare(String deskNo, IBullfightRecordAppService bullfightRecordAppService, RedisService redisService) {
        CardType cardType = null;
        List<GameOver> over = new ArrayList<>();
        int bankerMultiple = 0;//庄家抢庄的倍数
        String jsonData = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonData, Game.class);
        int baseScore = game.getDesk().getBaseScore();//基础分
        //判断庄家是否存在，并查看当前人数是否可以继续游戏
        boolean isExitBanker = false;
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getIsTheBanker().getValue() == 1) {
                isExitBanker = true;
                break;
            }
        }
        //得到这场游戏的玩家
        int count = 0;
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getIsExit().getValue() == 1) {
                count++;
            }
        }
        //存在庄家并且该场游戏的人数>2,则游戏继续
        if (isExitBanker && count > 1) {
            List<Seat> seats = game.getDesk().getSeats();
            for (Seat seat : seats) {
                if (seat.getIsTheBanker().getValue() == 1)//获取庄家
                {
                    List<Card> cards = seat.getCards();//庄家的牌
                    cardType = this.getCardType(cards);
                    bankerMultiple = seat.getBankerMultiple();//庄家的抢庄倍数
                    if (bankerMultiple == 0) {
                        bankerMultiple = 1;
                    }
                }
            }
            //得到每个闲家
            for (int j = 0; j < seats.size(); j++) {
                if (game.getDesk().getSeats().get(j).getIsPlaying() != null) {
                    if (game.getDesk().getSeats().get(j).getIsPlaying().getValue() == 1) {
                        if (seats.get(j).getIsExit().getValue() == 1) {
                            if (seats.get(j).getIsTheBanker().getValue() == 2) {
                                CardType cardTypePlayer = this.getCardType(seats.get(j).getCards());//闲家的牌的类型
                                assert cardType != null;
                                if ((cardType.getValue() > cardTypePlayer.getValue()) || (Objects.equals(cardType.getValue(), cardTypePlayer.getValue()))) {
                                    //庄家胜
                                    seats.get(j).setWinOrLose(Boolean.FALSE);
                                } else if (cardType.getValue() < cardTypePlayer.getValue()) {
                                    //闲家胜
                                    seats.get(j).setWinOrLose(Boolean.TRUE);
                                }
                            }
                        }
                    }
                }
            }
            int a = 0;// 庄家输赢的金币
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                    if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 2) {
                        if (game.getDesk().getSeats().get(i).getWinOrLose() != null) {
                            if (game.getDesk().getSeats().get(i).getWinOrLose()) {
                                //闲家赢
                                int multiple = game.getDesk().getSeats().get(i).getMultiple();//闲家下注的倍数
                                GameOver gameOver = new GameOver();
                                gameOver.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                                CardType cardTypePlayer = this.getCardType(game.getDesk().getSeats().get(i).getCards());
                                //计算牌型
                                this.compute(cardTypePlayer);
                                gameOver.setCardType(cardTypePlayer.getValue());
                                gameOver.setScore(new BigDecimal(bankerMultiple * baseScore * cardTypeMultiple * multiple));
                                over.add(gameOver);
                                a = a - (bankerMultiple * baseScore * cardTypeMultiple * multiple);
                            } else {
                                //闲家输
                                int multiple = game.getDesk().getSeats().get(i).getMultiple();//闲家下注的倍数
                                GameOver gameOver = new GameOver();
                                gameOver.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                                CardType cardTypePlayer = this.getCardType(game.getDesk().getSeats().get(i).getCards());
                                //计算牌型
                                this.compute(cardTypePlayer);
                                gameOver.setCardType(cardTypePlayer.getValue());
                                gameOver.setScore(new BigDecimal(-bankerMultiple * baseScore * cardTypeMultiple * multiple));
                                over.add(gameOver);
                                a += bankerMultiple * baseScore * cardTypeMultiple * multiple;
                            }
                        }
                    }
                }
            }
            //庄家的总分结算
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                    if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 1) {
                        GameOver gameOver = new GameOver();
                        CardType cardTypePlayer = this.getCardType(game.getDesk().getSeats().get(i).getCards());
                        gameOver.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                        gameOver.setScore(new BigDecimal(a));
                        gameOver.setCardType(cardTypePlayer.getValue());
                        over.add(gameOver);
                    }
                }
            }
            //游戏账目结算
            over = bullfightRecordAppService.gameSettlement(over, game);
            //给每个玩家推送游戏结果
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                    if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 1) {
                        PushObject push = new PushObject();
                        push.setType(GamePushObject.TEN.getValue());
                        push.setGameOverPushType(over);
                        bullfightClient.send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                    }
                }
            }
        }
    }


    /***
     * cardTypePlayer 牌型
     * cardTypeMultiple  牌型的倍数
     * 计算牌型 重复方法提取
     */
    private void compute(CardType cardTypePlayer) {
        if (cardTypePlayer.getValue() <= 6) {
            cardTypeMultiple = 1;
        } else if (cardTypePlayer.getValue() <= 9) {
            cardTypeMultiple = 2;
        } else if (cardTypePlayer.getValue() == 10) {
            cardTypeMultiple = 3;
        } else if (cardTypePlayer.getValue() == 11) {
            cardTypeMultiple = 4;
        } else if (cardTypePlayer.getValue() == 12) {
            cardTypeMultiple = 5;
        } else if (cardTypePlayer.getValue() == 13) {
            cardTypeMultiple = 7;
        } else if (cardTypePlayer.getValue() == 14) {
            cardTypeMultiple = 10;
        }
    }
}
