package chess.game.landlords.listener;

import chess.application.gamemultiple.IGameMultipleAppService;
import chess.application.gamerecord.IGameRecordAppService;
import chess.application.user.IApiUserAppService;
import chess.core.redis.RedisService;
import chess.game.landlords.mode.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by YJH
 * Date : 2016/3/9.
 */
public class LandlordsTcpService implements Runnable {

    private ServerSocket serverSocket;
    private boolean started = false;
    public static Map<String, LandlordsClient> userClients = new HashMap<>();
    static final Map<String, List<String>> deskList = new HashMap<>();
    public static final Map<String, Game> games = new HashMap<>();
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisService redisService;

    private final IApiUserAppService apiUserAppService;

    private final IGameRecordAppService gameRecordAppService;

    private final IGameMultipleAppService gameMultipleAppService;

    @Autowired
    public LandlordsTcpService(IGameRecordAppService gameRecordAppService, IApiUserAppService apiUserAppService, RedisService redisService, IGameMultipleAppService gameMultipleAppService) {
        this.gameRecordAppService = gameRecordAppService;
        this.apiUserAppService = apiUserAppService;
        this.redisService = redisService;
        this.gameMultipleAppService = gameMultipleAppService;
    }

    @Override
    public void run() {
        int port = 9091;
        boolean used = true;
        while (used) {
            try {
                serverSocket = new ServerSocket(port);
                started = true;
                used = false;
                logger.info("斗地主tcp开启成功，端口[" + port + "]");
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
                cachedThreadPool.execute(new LandlordsClient(s, redisService, apiUserAppService, gameRecordAppService,gameMultipleAppService));
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
