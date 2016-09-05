package chess.application.user;

import chess.application.shared.command.SharedCommand;
import chess.application.user.command.MoneyCommand;
import chess.application.user.command.CreateUserCommand;
import chess.application.user.command.EditUserCommand;
import chess.application.user.command.ListUserCommand;
import chess.application.user.representation.UserRepresentation;
import chess.core.mapping.IMappingService;
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
 * Date : 2016/4/19.
 */
@Service("userAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserAppService implements IUserAppService {

    private final IUserService userService;

    private final IMappingService mappingService;

    @Autowired
    public UserAppService(IUserService userService, IMappingService mappingService) {
        this.userService = userService;
        this.mappingService = mappingService;
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<UserRepresentation> pagination(ListUserCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        Pagination<User> pagination = userService.pagination(command);
        List<UserRepresentation> data = mappingService.mapAsList(pagination.getData(), UserRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public UserRepresentation searchByID(String id) {
        return mappingService.map(userService.searchByID(id), UserRepresentation.class, false);
    }

    @Override
    public UserRepresentation create(CreateUserCommand command) {
        return mappingService.map(userService.create(command), UserRepresentation.class, false);
    }

    @Override
    public UserRepresentation edit(EditUserCommand command) {
        return mappingService.map(userService.edit(command), UserRepresentation.class, false);
    }

    @Override
    public void addMoney(MoneyCommand command) {
        userService.addMoney(command);
    }

    @Override
    public void subtractMoney(MoneyCommand command) {
        userService.subtractMoney(command);
    }

    @Override
    public void updateVip(SharedCommand command) {
        userService.updateVip(command);
    }
}
