package chess.application.gamemultiple;

import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.core.api.ApiResponse;

/**
 * Author pengyi
 * Create date 16-7-19
 */
public interface IApiGameMultipleAppService {

    ApiResponse list(ListGameMultipleCommand command);

}
