package chess.game.threecard.listener;

import chess.game.threecard.mode.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Author pengyi
 * Create date 16-7-13
 */
class PlayTimeOutThread extends Thread {

    private String deskNo;
    private int seat;
    private String userName;
    private Date date;
    private Logger logger;

    PlayTimeOutThread(String deskNo, int seat, String userName, Date date) {
        this.deskNo = deskNo;
        this.seat = seat;
        this.userName = userName;
        this.date = date;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(16000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ThreeCardTcpService.games.containsKey(deskNo)) {
            Game game = ThreeCardTcpService.games.get(deskNo);
            ThreeCardClient cardClient = ThreeCardTcpService.userClients.get(userName);
            if (game.getDesk().getOperationSeat() == seat && null != cardClient && game.getDesk().getDate().compareTo(date) == 0) {
                logger.info(userName + "超时");
                if (!cardClient.end(seat)) {
                    cardClient.operation(game.getNext(seat));
                }
            }
        }
    }
}
