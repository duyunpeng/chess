package chess.game.threecard.listener;

import chess.application.gamerecord.IGameRecordAppService;
import chess.application.user.IApiUserAppService;
import chess.core.redis.RedisService;
import chess.game.threecard.mode.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 斗地主
 * Created by pengyi on 2016/3/9.
 */
public class ThreeCardTcpService implements Runnable {

    private ServerSocket serverSocket;
    private boolean started = false;
    public static final Map<String, ThreeCardClient> userClients = new HashMap<>();
    static final Map<String, BigDecimal> deskNos = new HashMap<>();
    static final Map<String, Game> games = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisService redisService;
    private final IApiUserAppService apiUserAppService;
    private final IGameRecordAppService gameRecordAppService;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    public ThreeCardTcpService(IApiUserAppService apiUserAppService, RedisService redisService, IGameRecordAppService gameRecordAppService) {
        this.apiUserAppService = apiUserAppService;
        this.redisService = redisService;
        this.gameRecordAppService = gameRecordAppService;
    }

    @Override
    public void run() {
        int port = 9092;
        boolean used = true;
        while (used) {
            try {
                serverSocket = new ServerSocket(port);
                started = true;
                used = false;
                logger.info("扎金花tcp开启成功，端口[" + port + "]");
            } catch (BindException e) {
                port++;
            } catch (IOException e) {
                logger.error("socket.open.fail.message");
                e.printStackTrace();
                used = false;
            }
        }

        try {
            while (started) {
                Socket s = serverSocket.accept();
                cachedThreadPool.execute(new ThreeCardClient(s, redisService, apiUserAppService, gameRecordAppService));
            }
        } catch (IOException e) {
            logger.error("socket.server.dirty.shutdown.message");
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
