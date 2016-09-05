package chess.game.landlords.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 牌分析
 * Created by YJH
 * Date : 16-7-6.
 */
public class CardsAnalyzer {

    private List<Card> cards;//手上所有卡牌
    private int[] countCards = new int[12];//统计每张牌出现次数   3-A
    private List<Card> count2 = new ArrayList<>();//手上2的个数
    private List<Card> countWang = new ArrayList<>();//手上王的个数
    private Vector<List<Card>> card_zhadan = new Vector<>(3);//炸弹
    private Vector<List<Card>> card_sanshun = new Vector<>(3);//三顺
    private Vector<List<Card>> card_shuangshun = new Vector<>(3);//双顺
    private Vector<List<Card>> card_sanzhang = new Vector<>(3);//三张
    private Vector<List<Card>> card_danshun = new Vector<>(3);//单顺
    private Vector<List<Card>> card_duipai = new Vector<>(3);//对子
    private Vector<List<Card>> card_danpai = new Vector<>(5);//单牌

    public List<Card> getCards() {
        return cards;
    }

    List<Card> getCount2() {
        return count2;
    }

    List<Card> getCountWang() {
        return countWang;
    }

    Vector<List<Card>> getCard_zhadan() {
        return card_zhadan;
    }

    Vector<List<Card>> getCard_sanshun() {
        return card_sanshun;
    }

    Vector<List<Card>> getCard_shuangshun() {
        return card_shuangshun;
    }

    Vector<List<Card>> getCard_sanzhang() {
        return card_sanzhang;
    }

    Vector<List<Card>> getCard_danshun() {
        return card_danshun;
    }

    Vector<List<Card>> getCard_duipai() {
        return card_duipai;
    }

    Vector<List<Card>> getCard_danpai() {
        return card_danpai;
    }

    public static CardsAnalyzer getInstance() {
        return new CardsAnalyzer();
    }

    /**
     * 初始化
     */
    private void init() {
        for (int i = 0; i < countCards.length; i++) {
            countCards[i] = 0;
        }
        count2.clear();
        countWang.clear();
        card_zhadan.clear();
        card_sanshun.clear();
        card_shuangshun.clear();
        card_sanzhang.clear();
        card_danshun.clear();
        card_duipai.clear();
        card_danpai.clear();
    }

    void setPokes(List<Card> cards) {
        this.cards = Game.sort(cards);
        analyze();
    }

    /**
     * 分析几大主要牌型
     */
    private void analyze() {

        // 初始化牌型容器
        init();

        // 分析王，2，普通牌的数量
        for (Card card1 : cards) {
            int v = card1.getValue();
            if (v == 16) {
                countWang.add(card1);
            } else if (v == 15) {
                count2.add(card1);
            } else {
                countCards[v - 3]++;
            }
        }

        // 分析三顺牌型
        int start = -1;
        int end = -1;
        for (int i = 0; i < countCards.length; i++) {
            if (countCards[i] == 3) {
                if (start == -1) {
                    start = i;
                } else {
                    end = i;
                }
            } else {
                if (end != -1 && start != -1) {
                    int dur = end - start + 1;
                    List<Card> ss = new ArrayList<>(dur * 3);
                    int m = 0;
                    for (Card card : cards) {
                        int v = card.getValue() - 3;
                        if (v >= start && v <= end) {
                            ss.add(m++, card);
                        }
                    }
                    card_sanshun.addElement(ss);
                    for (int s = start; s <= end; s++) {
//                        countCards[s]--;
                    }
                    start = end = -1;
                } else {
                    start = end = -1;
                }
            }
        }

        // 分析双顺牌型
        int sstart = -1;
        int send = -1;
        for (int i = 0; i < countCards.length; i++) {
            if (countCards[i] >= 2) {
                if (sstart == -1) {
                    sstart = i;
                } else {
                    send = i;
                }
            } else {
                if (sstart != -1 && send != -1) {
                    int dur = send - sstart + 1;
                    if (dur < 3) {
                        sstart = send = -1;
                    } else {
                        List<Card> shuangshun = new ArrayList<>(dur * 2);
                        int m = 0;
                        for (int j = cards.size() - 1; j >= 0; j--) {
                            int v = cards.get(j).getValue() - 3;
                            if (v >= sstart && v <= send) {
                                int count = 0;
                                for (Card item : shuangshun) {
                                    if (item.getValue() == cards.get(j).getValue()) {
                                        count++;
                                    }
                                }
                                if (count < 2) {
                                    shuangshun.add(m++, cards.get(j));
                                }
                            }
                        }
                        card_shuangshun.addElement(shuangshun);
                        for (int s = sstart; s <= send; s++) {
//                            countCards[i]--;
                        }
                        sstart = send = -1;
                    }
                } else {
                    sstart = send = -1;
                }
            }
        }

        // 分析单顺牌型
        int dstart = -1;
        int dend = -1;
        for (int i = 0; i < countCards.length; i++) {
            if (countCards[i] >= 1) {
                if (dstart == -1) {
                    dstart = i;
                } else {
                    dend = i;
                }
            } else {
                if (dstart != -1 && dend != -1) {
                    int dur = dend - dstart + 1;
                    if (dur >= 5) {
                        int m = 0;
                        List<Card> danshun = new ArrayList<>(dur);
                        for (int j = cards.size() - 1; j >= 0; j--) {
                            int v = cards.get(j).getValue() - 3;
                            if (v == dend) {
                                danshun.add(m++, cards.get(j));
//                                countCards[dend]--;
                                dend--;
                            }
                            if (dend == dstart - 1) {
                                break;
                            }
                        }
                        card_danshun.addElement(danshun);
                    }
                    dstart = dend = -1;
                } else {
                    dstart = dend = -1;
                }
            }
        }

        // 分析三张牌型
        for (int i = 0; i < countCards.length; i++) {
            if (countCards[i] == 3) {
                countCards[i] = -1;
                List<Card> sanzhang = new ArrayList<>(3);
                int m = 0;
                for (Card card : cards) {
                    int v = card.getValue() - 3;
                    if (v == i) {
                        sanzhang.add(m++, card);
                    }
                }
                card_sanzhang.addElement(sanzhang);
            }
        }

        // 分析对牌
        for (int i = 0; i < countCards.length; i++) {
            if (countCards[i] == 2) {
                List<Card> duipai = new ArrayList<>(2);
                for (int j = 0; j < cards.size(); j++) {
                    int v = cards.get(j).getValue() - 3;
                    if (v == i) {
                        duipai.add(0, cards.get(j));
                        duipai.add(1, cards.get(j + 1));
                        card_duipai.addElement(duipai);
                        break;
                    }
                }
                countCards[i]--;
            }
        }

        // 分析单牌
        for (int i = 0; i < countCards.length; i++) {
            if (countCards[i] == 1) {
                for (Card card : cards) {
                    int v = card.getValue() - 3;
                    if (v == i) {
                        List<Card> danpai = new ArrayList<>(1);
                        danpai.add(card);
                        card_danpai.addElement(danpai);
                        countCards[i]--;
                        break;
                    }

                }
            }
        }

        switch (count2.size()) {
            case 4:
                card_zhadan.addElement(count2);
                break;
            case 3:
                card_sanzhang.addElement(count2);
                break;
            case 2:
                card_duipai.addElement(count2);
                break;
            case 1:
                card_danpai.addElement(count2);
                break;
        }

        // 分析炸弹
        for (int i = 0; i < countCards.length - 1; i++) {
            if (countCards[i] == 4) {
                List<Card> zhadan = new ArrayList<>();
                Card card_1 = new Card(i + 3, CardColor.SPADE);
                Card card_2 = new Card(i + 3, CardColor.HEART);
                Card card_3 = new Card(i + 3, CardColor.PLUM);
                Card card_4 = new Card(i + 3, CardColor.BLOCK);
                zhadan.add(card_1);
                zhadan.add(card_2);
                zhadan.add(card_3);
                zhadan.add(card_4);
                card_zhadan.addElement(zhadan);
                countCards[i]--;
            }
        }

        // 分析火箭
        if (countWang.size() == 1) {
            List<Card> danpai = new ArrayList<>();
            danpai.add(countWang.get(0));
            card_danpai.addElement(danpai);
        } else if (countWang.size() == 2) {
            List<Card> zhadan = new ArrayList<>();
            zhadan.add(countWang.get(0));
            zhadan.add(countWang.get(1));
            card_zhadan.addElement(zhadan);
        }

    }

}
