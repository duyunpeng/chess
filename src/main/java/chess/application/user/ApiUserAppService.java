package chess.application.user;

import chess.application.account.command.ResetPasswordCommand;
import chess.application.shared.command.SharedCommand;
import chess.application.user.command.EditUserCommand;
import chess.application.user.command.RankingListCommand;
import chess.application.user.command.RegisterCommand;
import chess.application.user.representation.ApiUserRepresentation;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.common.BasicPaginationCommand;
import chess.core.mapping.IMappingService;
import chess.core.redis.RedisService;
import chess.core.util.CoreStringUtils;
import chess.domain.model.user.User;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YJH
 * Date : 2016/4/28.
 */
@Service("apiUserAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ApiUserAppService implements IApiUserAppService {

    private final IMappingService mappingService;

    private final IUserService userService;

    private final RedisService redisService;

    @Autowired
    public ApiUserAppService(RedisService redisService, IMappingService mappingService, IUserService userService) {
        this.redisService = redisService;
        this.mappingService = mappingService;
        this.userService = userService;
    }

    @Override
    public ApiUserRepresentation searchByUserName(String userName) {
        return mappingService.map(userService.searchByName(userName), ApiUserRepresentation.class, false);
    }

    @Override
    public ApiResponse register(RegisterCommand command) {
        if (CoreStringUtils.isEmpty(command.getUserName())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "userName字段不能为空", null);
        }
        if (command.getUserName().startsWith("robot")) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "非法用户名", null);
        }
        if (CoreStringUtils.isEmpty(command.getPassword())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "password字段不能为空", null);
        }
        if (CoreStringUtils.isEmpty(command.getDeviceNo())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "deviceNo字段不能为空", null);
        }
        userService.apiCreate(command);
        redisService.delete(command.getUserName());
        return new ApiResponse(ApiReturnCode.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse info(SharedCommand command) {
        if (CoreStringUtils.isEmpty(command.getId())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "id字段不能为空", null);
        }
        ApiUserRepresentation data = mappingService.map(userService.searchByID(command.getId()), ApiUserRepresentation.class, false);
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), data);
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordCommand command) {
        if (CoreStringUtils.isEmpty(command.getUserName())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "用户名(userName)不能为空", null);
        }
        if (CoreStringUtils.isEmpty(command.getPassword())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "密码(password)不能为空", null);
        }
        userService.apiResetPassword(command);
        return new ApiResponse(ApiReturnCode.SUCCESS);
    }

    @Override
    public ApiResponse resetPayPassword(ResetPasswordCommand command) {
        if (CoreStringUtils.isEmpty(command.getUserName())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "用户名(userName)不能为空", null);
        }
        if (CoreStringUtils.isEmpty(command.getPassword())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "支付密码(password)不能为空", null);
        }
        userService.apiResetPayPassword(command);
        return new ApiResponse(ApiReturnCode.SUCCESS);
    }

    @Override
    public ApiResponse updateHeadPic(EditUserCommand command) {
        if (CoreStringUtils.isEmpty(command.getHeadPic())) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "头像(headPic)不能为空", null);
        }
        userService.apiUpdateHeadPic(command);
        return new ApiResponse(ApiReturnCode.SUCCESS);
    }

    @Override
    public ApiResponse updateInfo(EditUserCommand command) {
        userService.apiUpdateInfo(command);
        return new ApiResponse(ApiReturnCode.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse ranking(RankingListCommand command) {
        if (null == command.getGameType()) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, "游戏类型(gameType)不能为空", null);
        }
        Pagination<User> pagination = userService.apiRanking(command);
        List<ApiUserRepresentation> data = mappingService.mapAsList(pagination.getData(), ApiUserRepresentation.class);
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize()));
    }

    @Override
    public ApiResponse userRanking(BasicPaginationCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        Pagination<User> pagination = userService.userRanking(command);
        List<ApiUserRepresentation> data = mappingService.mapAsList(pagination.getData(), ApiUserRepresentation.class);
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(),
                new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize()));
    }
}
