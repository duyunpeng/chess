package chess.interfaces.app.web;

import chess.application.gamemultiple.IApiGameMultipleAppService;
import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.api.controller.BaseApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author pengyi
 * Create date 16-7-20
 */
@Controller
@RequestMapping("/app/api/game_multiple")
public class ApiAppGameMultipleController extends BaseApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IApiGameMultipleAppService apiGameMultipleAppService;

    @Autowired
    public ApiAppGameMultipleController(IApiGameMultipleAppService apiGameMultipleAppService) {
        this.apiGameMultipleAppService = apiGameMultipleAppService;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public ApiResponse list(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            ListGameMultipleCommand command = this.authenticationAndConvert(verificationCommand, ListGameMultipleCommand.class);
            apiResponse = apiGameMultipleAppService.list(command);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }
}
