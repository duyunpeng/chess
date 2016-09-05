package chess.application.robot;

import chess.application.robot.command.AddRobotCommand;

/**
 * Author pengyi
 * Date 16-8-24.
 */
public interface IRobotAppService {

    void add(AddRobotCommand command);
}
