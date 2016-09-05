package chess.application.gamemultiple;

import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.domain.service.gamemultiple.IGameMultipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author pengyi
 * Create date 16-7-19
 */
@Service("apiGameMultipleAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ApiGameMultipleAppService implements IApiGameMultipleAppService {

    private final IGameMultipleService gameMultipleService;

    @Autowired
    public ApiGameMultipleAppService(IGameMultipleService gameMultipleService) {
        this.gameMultipleService = gameMultipleService;
    }

    @Override
    public ApiResponse list(ListGameMultipleCommand command) {
        if (null == command.getGameType()) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "gameType字段不能为空", null);
        }
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), gameMultipleService.list(command));
    }
}
