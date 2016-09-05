package chess.game.robot;

import chess.core.enums.GameType;
import chess.game.landlords.command.PushObject;
import chess.game.landlords.command.ReceiveObject;
import chess.game.landlords.mode.Seat;
import chess.game.landlords.mode.push.PushCallLandlords;
import chess.game.landlords.mode.push.PushStartLandlords;
import chess.game.threecard.mode.Card;
import chess.game.threecard.mode.CardType;
import chess.game.threecard.mode.Game;
import chess.game.threecard.push.LookResponse;
import chess.game.threecard.push.OperationResponse;
import chess.game.threecard.push.StartResponse;
import com.alibaba.fastjson.JSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by pengyi on 2016/3/10.
 */
public class Robot {

    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;
    private GameType gameType;
    private String user;
    private BigDecimal score;
    private int seatNo;
    private List<Seat> seats;
    private int count = 0;
    private int compare = 0;
    private BigDecimal minScore;

    public Robot(GameType gameType, String user, BigDecimal score) {

        this.gameType = gameType;
        this.user = user;
        this.score = score;
        try {
            int port = 0;
            switch (gameType) {
                case LANDLORDS:
                    port = 9091;
                    break;
                case THREE_CARD:
                    port = 9092;
                    break;
                default:
                    break;
            }
//            s = new Socket("w.173600.com", port);
            s = new Socket("127.0.0.1", port);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            bConnected = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {

        ReceiveObject send = new ReceiveObject();
        switch (gameType) {
            case LANDLORDS:
                send.setType(20001);
                send.setData("{\"user\":\"" + user + "\",\"baseScore\":" + score + "}");
                send(JSON.toJSONString(send), false);
                break;
            case THREE_CARD:
                send.setType(1);
                send.setData("{\"userName\":\"" + user + "\",\"multiple\":" + score + "}");
                send(JSON.toJSONString(send), false);

                break;
        }


        new Thread(() -> {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    switch (gameType) {
                        case LANDLORDS:
                            PushObject pushObject = JSON.parseObject(str, PushObject.class);
                            switch (pushObject.getType()) {
                                case 21001:
                                    PushStartLandlords startLandlords = JSON.parseObject(pushObject.getData().toString(), PushStartLandlords.class);
                                    startLandlords.getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user))
                                            .forEach(seat -> this.seatNo = seat.getSeatNo());
                                    seats = startLandlords.getSeats();
                                    if (startLandlords.getRandomLandlordPlayer().equals(user)) {
                                        send.setType(20003);
                                        send.setData("{\"callLandlordScore\":0}");
                                        send(JSON.toJSONString(send), true);
                                    }

                                    break;

                                case 21003:
                                    PushCallLandlords callLandlords = JSON.parseObject(pushObject.getData().toString(), PushCallLandlords.class);
                                    final int[] seat1 = new int[1];
                                    seats.stream().filter(seat -> callLandlords.getPlayer().equals(seat.getPlayer().getUserName()))
                                            .forEach(seat -> seat1[0] = seat.getSeatNo());

                                    seat1[0] = (seat1[0] + 1) == 3 ? 0 : seat1[0] + 1;
                                    if (seat1[0] == seatNo) {
                                        send.setType(20003);
                                        send.setData("{\"callLandlordScore\":0}");
                                        send(JSON.toJSONString(send), true);
                                    }
                                    break;
                                case 21005:
                                    send.setType(20005);
                                    send.setData("{\"isDouble\":false}");
                                    send(JSON.toJSONString(send), true);
                                    return;

                                case 21007:
                                    return;
                            }
                            break;
                        case THREE_CARD:
                            chess.game.threecard.push.PushObject pushObject1 = JSON.parseObject(str, chess.game.threecard.push.PushObject.class);
                            switch (pushObject1.getType()) {
                                case 1:
                                    count = 0;
                                    compare = 0;
                                    StartResponse startResponse = JSON.parseObject(pushObject1.getData().toString(), StartResponse.class);
                                    startResponse.getSeats().stream().filter(seat -> seat.getUserName().equals(user)).forEach(seat -> seatNo = seat.getSeatNo());
                                    if (startResponse.getOperationSeat() == 0) {
                                        send.setType(8);
                                        send.setData(null);
                                        send(JSON.toJSONString(send), true);
                                    }
                                    break;
                                case 2:
                                    LookResponse lookResponse = JSON.parseObject(pushObject1.getData().toString(), LookResponse.class);
                                    if (seatNo == lookResponse.getSeatNo()) {
                                        List<Card> cards = lookResponse.getCards();
                                        Game game = new Game();
                                        CardType cardType = game.getCardType(cards);
                                        minScore = minScore.multiply(new BigDecimal("2"));
                                        switch (cardType) {
                                            case SINGLE:
                                                send.setType(5);
                                                send.setData(null);
                                                break;
                                            case DOUBLE:
                                                send.setType(4);
                                                send.setData("{\"otherSeatNo\":0}");
                                                count++;
                                                break;
                                            case STRAIGHT:
                                                send.setType(3);
                                                send.setData("{\"score\":" + minScore + "}");
                                                count++;
                                                compare = 3;
                                                break;
                                            case SAME_COLOR:
                                                send.setType(3);
                                                send.setData("{\"score\":" + minScore + "}");
                                                count++;
                                                compare = 5;
                                                break;
                                            case FLUSH:
                                                send.setType(3);
                                                send.setData("{\"score\":" + minScore + "}");
                                                count++;
                                                compare = 8;
                                                break;
                                            case LEOPARD:
                                                send.setType(3);
                                                send.setData("{\"score\":" + minScore + "}");
                                                count++;
                                                compare = 15;
                                                break;
                                        }
                                        send(JSON.toJSONString(send), true);
                                    }
                                    break;
                                case 9:
                                    OperationResponse operationResponse = JSON.parseObject(pushObject1.getData().toString(), OperationResponse.class);
                                    minScore = operationResponse.getMinScore();
                                    if (operationResponse.getSeatNo() == seatNo) {
                                        if (3 == count) {
                                            send.setType(2);
                                            send.setData(null);
                                        } else if (count < 3 + compare) {
                                            send.setType(3);
                                            send.setData("{\"score\":" + operationResponse.getMinScore() + "}");
                                            count++;
                                        } else {
                                            send.setType(4);
                                            send.setData("{\"otherSeatNo\":0}");
                                        }
                                        send(JSON.toJSONString(send), true);
                                    }
                                    break;

                                case 6:
                                    send.setType(8);
                                    send.setData(null);
                                    send(JSON.toJSONString(send), true);
                                    break;
                            }
                            break;
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    public void disconnect() {
        try {
            bConnected = false;
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(String str, boolean wait) {
        try {
            if (wait) {
                synchronized (this) {
                    try {
                        this.wait(2000);
                        dos.writeUTF(str);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                dos.writeUTF(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
