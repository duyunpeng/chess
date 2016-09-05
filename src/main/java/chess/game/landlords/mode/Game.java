package chess.game.landlords.mode;

import chess.application.user.representation.ApiUserRepresentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by YJH
 * Date : 16-6-12.
 */
public class Game {

    private Desk desk = new Desk(); //桌

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
     * @param userList 玩家集合
     */
    public Game(List<ApiUserRepresentation> userList, String deskNo, BigDecimal baseScore) {
        desk.setDeskNo(deskNo);
        desk.setMultiple(new BigDecimal(1));//默认1倍
        desk.setBaseScore(baseScore);//默认10分
        //初始化三个座位
        for (int i = 0; i < 3; i++) {
            Seat seat = new Seat();
            seat.setSeatNo(i);
            desk.getSeats().add(seat);
        }

        for (int i = 0; i < userList.size(); i++) {
            Player player = new Player(userList.get(i));
            desk.getSeats().get(i).setPlayer(player);
            desk.getSeats().get(i).setIsJoin(Action.TRUE);
            desk.getSeats().get(i).setIsReady(Action.TRUE);
        }
    }


    /**
     * 发牌
     */
    public void dealCard() {
        List<Card> allCard = new ArrayList<>();
        allCard.addAll(Card.ALL_CARD);

        //分三副牌
        List<Card> one = new ArrayList<>();
        List<Card> two = new ArrayList<>();
        List<Card> three = new ArrayList<>();

        //随机形成三副牌
        for (int i = 0; i < 51; i++) {
            int cardIndex = (int) (Math.random() * allCard.size());
            if (one.size() != 17) {
                one.add(allCard.get(cardIndex));
                allCard.remove(cardIndex);
                continue;
            }
            if (two.size() != 17) {
                two.add(allCard.get(cardIndex));
                allCard.remove(cardIndex);
                continue;
            }
            if (three.size() != 17) {
                three.add(allCard.get(cardIndex));
                allCard.remove(cardIndex);
            }
        }

        //给用户发牌 并排序
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    this.desk.getSeats().get(i).setCards(sort(one));
                    break;
                case 1:
                    this.desk.getSeats().get(i).setCards(sort(two));
                    break;
                case 2:
                    this.desk.getSeats().get(i).setCards(sort(three));
                    break;
            }
        }
        //最后留三张牌
        this.desk.setLandlordCard(sort(allCard));

        //随机选出地主
        int landlordIndex = (int) (Math.random() * 3);
//        int landlordIndex = 0;
        desk.setRandomLandlordPlayer(this.getDesk().getSeats().get(landlordIndex).getPlayer().getUserName());
    }

    /**
     * 初始化上一轮牌
     */
    public void initLastRoundCard() {
        List<LastRoundCard> lastRoundCards = new ArrayList<>();
//        for (Seat seat : this.desk.getSeats()) {
//            LastRoundCard lastRoundCard = new LastRoundCard();
//            lastRoundCard.setPlay(seat.getPlayer().getUserName());
//            lastRoundCard.setCardList(new ArrayList<>());
//            lastRoundCards.add(lastRoundCard);
//        }
        desk.setLastRoundCards(lastRoundCards);
    }

    /**
     * 给牌排序
     *
     * @param cards 未排序的牌
     * @return 排序过后的牌
     */
    public static List<Card> sort(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                //点数排序大的往后排
                if (cards.get(i).getValue() > cards.get(j).getValue()) {
                    Card temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
                //花色排序
                if (cards.get(i).getValue() == cards.get(j).getValue()) {
                    Card one = cards.get(i);
                    Card two = cards.get(j);
                    if (one.getCardColor().getValues() > two.getCardColor().getValues()) {
                        cards.set(i, two);
                        cards.set(j, one);
                    }
                }
            }
        }
        return cards;
    }


    /**
     * 判断用户手中是否有此牌
     *
     * @param user  用户
     * @param cards 牌
     * @return 结果
     */
    public boolean checkCards(String user, List<Card> cards) {
        List<Card> haveFound = new ArrayList<>();
        for (Seat seat : desk.getSeats()) {
            if (seat.getPlayer().getUserName().equals(user)) {
                for (Card card : cards) {
                    for (Card card1 : haveFound) {
                        if (card.getValue() == card1.getValue() && card.getCardColor() == card1.getCardColor()) {
                            return false;
                        }
                    }
                    boolean reslut = false;
                    for (Card card1 : seat.getCards()) {
                        if (card.getValue() == card1.getValue() && card.getCardColor() == card1.getCardColor()) {
                            haveFound.add(card1);
                            reslut = true;
                            break;
                        }
                    }
                    if (!reslut) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取牌型
     *
     * @param cards 需要排序的牌
     * @return 排序后的牌
     */
    public CardType getCardType(List<Card> cards) {
        int len = cards.size();

        cards = sort(cards);

        // 当牌数量为1时,单牌
        if (len == 1) {
            return CardType.DANPAI;
        }

        // 当牌数量为2时,可能是对牌和火箭
        if (len == 2) {
            if (cards.get(0).getValue() == 16 && cards.get(1).getValue() == 16) {
                return CardType.HUOJIAN;
            }
            if (cards.get(0).getValue() == cards.get(1).getValue()) {
                return CardType.DUIZI;
            }
        }

        // 当牌数为3时,三张
        if (len == 3) {
            if (cards.get(0).getValue() == cards.get(1).getValue() && cards.get(1).getValue() == cards.get(2).getValue()) {
                return CardType.SANZHANG;
            }
        }

        // 当牌数为4时,可能是三带一或炸弹
        if (len == 4) {
            if (cards.get(0).getValue() == cards.get(2).getValue() || cards.get(1).getValue() == cards.get(3).getValue()) {
                if (cards.get(0).getValue() == cards.get(3).getValue()) {
                    return CardType.ZHADAN;
                } else {
                    return CardType.SANDAIYI;
                }
            }
        }

        // 当牌数大于等于5时,判断是不是单顺
        if (len >= 5) {
            if (isDanShun(cards, CardType.SUNZI)) {
                return CardType.SUNZI;
            }
        }

        // 当牌数等于5时，三带一对
        if (len == 5) {
            if (cards.get(0).getValue() == cards.get(2).getValue() && cards.get(3).getValue() == cards.get(4).getValue()) {
                return CardType.SANDAIYI;
            }
            if (cards.get(0).getValue() == cards.get(1).getValue() && cards.get(2).getValue() == cards.get(4).getValue()) {
                return CardType.SANDAIYI;
            }
        }

        // 当牌数大于等于6时,判断是不是双顺和三顺
        if (len >= 6) {
            if (isShuangShun(cards)) {
                return CardType.SHUANGSHUN;
            }
            if (isSanShun(cards)) {
                return CardType.SANSHUN;
            }
        }

        // 当牌数为6时,判断四带二
        if (len == 6) {
            if (cards.get(0).getValue() == cards.get(3).getValue() || cards.get(1).getValue() == cards.get(4).getValue()
                    || cards.get(2).getValue() == cards.get(5).getValue()) {
                return CardType.SIDAIER;
            }
        }

        // 当牌数为7时只能是单顺，已判断过
        // 当牌数大于等于8,判断是不是飞机
        if (len >= 8) {
            if (isFeiJi(cards)) {
                return CardType.FEIJI;
            }
        }

        // 如果不是规定牌型,返回错误型
        return CardType.ERROR;
    }


    /**
     * 判断是不是单顺
     *
     * @param cards    牌
     * @param cardType 牌型
     * @return 结果
     */
    private boolean isDanShun(List<Card> cards, CardType cardType) {
        int start = cards.get(0).getValue();
        if (cardType == CardType.SUNZI) {
            // 单顺最小一张不能大于J(11)
            if (start > 11) {
                return false;
            }
        }
        int next;
        for (int i = 1; i < cards.size(); i++) {
            next = cards.get(i).getValue();
            if (next - start != 1) {
                return false;
            }
            start = next;
        }
        return true;
    }

    /**
     * 判断是否双顺
     *
     * @param cards 牌
     * @return 结果
     */
    private boolean isShuangShun(List<Card> cards) {
        int start = cards.get(0).getValue();
        // 双顺最小一张不能大于A
        if (start > 13) {
            return false;
        }
        // 奇数张牌不可能是双顺
        if (cards.size() % 2 != 0) {
            return false;
        }
        int next;
        for (int i = 2; i < cards.size() - 1; i += 2) {
            next = cards.get(i).getValue();
            if (start != cards.get(i - 1).getValue()) {
                return false;
            }
            if (next != cards.get(i + 1).getValue()) {
                return false;
            }
            if (next - start != 1) {
                return false;
            }
            start = next;
        }
        return true;
    }

    /**
     * 判断牌是不是三顺
     *
     * @param cards 牌
     * @return 结果
     */
    private boolean isSanShun(List<Card> cards) {
        int start = cards.get(0).getValue();
        // 三顺最大一张不能大于A
        if (start > 12) {
            return false;
        }
        // 三顺牌是3的倍数
        if (cards.size() % 3 != 0) {
            return false;
        }
        int next;
        for (int i = 3; i < cards.size() - 2; i += 3) {
            next = cards.get(i).getValue();
            if (start != cards.get(i - 1).getValue()) {
                return false;
            }
            if (next != cards.get(i + 2).getValue()) {
                return false;
            }
            if (next - start != 1) {
                return false;
            }
            start = next;
        }
        return true;
    }

    /**
     * 判断是不是飞机
     *
     * @param cards 牌
     * @return 结果
     */
    private boolean isFeiJi(List<Card> cards) {
        cards = sort(cards);
        // 飞机带翅膀的牌数只能是8、10、12、15、16、20
        if (cards.size() == 8 || cards.size() == 10 || cards.size() == 12 || cards.size() == 15
                || cards.size() == 16 || cards.size() == 20) {
            List<Card> feiji = new ArrayList<>();//存是飞机的牌
            //如果是带单牌
            if (cards.size() % 4 == 0) {
                for (int i = 0; i < cards.size(); i++) {
                    Card card_1 = cards.get(i);
                    int count = 0;
                    for (int j = i; j < cards.size(); j++) {
                        Card card_2 = cards.get(j);
                        if (card_1.getValue() == card_2.getValue()) {
                            count++;
                        }
                    }
                    if (count == 3) {
                        feiji.add(cards.get(i));
                    }
                }
                if (isDanShun(feiji, CardType.SANSHUN)) {
                    List<Card> other = new ArrayList<>();
                    for (Card card_1 : cards) {
                        boolean result = true;
                        for (Card card_2 : feiji) {
                            if (card_1.getValue() == card_2.getValue()) {
                                result = false;
                                break;
                            }
                        }
                        if (result) {
                            other.add(card_1);
                        }
                    }
                    if (feiji.size() == other.size()) {//带牌的张数不对
                        return true;
                    }
                }
            }
            //如果带对子
            if (cards.size() % 5 == 0) {
                for (int i = 0; i < cards.size(); i++) {
                    Card card_1 = cards.get(i);
                    int count = 0;
                    for (int j = i; j < cards.size(); j++) {
                        Card card_2 = cards.get(j);
                        if (card_1.getValue() == card_2.getValue()) {
                            count++;
                        }
                    }
                    if (count == 3) {
                        feiji.add(cards.get(i));
                    }
                }
                if (isDanShun(feiji, CardType.SANSHUN)) {
                    List<Card> other = new ArrayList<>();
                    for (Card card_1 : cards) {
                        boolean result = true;
                        for (Card card_2 : feiji) {
                            if (card_1.getValue() == card_2.getValue()) {
                                result = false;
                                break;
                            }
                        }
                        if (result) {
                            other.add(card_1);
                        }
                    }
                    //带的牌是不是对子
                    if (isTwined(other)) {
                        if (feiji.size() == other.size() / 2) {//带牌的张数不对
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

//    /**
//     * 判断是否一串单牌
//     *
//     * @param cards 牌
//     * @return 结果
//     */
//    public boolean isDissimilar(List<Card> cards) {
//        for (int i = 0; i < cards.size() - 1; i++) {
//            for (int j = i + 1; j < cards.size(); j++) {
//                if (cards.get(i).getValue() == cards.get(j).getValue()) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    /**
     * 判断是否一串对牌
     *
     * @param cards 牌
     * @return 结果
     */
    private boolean isTwined(List<Card> cards) {
        for (int i = 0; i <= cards.size() - 2; i += 2) {
            if (cards.get(i).getValue() != cards.get(i + 1).getValue()) {
                return false;
            }
            if (i <= cards.size() - 4) {
                if (cards.get(i).getValue() == cards.get(i + 2).getValue()) {
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * 根据牌型获取牌的 总大小
     *
     * @param cards    牌
     * @param cardType 牌型
     * @return 牌值
     */
    private int getCardValue(List<Card> cards, CardType cardType) {
        int sum = 0;
        //如果为三带一 只算三张的和
        if (cardType == CardType.SANDAIYI) {
            List<Card> cardList = sort(cards);
            if (cardList.size() == 4) {
                if (cardList.get(0).getValue() == cardList.get(2).getValue()) {
                    return cardList.get(0).getValue() * 3;
                }
                if (cardList.get(1).getValue() == cardList.get(3).getValue()) {
                    return cardList.get(1).getValue() * 3;
                }
            } else if (cardList.size() == 5) {
                if (cardList.get(0).getValue() == cardList.get(2).getValue()) {
                    return cardList.get(0).getValue() * 3;
                }
                if (cardList.get(2).getValue() == cardList.get(4).getValue()) {
                    return cardList.get(2).getValue() * 3;
                }
            }
        }
        //如果四带二 只是四张的和
        if (cardType == CardType.SIDAIER) {
            List<Card> cardList = sort(cards);
            if (cardList.get(0).getValue() == cardList.get(3).getValue()) {
                return cardList.get(0).getValue() * 4;
            }
            if (cardList.get(2).getValue() == cardList.get(4).getValue()) {
                return cardList.get(2).getValue() * 4;
            }
        }
        if (cardType == CardType.FEIJI) {
            List<Card> feiji = new ArrayList<>();
            if (cards.size() % 4 == 0) {
                for (int i = 0; i < cards.size() - 3; i++) {
                    if (cards.get(i).getValue() == cards.get(i + 1).getValue() && cards.get(i + 1).getValue() == cards.get(i + 2).getValue()) {
                        feiji.add(cards.get(i));
                    }
                }
                for (Card card : feiji) {
                    sum += card.getValue();
                }
                return sum;
            }
            //如果带对子
            if (cards.size() % 5 == 0) {
                for (int i = 0; i < cards.size() - 4; i++) {
                    if (cards.get(i).getValue() == cards.get(i + 1).getValue() && cards.get(i + 1).getValue() == cards.get(i + 2).getValue()) {
                        feiji.add(cards.get(i));
                    }
                }
                for (Card card : feiji) {
                    sum += card.getValue();
                }
                return sum;
            }
        }
        for (Card card : cards) {
            sum += card.getValue();
        }
        return sum;
    }


    /**
     * 获取牌的 总大小
     *
     * @param cards 牌
     * @return 牌值
     */
    private int getCardValue(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getValue();
        }
        return sum;
    }

    /**
     * 比较牌大小
     *
     * @param oneCard 上一手牌
     * @param twoCard 出的牌
     * @return 1可以出牌 0不能出牌 -1无法比较
     */
    public Integer compare(List<Card> oneCard, List<Card> twoCard) {
        CardType oneCardType = getCardType(oneCard);
        CardType twoCardType = getCardType(twoCard);
        Integer oneCardValue = getCardValue(oneCard, oneCardType);
        Integer twoCardValue = getCardValue(twoCard, twoCardType);

        //如果牌型相同
        if (oneCardType == twoCardType) {
            //如果牌的数量不同 将无法比较
            if (oneCard.size() != twoCard.size()) {
                return -1;
            } else {
                if (oneCardValue.intValue() == twoCardValue.intValue()) {
                    CardColor twoCardColor = twoCard.get(0).getCardColor();
                    if (twoCardColor == CardColor.BIG_JOKER) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
                if (oneCardValue < twoCardValue) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        // 在牌型不同的时候,火箭最大
        if (oneCardType == CardType.HUOJIAN) {
            return 0;
        }
        if (twoCardType == CardType.HUOJIAN) {
            return 1;
        }

        // 在牌型不同的时候,排除火箭的类型，炸弹最大
        if (oneCardType == CardType.ZHADAN) {
            return 0;
        }
        if (twoCardType == CardType.ZHADAN) {
            return 1;
        }

        //无法比较
        return -1;
    }


    /**
     * 移除用户出的牌
     *
     * @param cards 出的牌
     * @param user  用户
     */
    public void removerCard(List<Card> cards, String user) {
        this.desk.getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
            for (Card card : cards) {
                for (Card card1 : seat.getCards()) {
                    if (card.getValue() == card1.getValue() && card.getCardColor() == card1.getCardColor()) {
                        seat.getCards().remove(card1);
                        break;
                    }
                }
            }
            seat.setCardsSize(seat.getCards().size());
        });
    }

    /**
     * 电脑智能出牌
     *
     * @param user 出牌用户
     * @return 结果数据
     */
    public PastCard robotPlay(String user) {
        PastCard pastCard = desk.getPastCard();

        Seat play = null;
        for (int i = 0; i < desk.getSeats().size(); i++) {
            Seat seat = desk.getSeats().get(i);
            if (seat.getPlayer().getUserName().equals(user)) {
                play = seat;
                break;
            }
        }

        assert play != null;
        // 上一手牌为自己出的 玩家随意出一手牌
        if (null == pastCard || null == pastCard.getLastPlayer() || pastCard.getActionPlayer().equals(play.getPlayer().getUserName())) {
            List<Card> playCard = casuallyCards(play.getCards());
            PastCard result = new PastCard();
            result.setIsPass(Action.FALSE);
            result.setCards(playCard);
            result.setNextPlayer(this, play.getPlayer().getUserName());
            result.setActionPlayer(play.getPlayer().getUserName());
            result.setLastPlayer(play.getPlayer().getUserName());
            result.setCardType(getCardType(result.getCards()));
            return result;
        } else {
            //玩家需要出一手比上一手大的牌
            List<Card> playCard = findBigCards(pastCard, play.getCards());
            if (null == playCard) {//没有大得过的牌
                PastCard result = new PastCard();
                result.setCards(this.getDesk().getPastCard().getCards());
                result.setLastPlayer(play.getPlayer().getUserName());
                result.setActionPlayer(this.getDesk().getPastCard().getActionPlayer());
                result.setNextPlayer(this, play.getPlayer().getUserName());
                result.setIsPass(Action.TRUE);
                result.setCardType(getCardType(result.getCards()));
                return result;
            } else {
                PastCard result = new PastCard();
                result.setIsPass(Action.FALSE);
                result.setCards(playCard);
                result.setLastPlayer(play.getPlayer().getUserName());
                result.setActionPlayer(play.getPlayer().getUserName());
                result.setNextPlayer(this, play.getPlayer().getUserName());
                result.setCardType(getCardType(result.getCards()));
                return result;
            }
        }
    }


    /**
     * 随意出牌
     *
     * @param cards 玩家手牌数据
     * @return 一组排
     */
    private List<Card> casuallyCards(List<Card> cards) {
        CardsAnalyzer analyzer = CardsAnalyzer.getInstance();
        analyzer.setPokes(cards);

        //根据优先级来处理
        Vector<List<Card>> card_sanshun = analyzer.getCard_sanshun();
        Vector<List<Card>> card_shuangshun = analyzer.getCard_shuangshun();
        Vector<List<Card>> card_sanzhang = analyzer.getCard_sanzhang();
        Vector<List<Card>> card_danshun = analyzer.getCard_danshun();
        Vector<List<Card>> card_duipai = analyzer.getCard_duipai();
        Vector<List<Card>> card_danpai = analyzer.getCard_danpai();
        Vector<List<Card>> card_zhadan = analyzer.getCard_zhadan();

        if (card_sanshun.size() > 0) {
            List<Card> cardArray = card_sanshun.get(0);
            //需要带的牌
            int size = cardArray.size() / 3;

            //从单牌中选择
            if (card_danpai.size() > size) {
                for (int j = 0; j < size; j++) {
                    cardArray.addAll(card_danpai.get(j));
                }
                return cardArray;
            }
            //从对子中选择
            if (card_duipai.size() > size) {
                for (int j = 0; j < size; j++) {
                    cardArray.addAll(card_duipai.get(j));
                }
                return cardArray;
            }
            //不带
            return cardArray;
        }

        if (card_shuangshun.size() > 0) {
            return card_shuangshun.get(0);
        }
        if (card_sanzhang.size() > 0) {
            List<Card> cardArray = card_sanzhang.get(0);
            //从单牌中选择
            if (card_danpai.size() > 0) {
                cardArray.add(card_danpai.get(0).get(0));
                return cardArray;
            }
            //从对子中选
            if (card_duipai.size() > 0) {
                cardArray.addAll(card_duipai.get(0));
                return cardArray;
            }
            //不带
            return cardArray;
        }
        if (card_danshun.size() > 0) {
            return card_danshun.get(0);
        }
        if (card_duipai.size() > 0) {
            return card_duipai.get(0);
        }
        if (card_danpai.size() > 0) {
            return card_danpai.get(0);
        }
        if (card_zhadan.size() > 0) {
            return card_zhadan.get(0);
        }
        return null;
    }

    /**
     * 寻找大牌
     *
     * @param pastCard 上一手牌数据
     * @param cards    玩家手牌数据
     * @return 一组排
     */
    public List<Card> findBigCards(PastCard pastCard, List<Card> cards) {
        // 获取pastCard的信息，牌值，牌型
        List<Card> cardList = pastCard.getCards();
        CardType cardType = this.getCardType(cardList);
        int cardValue = getCardValue(cardList, cardType);
        int cardLength = cardList.size();

        // 使用AnalyzePoke来对牌进行分析
        CardsAnalyzer analyzer = CardsAnalyzer.getInstance();
        analyzer.setPokes(cards);

        Vector<List<Card>> temp;
        int size;

        // 根据适当牌型选取适当牌
        switch (cardType) {
            case DANPAI:
                temp = analyzer.getCard_danpai();
                size = temp.size();
                for (int i = 0; i < size; i++) {
                    List<Card> cardArray = temp.get(i);
                    int v = this.getCardValue(cardArray);
                    if (v > cardValue) {
                        return cardArray;
                    }
                    if (v == 16 && cardArray.get(0).getCardColor() == CardColor.HEART) {
                        return cardArray;
                    }
                }
                // 如果单牌中没有，则选择现有牌型中除火箭和4个2后的最大一个
                int st = cards.size() - 1;
                if (analyzer.getCountWang().size() == 2) {
                    st -= 2;
                }
                if (analyzer.getCount2().size() == 4) {
                    st -= 4;
                }
                if (-1 < st && cards.get(st).getValue() > cardValue) {
                    List<Card> result = new ArrayList<>();
                    result.add(cards.get(st));
                    return result;
                }

                // 检查炸弹，出牌炸
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }

                break;
            case DUIZI:
                temp = analyzer.getCard_duipai();
                size = temp.size();

                for (int i = 0; i < size; i++) {
                    List<Card> cardArray = temp.get(i);
                    int v = this.getCardValue(cardArray);
                    if (v > cardValue) {
                        return cardArray;
                    }
                }

                // 如果对子中没有，则需要检查双顺
                temp = analyzer.getCard_shuangshun();
                size = temp.size();
                for (int i = 0; i < size; i++) {
                    List<Card> cardArray = temp.get(i);
                    for (int j = cardArray.size() - 1; j > 0; j--) {
                        int v = cardArray.get(j).getValue();
                        if (v > cardValue) {
                            List<Card> result = new ArrayList<>();
                            result.add(cardArray.get(j));
                            result.add(cardArray.get(j - 1));
                            return result;
                        }
                    }
                }
                // 如果双顺中没有，则需要检查三张
                temp = analyzer.getCard_sanzhang();
                size = temp.size();
                for (int i = 0; i < size; i++) {
                    List<Card> cardArray = temp.get(i);
                    int v = cardArray.get(0).getValue();
                    if (v > cardValue) {
                        List<Card> result = new ArrayList<>();
                        result.add(cardArray.get(0));
                        result.add(cardArray.get(1));
                        return result;
                    }
                }

                // 检查炸弹，出牌炸
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
            case SANZHANG:
                temp = analyzer.getCard_sanzhang();
                size = temp.size();
                for (int i = 0; i < size; i++) {
                    List<Card> cardArray = temp.get(i);
                    int v = this.getCardValue(cardArray);
                    if (v > cardValue) {
                        return cardArray;
                    }
                }

                // 检查炸弹，出牌炸
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
            case SANDAIYI:
                if (cards.size() < 4) {
                    break;
                }
                boolean find = false;
                if (cardLength == 4) {
                    List<Card> sandaiyi = new ArrayList<>();
                    temp = analyzer.getCard_sanzhang();
                    size = temp.size();
                    for (int i = size - 1; i >= 0; i--) {
                        List<Card> cardArray = temp.get(i);
                        int v = this.getCardValue(cardArray);
                        if (v > cardValue) {
                            for (int j = 0; j < cardArray.size(); j++) {
                                sandaiyi.add(j, cardArray.get(j));
                                find = true;
                            }
                        }
                    }
                    // 三张满足条件
                    if (find) {
                        // 再找一张组合成三带一
                        temp = analyzer.getCard_danpai();
                        size = temp.size();
                        if (size > 0) {
                            List<Card> t = temp.get(0);
                            sandaiyi.add(3, t.get(0));
                        } else {
                            temp = analyzer.getCard_danshun();
                            size = temp.size();
                            for (int i = 0; i < size; i++) {
                                List<Card> danshun = temp.get(i);
                                if (danshun.size() >= 6) {
                                    sandaiyi.add(3, danshun.get(0));
                                }
                            }
                        }
                        // 从中随便找一个最小的
                        if (sandaiyi.size() == 3) {
                            cards.stream().filter(card -> card.getValue() != sandaiyi.get(0).getValue()).forEach(card -> sandaiyi.add(3, card));
                        }
                        if (sandaiyi.size() == 4) {
                            sort(sandaiyi);
                            return sandaiyi;
                        }
                    } else {
                        if (analyzer.getCard_zhadan().size() > 0) {
                            return analyzer.getCard_zhadan().get(0);
                        }
                        break;
                    }
                }
                if (cardLength == 5) {
                    List<Card> sandaidui = new ArrayList<>();
                    temp = analyzer.getCard_sanzhang();
                    size = temp.size();
                    for (int i = size - 1; i >= 0; i--) {
                        List<Card> cardArray = temp.get(i);
                        int v = this.getCardValue(cardArray);
                        if (v > cardValue) {
                            for (int j = 0; j < cardArray.size(); j++) {
                                sandaidui.add(j, cardArray.get(j));
                                find = true;
                            }
                        }
                    }
                    // 三张满足条件
                    if (find) {
                        // 再找一对组合成三带一对
                        temp = analyzer.getCard_duipai();
                        size = temp.size();
                        if (size > 0) {
                            List<Card> t = temp.get(0);
                            sandaidui.add(3, t.get(0));
                            sandaidui.add(4, t.get(1));
                        } else {
                            temp = analyzer.getCard_shuangshun();
                            size = temp.size();
                            for (int i = 0; i < size; i += 2) {
                                List<Card> shuangshun = temp.get(i);
                                if (shuangshun.size() >= 8) {
                                    sandaidui.add(3, shuangshun.get(0));
                                    sandaidui.add(4, shuangshun.get(1));
                                }
                            }
                        }
                        // 从中随便找一个最小的对牌
                        if (sandaidui.size() == 3) {
                            for (int i = 0; i < cards.size() - 1; i++) {
                                if (cards.get(i).getValue() != sandaidui.get(0).getValue()
                                        && cards.get(i).getValue() == cards.get(i + 1).getValue()) {
                                    sandaidui.add(3, cards.get(i));
                                    sandaidui.add(4, cards.get(i + 1));
                                }
                            }
                        }
                        if (sandaidui.size() == 5) {
                            sort(sandaidui);
                            return sandaidui;
                        }
                    }
                } else {
                    if (analyzer.getCard_zhadan().size() > 0) {
                        return analyzer.getCard_zhadan().get(0);
                    }
                    break;
                }
                break;
            case SUNZI:
                temp = analyzer.getCard_danshun();
                size = temp.size();
                for (int i = 0; i < size; i++) {
                    List<Card> danshun = temp.get(i);
                    if (danshun.size() == cardLength) {
                        if (cardValue < this.getCardValue(danshun)) {
                            return danshun;
                        }
                    }
                }
                for (int i = 0; i < size; i++) {
                    List<Card> danshun = sort(temp.get(i));
                    if (danshun.size() > cardLength) {
                        int index = 0;
                        for (int j = 0; j < danshun.size(); j++) {
                            List<Card> arrayCard = new ArrayList<>();
                            for (int k = index; arrayCard.size() < cardLength && danshun.size() > k; k++) {
                                arrayCard.add(danshun.get(k));
                            }
                            if (getCardValue(arrayCard) > cardValue) {
                                return arrayCard;
                            }
                            index++;
                        }
                    }
                }
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
            case SHUANGSHUN:
                temp = analyzer.getCard_shuangshun();
                size = temp.size();
                for (int i = size - 1; i >= 0; i--) {
                    List<Card> cardArray = temp.get(i);
                    if (cardArray.size() < cardLength) {
                        continue;
                    }

                    if (cardValue < cardArray.get(0).getValue()) {
                        if (cardArray.size() == cardLength) {
                            return cardArray;
                        } else {
                            // int d = (cardArray.length - cardLength) / 2;
                            int index = 0;
                            for (int j = cardArray.size() - 1; j >= 0; j--) {
                                if (cardValue < cardArray.get(j).getValue()) {
                                    index = j / 2;
                                    break;
                                }
                            }

                            int total = cardArray.size() / 2;
                            int cardTotal = cardLength / 2;
                            if (index + cardTotal > total) {
                                index = total - cardTotal;
                            }
                            List<Card> shuangshun = new ArrayList<>();
                            int m = 0;
                            for (int k = index * 2; k < cardArray.size(); k++) {
                                shuangshun.add(m++, cardArray.get(k));
                            }
                            return shuangshun;
                        }
                    }
                }
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
            case SANSHUN:
                temp = analyzer.getCard_sanshun();
                size = temp.size();
                for (int i = size - 1; i >= 0; i--) {
                    List<Card> cardArray = temp.get(i);
                    if (cardLength > cardArray.size()) {
                        continue;
                    }

                    if (cardValue < cardArray.get(0).getValue()) {
                        if (cardLength == cardArray.size()) {
                            return cardArray;
                        } else {
                            List<Card> newArray = new ArrayList<>();
                            for (int k = 0; k < cardLength; k++) {
                                newArray.add(k, cardArray.get(k));
                            }
                            return newArray;
                        }
                    }
                }
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
            case FEIJI:
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
            case ZHADAN:
                temp = analyzer.getCard_zhadan();
                size = temp.size();
                List<Card> zd;
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        zd = temp.elementAt(i);
                        if (cardValue < zd.get(0).getValue()) {
                            return zd;
                        }
                    }
                }
                break;
            case HUOJIAN:
                return null;
            case SIDAIER:
                if (analyzer.getCard_zhadan().size() > 0) {
                    return analyzer.getCard_zhadan().get(0);
                }
                break;
        }

        return null;
    }
}
