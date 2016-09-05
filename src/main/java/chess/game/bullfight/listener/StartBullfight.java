package chess.game.bullfight.listener;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by dyp
 * Date : 16-6-20.
 */
public class StartBullfight {
    private Thread thread;

    @Autowired
    private BullfightTcpService tcpService;

    public void start() {
        thread = new Thread(tcpService);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

}
