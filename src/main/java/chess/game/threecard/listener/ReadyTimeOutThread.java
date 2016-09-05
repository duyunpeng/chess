package chess.game.threecard.listener;

import chess.game.threecard.mode.Game;
import chess.game.threecard.mode.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Author pengyi
 * Create date 16-7-13
 */
class ReadyTimeOutThread extends Thread {

    private String deskNo;
    private int seat;
    private String userName;
    private Logger logger;
    private Date startDate;

    ReadyTimeOutThread(String deskNo, int seat, String userName, Date startDate) {
        this.deskNo = deskNo;
        this.seat = seat;
        this.userName = userName;
        this.startDate = startDate;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(18000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ThreeCardTcpService.games.containsKey(deskNo)) {
            synchronized (ThreeCardTcpService.games.get(deskNo)) {
                Game game = ThreeCardTcpService.games.get(deskNo);
                ThreeCardClient cardClient = ThreeCardTcpService.userClients.get(userName);
                if (null != cardClient && startDate.compareTo(game.getDesk().getStartDate()) == 0) {
                    for (Seat seat : game.getDesk().getSeats()) {
                        if (seat.getSeatNo() == this.seat && !seat.isReady()) {
                            logger.info(userName + "准备超时");
                            cardClient.exit();
                            break;
                        }
                    }
                }
            }
        }
    }
}
