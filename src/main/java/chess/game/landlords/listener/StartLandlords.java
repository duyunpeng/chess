package chess.game.landlords.listener;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by YJH
 * Date : 16-6-13.
 */
public class StartLandlords {

    private Thread thread;

    private final LandlordsTcpService tcpService;

    @Autowired
    public StartLandlords(LandlordsTcpService tcpService) {
        this.tcpService = tcpService;
    }

    public void start() {
        thread = new Thread(tcpService);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

}
