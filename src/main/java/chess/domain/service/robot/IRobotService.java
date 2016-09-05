package chess.domain.service.robot;

import chess.application.robot.command.AddRobotCommand;

/**
 * Author pengyi
 * Date 16-8-24.
 */
public interface IRobotService {

    void add(AddRobotCommand command);
}
