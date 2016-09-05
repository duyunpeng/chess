package chess.game.bullfight.listener;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.application.user.IApiUserAppService;
import chess.core.redis.RedisService;
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

/**
 * Created by dyp on 2016/6/20.
 * 斗牛
 */
public class BullfightTcpService implements Runnable {
    public static Map<String, BullfightClient> userClients = new HashMap<>();
    public static final Map<String, List<String>> deskList_10 = new HashMap<>();
    public static final Map<String, List<String>> deskList_20 = new HashMap<>();
    public static final Map<String, List<String>> deskList_30 = new HashMap<>();
    public static final Map<String, List<String>> deskList_40 = new HashMap<>();
    boolean started = false;
    private ServerSocket serverSocket;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Autowired
    private IApiUserAppService apiUserAppService;

    @Autowired
    private IBullfightRecordAppService bullfightRecordAppService;

    @Override
    public void run() {
        int port = 9001;
        boolean used = true;
        while (used) {
            try {
                serverSocket = new ServerSocket(port);
                started = true;
                used = false;
                logger.info("斗牛tcp开启成功，端口[" + port + "]");
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
                new Thread(new BullfightClient(s, redisService, apiUserAppService,bullfightRecordAppService)).start();
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
