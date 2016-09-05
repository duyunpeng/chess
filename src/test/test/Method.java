package test;

import chess.application.user.representation.ApiUserRepresentation;
import chess.game.landlords.mode.*;
import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.Game;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import javax.swing.*;
import javax.validation.constraints.Past;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * Created by yjh on 16-6-15.
 */
public class Method {

    @Test
    public void random() {
        System.out.println((int) (Math.random() * 3));
    }

    @Test
    public void dealCard() {
        String json = "{\"desk\":{\"baseScore\":10,\"callCount\":1,\"deskNo\":\"1f21e570-9def-4127-9bdb-11c1775c221b\",\"gameStatus\":4,\"landlordCard\":[{\"cardColor\":\"SPADE\",\"value\":8},{\"cardColor\":\"SPADE\",\"value\":13},{\"cardColor\":\"SPADE\",\"value\":15}],\"multiple\":2,\"randomLandlordPlayer\":{\"head\":\"\",\"loseCount\":0,\"money\":110,\"sex\":\"MAN\",\"userName\":\"15823634844\",\"winCount\":0},\"seats\":[{\"cards\":[{\"cardColor\":\"PLUM\",\"value\":3},{\"cardColor\":\"BLOCK\",\"value\":3},{\"cardColor\":\"HEART\",\"value\":4},{\"cardColor\":\"BLOCK\",\"value\":4},{\"cardColor\":\"SPADE\",\"value\":6},{\"cardColor\":\"BLOCK\",\"value\":6},{\"cardColor\":\"SPADE\",\"value\":9},{\"cardColor\":\"SPADE\",\"value\":10},{\"cardColor\":\"HEART\",\"value\":11},{\"cardColor\":\"BLOCK\",\"value\":12},{\"cardColor\":\"PLUM\",\"value\":13},{\"cardColor\":\"HEART\",\"value\":14},{\"cardColor\":\"BLOCK\",\"value\":14},{\"cardColor\":\"HEART\",\"value\":15},{\"cardColor\":\"PLUM\",\"value\":15},{\"cardColor\":\"BLOCK\",\"value\":15},{\"cardColor\":\"BIG_KING\",\"value\":17}],\"cardsSize\":17,\"isCallLandlord\":\"TRUE\",\"isDouble\":\"NONE\",\"isJoin\":\"TRUE\",\"isOffLine\":\"FALSE\",\"isReady\":\"TRUE\",\"isRobLandlord\":\"FALSE\",\"player\":{\"$ref\":\"$.desk.randomLandlordPlayer\"},\"seatNo\":0},{\"cards\":[{\"cardColor\":\"SPADE\",\"value\":3},{\"cardColor\":\"SPADE\",\"value\":5},{\"cardColor\":\"HEART\",\"value\":5},{\"cardColor\":\"PLUM\",\"value\":5},{\"cardColor\":\"PLUM\",\"value\":7},{\"cardColor\":\"BLOCK\",\"value\":7},{\"cardColor\":\"PLUM\",\"value\":8},{\"cardColor\":\"BLOCK\",\"value\":9},{\"cardColor\":\"PLUM\",\"value\":10},{\"cardColor\":\"BLOCK\",\"value\":10},{\"cardColor\":\"PLUM\",\"value\":11},{\"cardColor\":\"SPADE\",\"value\":12},{\"cardColor\":\"HEART\",\"value\":12},{\"cardColor\":\"PLUM\",\"value\":12},{\"cardColor\":\"HEART\",\"value\":13},{\"cardColor\":\"PLUM\",\"value\":14},{\"cardColor\":\"HEART\",\"value\":16}],\"cardsSize\":17,\"isCallLandlord\":\"FALSE\",\"isDouble\":\"NONE\",\"isJoin\":\"FALSE\",\"isOffLine\":\"TRUE\",\"isReady\":\"FALSE\",\"isRobLandlord\":\"FALSE\",\"player\":{\"head\":\"\",\"loseCount\":0,\"money\":10000,\"sex\":\"MAN\",\"userName\":\"15823634866\",\"winCount\":0},\"seatNo\":1},{\"cards\":[{\"cardColor\":\"HEART\",\"value\":3},{\"cardColor\":\"SPADE\",\"value\":4},{\"cardColor\":\"PLUM\",\"value\":4},{\"cardColor\":\"BLOCK\",\"value\":5},{\"cardColor\":\"HEART\",\"value\":6},{\"cardColor\":\"PLUM\",\"value\":6},{\"cardColor\":\"SPADE\",\"value\":7},{\"cardColor\":\"HEART\",\"value\":7},{\"cardColor\":\"HEART\",\"value\":8},{\"cardColor\":\"BLOCK\",\"value\":8},{\"cardColor\":\"HEART\",\"value\":9},{\"cardColor\":\"PLUM\",\"value\":9},{\"cardColor\":\"HEART\",\"value\":10},{\"cardColor\":\"SPADE\",\"value\":11},{\"cardColor\":\"BLOCK\",\"value\":11},{\"cardColor\":\"BLOCK\",\"value\":13},{\"cardColor\":\"SPADE\",\"value\":14}],\"cardsSize\":17,\"isCallLandlord\":\"FALSE\",\"isDouble\":\"NONE\",\"isJoin\":\"FALSE\",\"isOffLine\":\"TRUE\",\"isReady\":\"FALSE\",\"isRobLandlord\":\"FALSE\",\"player\":{\"head\":\"\",\"loseCount\":0,\"money\":1,\"sex\":\"WOMAN\",\"userName\":\"15823634877\",\"winCount\":0},\"seatNo\":2}]}}";
        Game game = JSON.parseObject(json, Game.class);
        System.out.println(JSONObject.toJSONString(game).getBytes().length);
    }

    @Test
    public void newSeat() {
        Seat seat = new Seat();
        System.out.println("1");
    }

    @Test
    public void list() {
        Seat seat = new Seat();
        seat.setIsCallLandlord(Action.FALSE);
        if (null != seat.getIsCallLandlord() && seat.getIsCallLandlord() == Action.FALSE) {
            System.out.println("+1");
        }
    }

    @Test
    public void strLength() {
        Map<String, String> s = new HashMap<String, String>();
        s.put("sb", "sb");
        System.out.println(s.get("sb"));
        s.put("sb", "sb");
        System.out.println(s.get("sb"));
    }

    @Test
    public void listJson() {
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(3, CardColor.PLUM));
        cards.add(new Card(3, CardColor.BLOCK));
        cards.add(new Card(3, CardColor.HEART));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playCard", null);
        jsonObject.put("deskNo", "123213");
        System.out.println(jsonObject.toJSONString());
        String strJson = jsonObject.toJSONString();
        JSONObject jsonObject_1 = JSONObject.parseObject(strJson);
        cards = (List<Card>) jsonObject_1.get("playCard");
        System.out.println(jsonObject_1);
    }

    @Test
    public void compare() {
        Game game = new Game();
        List<Card> one = new ArrayList<Card>();
        one.add(new Card(15, CardColor.HEART));

        List<Card> two = new ArrayList<Card>();
        two.add(new Card(16, CardColor.LITTLE_JOKER));


        System.out.println(game.compare(one, two));
    }

    @Test
    public void getCardType() {
        List<Card> cardList = new ArrayList<Card>();
        cardList.add(new Card(10, CardColor.BLOCK));
        cardList.add(new Card(10, CardColor.PLUM));
        cardList.add(new Card(10, CardColor.BLOCK));
        cardList.add(new Card(11, CardColor.BLOCK));
        cardList.add(new Card(11, CardColor.PLUM));
        cardList.add(new Card(11, CardColor.BLOCK));
        cardList.add(new Card(12, CardColor.PLUM));
        cardList.add(new Card(12, CardColor.PLUM));
        cardList.add(new Card(13, CardColor.PLUM));
        cardList.add(new Card(13, CardColor.PLUM));

        Game game = new Game();
        CardType cardType = game.getCardType(cardList);
        System.out.println();
    }

    @Test
    public void booleanTest() {
        boolean bool = true;
        System.out.println(JSONObject.toJSONString(bool));
    }

    public static int count = 1;

    public static int sellTicket() {
        return count++;
    }

    @Test
    public void duotest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).run();
    }

    @Test
    public void te() {
        Socket socket = new Socket();
        try {
            socket.bind(new InetSocketAddress("0.0.0.0", 0));
            socket.connect(new InetSocketAddress("www.baidu.com", 80));

            System.out.println(socket.getLocalAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void remover() {
        List<Card> one = new ArrayList<Card>();
        one.add(new Card(10, CardColor.PLUM));
        one.add(new Card(10, CardColor.SPADE));

        List<Card> two = new ArrayList<Card>();
        two.add(new Card(5, CardColor.HEART));
        two.add(new Card(5, CardColor.SPADE));
        two.add(new Card(5, CardColor.PLUM));

        two.add(new Card(9, CardColor.PLUM));
        two.add(new Card(9, CardColor.SPADE));

        two.add(new Card(6, CardColor.HEART));
        two.add(new Card(6, CardColor.SPADE));
        two.add(new Card(6, CardColor.PLUM));

        two.add(new Card(10, CardColor.PLUM));
        two.add(new Card(10, CardColor.SPADE));


        for (Card card : one) {
            for (Card card1 : two) {
                if (card.getValue() == card1.getValue() && card.getCardColor() == card1.getCardColor()) {
                    two.remove(card1);
                    break;
                }
            }
        }

        System.out.println("");
    }

    @Test
    public void testBit() {
        try {
            FileOutputStream file_out = new FileOutputStream(new File("/home/yjh/桌面/文本.txt"));
            DataOutputStream outputStream = new DataOutputStream(file_out);

            outputStream.writeUTF("{\"desk\":{\"baseScore\":10,\"callCount\":4,\"deskNo\":\"48008b9b-b7dc-4d1c-a89a-a59666ee7e41\",\"gameStatus\":4,\"landlordCard\":[{\"cardColor\":\"HEART\",\"value\":5},{\"cardColor\":\"PLUM\",\"value\":8},{\"cardColor\":\"HEART\",\"value\":9}],\"landlordPlayer\":{\"head\":\"\",\"loseCount\":0,\"money\":110,\"sex\":\"MAN\",\"userName\":\"15823634844\",\"winCount\":0},\"multiple\":16,\"randomLandlordPlayer\":{\"head\":\"\",\"loseCount\":0,\"money\":110,\"sex\":\"MAN\",\"userName\":\"15823634844\",\"winCount\":0},\"seats\":[{\"cards\":[{\"cardColor\":\"SPADE\",\"value\":3},{\"cardColor\":\"HEART\",\"value\":5},{\"cardColor\":\"PLUM\",\"value\":5},{\"cardColor\":\"BLOCK\",\"value\":5},{\"cardColor\":\"HEART\",\"value\":6},{\"cardColor\":\"BLOCK\",\"value\":6},{\"cardColor\":\"BLOCK\",\"value\":7},{\"cardColor\":\"SPADE\",\"value\":8},{\"cardColor\":\"PLUM\",\"value\":8},{\"cardColor\":\"SPADE\",\"value\":9},{\"cardColor\":\"HEART\",\"value\":9},{\"cardColor\":\"PLUM\",\"value\":9},{\"cardColor\":\"BLOCK\",\"value\":9},{\"cardColor\":\"HEART\",\"value\":11},{\"cardColor\":\"BLOCK\",\"value\":12},{\"cardColor\":\"SPADE\",\"value\":13},{\"cardColor\":\"HEART\",\"value\":13},{\"cardColor\":\"HEART\",\"value\":14},{\"cardColor\":\"SPADE\",\"value\":15},{\"cardColor\":\"HEART\",\"value\":16}],\"isCallLandlord\":\"TRUE\",\"isDouble\":\"TRUE\",\"isEscape\":\"FALSE\",\"isJoin\":\"TRUE\",\"isOffLine\":\"FALSE\",\"isReady\":\"TRUE\",\"isRobLandlord\":\"TRUE\",\"isRobot\":\"FALSE\",\"player\":{\"head\":\"\",\"loseCount\":0,\"money\":110,\"sex\":\"MAN\",\"userName\":\"15823634844\",\"winCount\":0},\"seatNo\":0},{\"cards\":[{\"cardColor\":\"HEART\",\"value\":3},{\"cardColor\":\"PLUM\",\"value\":3},{\"cardColor\":\"BLOCK\",\"value\":3},{\"cardColor\":\"SPADE\",\"value\":4},{\"cardColor\":\"PLUM\",\"value\":4},{\"cardColor\":\"BLOCK\",\"value\":4},{\"cardColor\":\"PLUM\",\"value\":7},{\"cardColor\":\"HEART\",\"value\":8},{\"cardColor\":\"SPADE\",\"value\":10},{\"cardColor\":\"HEART\",\"value\":10},{\"cardColor\":\"PLUM\",\"value\":10},{\"cardColor\":\"PLUM\",\"value\":11},{\"cardColor\":\"HEART\",\"value\":12},{\"cardColor\":\"BLOCK\",\"value\":13},{\"cardColor\":\"SPADE\",\"value\":14},{\"cardColor\":\"HEART\",\"value\":15},{\"cardColor\":\"BLOCK\",\"value\":15}],\"isCallLandlord\":\"FALSE\",\"isDouble\":\"TRUE\",\"isEscape\":\"FALSE\",\"isJoin\":\"TRUE\",\"isOffLine\":\"FALSE\",\"isReady\":\"TRUE\",\"isRobLandlord\":\"TRUE\",\"isRobot\":\"FALSE\",\"player\":{\"head\":\"\",\"loseCount\":0,\"money\":10,\"sex\":\"WOMAN\",\"userName\":\"15823634855\",\"winCount\":0},\"seatNo\":1},{\"cards\":[{\"cardColor\":\"HEART\",\"value\":4},{\"cardColor\":\"SPADE\",\"value\":5},{\"cardColor\":\"SPADE\",\"value\":6},{\"cardColor\":\"PLUM\",\"value\":6},{\"cardColor\":\"SPADE\",\"value\":7},{\"cardColor\":\"HEART\",\"value\":7},{\"cardColor\":\"BLOCK\",\"value\":8},{\"cardColor\":\"BLOCK\",\"value\":10},{\"cardColor\":\"SPADE\",\"value\":11},{\"cardColor\":\"BLOCK\",\"value\":11},{\"cardColor\":\"SPADE\",\"value\":12},{\"cardColor\":\"PLUM\",\"value\":12},{\"cardColor\":\"PLUM\",\"value\":13},{\"cardColor\":\"PLUM\",\"value\":14},{\"cardColor\":\"BLOCK\",\"value\":14},{\"cardColor\":\"PLUM\",\"value\":15},{\"cardColor\":\"BIG_KING\",\"value\":16}],\"isCallLandlord\":\"FALSE\",\"isDouble\":\"TRUE\",\"isEscape\":\"FALSE\",\"isJoin\":\"TRUE\",\"isOffLine\":\"FALSE\",\"isReady\":\"TRUE\",\"isRobLandlord\":\"TRUE\",\"isRobot\":\"FALSE\",\"player\":{\"head\":\"\",\"loseCount\":0,\"money\":10000,\"sex\":\"MAN\",\"userName\":\"15823634866\",\"winCount\":0},\"seatNo\":2}]}}\";");

            FileInputStream file_input = new FileInputStream(new File("/home/yjh/桌面/文本.txt"));
            DataInputStream inputStream = new DataInputStream(file_input);


            int str = inputStream.readUnsignedByte();
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void window() {
        JFrame jFrame = new JFrame();
        jFrame.setSize(300, 300);//设置宽度和高度
        jFrame.setLocation(300, 266);//设置初始位置
        jFrame.setVisible(true);//设置可见
        jFrame.setTitle("窗体");
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                JOptionPane.showMessageDialog(null, "按下了" + e.getExtendedKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                JOptionPane.showMessageDialog(null, "释放了" + e.getExtendedKeyCode());
            }
        });
        while (true) {

        }
    }

    @Test
    public void game() {
        ApiUserRepresentation user_1 = new ApiUserRepresentation();
        user_1.setUserName("15823634800");
        user_1.setName("张飞");
        user_1.setMoney(new BigDecimal(10));
        user_1.setLandlordsWinCount(0);
        user_1.setLandlordsLoseCount(0);
        ApiUserRepresentation user_2 = new ApiUserRepresentation();
        user_2.setUserName("15823634811");
        user_2.setName("刘备");
        user_2.setMoney(new BigDecimal(10));
        user_2.setLandlordsWinCount(0);
        user_2.setLandlordsLoseCount(0);
        ApiUserRepresentation user_3 = new ApiUserRepresentation();
        user_3.setUserName("15823634822");
        user_3.setName("关羽");
        user_3.setMoney(new BigDecimal(10));
        user_3.setLandlordsWinCount(0);
        user_3.setLandlordsLoseCount(0);
        List<ApiUserRepresentation> userList = new ArrayList<>();
        userList.add(user_1);
        userList.add(user_2);
        userList.add(user_3);


        Game game = new Game(userList, "123", new BigDecimal(10));
        game.dealCard();

        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card(3, CardColor.HEART));
        cardList.add(new Card(3, CardColor.BLOCK));
        PastCard pastCard = new PastCard();
        pastCard.setNextPlayer("15823634811");
        pastCard.setActionPlayer("15823634800");
        pastCard.setCards(cardList);
        pastCard.setLastPlayer("15823634800");
        pastCard.setIsPass(Action.FALSE);

        List<Card> cards = game.findBigCards(pastCard, game.getDesk().getSeats().get(1).getCards());

//        List<Card> outCards = game.casuallyCards(game.getDesk().getSeats().get(0).getCards());
        System.out.println("");
    }

    @Test
    public void testeq() {
        Card card = new Card(3, CardColor.BLOCK);
        Card card1 = new Card(3, CardColor.BLOCK);
        System.out.println(card.equals(card1));
    }

    @Test
    public void find() {
        List<Card> cards_1 = new ArrayList<>();
        cards_1.add(new Card(3, CardColor.BLOCK));
        cards_1.add(new Card(4, CardColor.BLOCK));
        cards_1.add(new Card(5, CardColor.BLOCK));
        cards_1.add(new Card(6, CardColor.BLOCK));
        cards_1.add(new Card(7, CardColor.BLOCK));
        cards_1.add(new Card(8, CardColor.BLOCK));
        PastCard pastCard = new PastCard();
        pastCard.setCards(cards_1);

        List<Card> card_2 = new ArrayList<>();
        card_2.add(new Card(3, CardColor.HEART));
        card_2.add(new Card(4, CardColor.SPADE));
        card_2.add(new Card(5, CardColor.SPADE));
        card_2.add(new Card(6, CardColor.SPADE));
        card_2.add(new Card(7, CardColor.SPADE));
        card_2.add(new Card(7, CardColor.BLOCK));
        card_2.add(new Card(8, CardColor.BLOCK));
        card_2.add(new Card(9, CardColor.SPADE));
        card_2.add(new Card(10, CardColor.SPADE));
        card_2.add(new Card(10, CardColor.PLUM));
        card_2.add(new Card(10, CardColor.HEART));
        card_2.add(new Card(11, CardColor.BLOCK));
        card_2.add(new Card(11, CardColor.HEART));
        card_2.add(new Card(11, CardColor.BLOCK));
        card_2.add(new Card(11, CardColor.PLUM));
        card_2.add(new Card(16, CardColor.LITTLE_JOKER));
        card_2.add(new Card(16, CardColor.BIG_JOKER));

        Game game = new Game();
        List<Card> card_3 = game.findBigCards(pastCard, card_2);
        System.out.println("");
    }

    @Test
    public void tetestxs(){
        BigDecimal bigDecimal_1 = new BigDecimal(124.00);
        BigDecimal bigDecimal_2 = new BigDecimal(124);
        System.out.println(bigDecimal_1.compareTo(bigDecimal_2) < 0);
    }
}
