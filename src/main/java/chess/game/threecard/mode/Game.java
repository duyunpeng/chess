package chess.game.threecard.mode;

import chess.application.user.representation.ApiUserRepresentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created date 16-6-21
 * Author pengyi
 */
public class Game {


    private Desk desk; //桌

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    public Game() {
    }

    /**
     * 初始化一场游戏
     *
     * @param user      玩家
     * @param baseScore 基础分
     * @param deskNo    桌号
     */
    public Game(ApiUserRepresentation user, BigDecimal baseScore, String deskNo) {
        List<Integer> seatNo = new ArrayList<>();
        seatNo.add(1);
        seatNo.add(2);
        seatNo.add(3);
        seatNo.add(4);
        seatNo.add(5);
        desk = new Desk();
        desk.setBaseScore(baseScore);
        desk.setDeskNo(deskNo);
        Seat seat = new Seat();
        seat.setSeatNo(seatNo.get(0));
        seatNo.remove(0);
        seat.setReady(false);
        seat.setUserName(user.getUserName());
        seat.setName(user.getName());
        seat.setGold(user.getMoney());
        seat.setAreaString(user.getAreaString());
        if (null != user.getHeadPic()) {
            seat.setHeadPic(user.getHeadPic().getPicPath());
        }
        desk.addSeat(seat);
        desk.setSeatsNo(seatNo);
        desk.setBanker(1);
        desk.setLastScore(new BigDecimal(0));
        desk.setDate(new Date());
        desk.setStartDate(new Date());
    }

    public Integer addSeat(ApiUserRepresentation user) {
        Seat seat = new Seat();
        seat.setSeatNo(desk.getSeatsNo().get(0));
        desk.getSeatsNo().remove(0);
        seat.setUserName(user.getUserName());
        seat.setName(user.getName());
        seat.setGold(user.getMoney());
        seat.setEnd(true);
        seat.setReady(false);
        seat.setAreaString(user.getAreaString());
        if (null != user.getHeadPic()) {
            seat.setHeadPic(user.getHeadPic().getPicPath());
        }
        desk.addSeat(seat);
        return seat.getSeatNo();
    }


    /**
     * 发牌
     */
    public void dealCard() {
        List<Card> allCard = new ArrayList<>();
        allCard.addAll(Card.getAllCard());

        for (Seat seat : desk.getSeats()) {
            List<Card> cards = new ArrayList<>();
            int cardIndex = (int) (Math.random() * allCard.size());
            cards.add(allCard.get(cardIndex));
            allCard.remove(cardIndex);
            cardIndex = (int) (Math.random() * allCard.size());
            cards.add(allCard.get(cardIndex));
            allCard.remove(cardIndex);
            cardIndex = (int) (Math.random() * allCard.size());
            cards.add(allCard.get(cardIndex));
            allCard.remove(cardIndex);
            seat.setCards(cards);
        }
    }


    /**
     * 获取下一家桌号
     */
    public int getNext(int last) {
        boolean a = true;
        int intoLast = last;
        while (a) {
            Integer[] seats = new Integer[desk.getSeats().size()];
            for (int i = 0; i < seats.length; i++) {
                seats[i] = desk.getSeats().get(i).getSeatNo();
            }
            Arrays.sort(seats);
            for (int i = 0; i < seats.length; i++) {
                if (seats[i] == last) {
                    if (i == seats.length - 1) {
                        last = seats[0];
                    } else {
                        last = seats[i + 1];
                        break;
                    }
                }
            }
            if (intoLast == last) {
                return seats[0];
            }
            for (Seat seat : desk.getSeats()) {
                if (seat.getSeatNo() == last && !seat.isEnd()) {
                    return last;
                }
            }
        }

        return last;
    }

    /**
     * 比牌
     *
     * @param myCards    比牌人的牌
     * @param otherCards 被比牌人的牌
     * @return 胜利
     */
    public boolean compare(List<Card> myCards, List<Card> otherCards) {

        CardType myCardType = getCardType(myCards);

        CardType otherCardType = getCardType(otherCards);


        if (myCardType == otherCardType) {
            short[] myValue = new short[]{myCards.get(0).getValue(), myCards.get(1).getValue(), myCards.get(2).getValue()};
            Arrays.sort(myValue);
            short[] otherValue = new short[]{otherCards.get(0).getValue(), otherCards.get(1).getValue(), otherCards.get(2).getValue()};
            Arrays.sort(otherValue);
            if (myCardType.equals(CardType.DOUBLE)) {
                short[] newMyValue;
                if (myValue[0] != myValue[1]) {
                    newMyValue = new short[]{myValue[1], myValue[2], myValue[0]};
                } else {
                    newMyValue = new short[]{myValue[0], myValue[1], myValue[2]};
                }
                short[] newOtherValue;
                if (otherValue[0] != otherValue[1]) {
                    newOtherValue = new short[]{otherValue[1], otherValue[2], otherValue[0]};
                } else {
                    newOtherValue = new short[]{otherValue[0], otherValue[1], otherValue[2]};
                }
                for (int i = 0; i < 3; i++) {
                    if (newMyValue[i] != newOtherValue[i]) {
                        return newMyValue[i] > newOtherValue[i];
                    }
                }
                return false;
            }
            for (int i = 2; i >= 0; i--) {
                if (myValue[i] != otherValue[i]) {
                    return myValue[i] > otherValue[i];
                }
            }
            return false;
        }

        switch (otherCardType) {

            case FLUSH:
                return myCardType == CardType.LEOPARD;

            case SAME_COLOR:
                return myCardType == CardType.LEOPARD || myCardType == CardType.FLUSH;

            case STRAIGHT:
                return !(myCardType == CardType.DOUBLE || myCardType == CardType.SINGLE);

            case DOUBLE:
                return !(myCardType == CardType.SINGLE);

            case SINGLE:
                return true;
            default:
                return false;
        }

    }


    // 判断三张相同
    private boolean isSameThree(List<Card> cards) {
        return cards.get(0).getValue() == cards.get(1).getValue() && cards.get(0).getValue() == cards.get(2).getValue();
    }

    // 判断对子
    private boolean isDouble(List<Card> cards) {
        return cards.get(0).getValue() == cards.get(1).getValue() || cards.get(0).getValue() == cards.get(2).getValue()
                || cards.get(1).getValue() == cards.get(2).getValue();
    }

    // 判断是否是同花
    private boolean isSameColor(List<Card> cards) {
        return cards.get(0).getCardColor() == cards.get(1).getCardColor() && cards.get(0).getCardColor() == cards.get(2).getCardColor();
    }

    // 是否是顺子
    private boolean isConnect(List<Card> cards) {
        short[] values = new short[]{cards.get(0).getValue(), cards.get(1).getValue(), cards.get(2).getValue()};
        Arrays.sort(values);
        if (values[0] == 2 && values[1] == 3 && values[2] == 14) {
            return true;
        }
        return values[0] == values[1] - 1 && values[1] == values[2] - 1;
    }

    public CardType getCardType(List<Card> cards) {
        if (isSameThree(cards)) {
            return CardType.LEOPARD;
        } else if (isSameColor(cards) && isConnect(cards)) {
            return CardType.FLUSH;
        } else if (isSameColor(cards)) {
            return CardType.SAME_COLOR;
        } else if (isConnect(cards)) {
            return CardType.STRAIGHT;
        } else if (isDouble(cards)) {
            return CardType.DOUBLE;
        } else {
            return CardType.SINGLE;
        }
    }
}
