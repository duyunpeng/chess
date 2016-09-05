package chess.game.threecard.listener;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author pengyi
 * Create date 16-6-22
 */
public class StartGame {

    private Thread threeCardThread;

    private final ThreeCardTcpService threeCardTcpService;

    @Autowired
    public StartGame(ThreeCardTcpService threeCardTcpService) {
        this.threeCardTcpService = threeCardTcpService;
    }

    public void start() {
        threeCardThread = new Thread(threeCardTcpService);
        threeCardThread.start();
    }

    public void stop() {
        threeCardThread.interrupt();
    }

}
