package chess.application.robot;

import chess.application.robot.command.AddRobotCommand;
import chess.domain.service.robot.IRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author pengyi
 * Date 16-8-24.
 */
@Service("robotAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RobotAppService implements IRobotAppService {

    private final IRobotService robotService;

    @Autowired
    public RobotAppService(IRobotService robotService) {
        this.robotService = robotService;
    }

    @Override
    public void add(AddRobotCommand command) {
        robotService.add(command);
    }
}
