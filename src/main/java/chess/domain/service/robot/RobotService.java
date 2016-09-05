package chess.domain.service.robot;

import chess.application.robot.command.AddRobotCommand;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.robot.Robot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author pengyi
 * Date 16-8-24.
 */
@Service("robotervice")
public class RobotService implements IRobotService {

    private final RedisService redisService;

    @Autowired
    public RobotService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void add(AddRobotCommand command) {

        int j = 0;
        for (int i = 0; i < 10000; i++) {
            if (!redisService.exists("robot" + i)) {
                new Robot(command.getGameType(), "robot" + i, command.getScore()).start();
                redisService.addCache("robot" + i, "true", Constants.REDIS_GAME_TIME_OUT);
                j += 10;
                if (j == command.getCount()) {
                    return;
                }
            }
        }
    }
}
