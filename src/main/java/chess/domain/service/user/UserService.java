package chess.domain.service.user;

import chess.application.account.command.ResetPasswordCommand;
import chess.application.moneydetailed.command.CreateMoneyDetailedCommand;
import chess.application.picture.command.CreatePictureCommand;
import chess.application.shared.command.SharedCommand;
import chess.application.user.command.*;
import chess.core.common.BasicPaginationCommand;
import chess.core.common.PasswordHelper;
import chess.core.enums.AuthStatus;
import chess.core.enums.EnableStatus;
import chess.core.enums.FlowType;
import chess.core.enums.GameType;
import chess.core.exception.ExistException;
import chess.core.exception.NoFoundException;
import chess.core.upload.IFileUploadService;
import chess.core.util.CoreStringUtils;
import chess.domain.model.area.Area;
import chess.domain.model.picture.Picture;
import chess.domain.model.role.Role;
import chess.domain.model.user.IUserRepository;
import chess.domain.model.user.User;
import chess.domain.service.account.IAccountService;
import chess.domain.service.area.IAreaService;
import chess.domain.service.moneydetailed.IMoneyDetailedService;
import chess.domain.service.picture.IPictureService;
import chess.domain.service.role.IRoleService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.LockOptions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJH
 * Date : 2016/4/19.
 */
@Service("userService")
public class UserService implements IUserService {

    private final IUserRepository<User, String> userRepository;

    private final IAccountService accountService;

    private final IRoleService roleService;

    private final IAreaService areaService;

    private final IFileUploadService fileUploadService;

    private final IPictureService pictureService;

    @Autowired
    private IMoneyDetailedService moneyDetailedService;

    @Autowired
    public UserService(IFileUploadService fileUploadService, IRoleService roleService, IPictureService pictureService, IUserRepository<User, String> userRepository, IAreaService areaService, IAccountService accountService) {
        this.fileUploadService = fileUploadService;
        this.roleService = roleService;
        this.pictureService = pictureService;
        this.userRepository = userRepository;
        this.areaService = areaService;
        this.accountService = accountService;
    }


    @Override
    public Pagination<User> pagination(ListUserCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.asc("createDate"));
        return userRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public User searchByName(String userName) {
        return userRepository.searchByName(userName);
    }

    @Override
    public boolean checkDeviceNo(String deviceNo) {

        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("deviceNo", deviceNo));
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.asc("createDate"));

        return userRepository.list(criterionList, orderList).size() < 3;
    }

    @Override
    public User searchByName(String userName, LockOptions lockOptions) {
        return userRepository.searchByName(userName, lockOptions);
    }

    @Override
    public User searchByID(String id) {
        User user = userRepository.getById(id);
        if (null == user) {
            throw new NoFoundException("没有找到ID[" + id + "]的User数据");
        }
        return user;
    }

    @Override
    public User searchByID(String id, LockOptions lockOptions) {
        User user = userRepository.getById(id, LockOptions.READ);
        if (null == user) {
            throw new NoFoundException("没有找到ID[" + id + "]的User数据");
        }
        return user;
    }

    @Override
    public User create(CreateUserCommand command) {
        Area area = null;
        if (!CoreStringUtils.isEmpty(command.getArea())) {
            area = areaService.searchByID(command.getArea());
        }
        List<Role> roleList = roleService.searchByIDs(command.getRoles());
        if (null != accountService.searchByAccountName(command.getUserName())) {
            throw new ExistException("userName[" + command.getUserName() + "]的Account数据已存在");
        }
        String salt = PasswordHelper.getSalt();
        String password = PasswordHelper.encryptPassword(command.getPassword(), salt);
        User user = new User(command.getUserName(), password, salt, null, null, null, roleList, command.getEmail(), command.getStatus(),
                null, command.getName(), area, new BigDecimal(8), AuthStatus.NOT, null, null);
        userRepository.save(user);
        return user;
    }

    @Override
    public User edit(EditUserCommand command) {
        User user = this.searchByID(command.getId());
        user.fainWhenConcurrencyViolation(command.getVersion());
        Area area = null;
        if (!CoreStringUtils.isEmpty(command.getArea())) {
            area = areaService.searchByID(command.getArea());
        }
        user.changeName(command.getName());
        user.changeArea(area);
        user.changeEmail(command.getEmail());
        userRepository.update(user);
        return user;
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void addMoney(MoneyCommand command) {
        User user = this.searchByID(command.getId(), LockOptions.READ);
        user.changeMoney(user.getMoney().add(command.getMoney()));
        userRepository.update(user);

        //创建资金明细
        CreateMoneyDetailedCommand moneyDetailedCommand = new CreateMoneyDetailedCommand();
        moneyDetailedCommand.setDescription(command.getDescribe());
        moneyDetailedCommand.setFlowType(FlowType.IN_FLOW);
        moneyDetailedCommand.setMoney(command.getMoney());
        moneyDetailedCommand.setUserName(user.getUserName());
        moneyDetailedService.create(moneyDetailedCommand);
    }

    @Override
    public void subtractMoney(MoneyCommand command) {
        User user = this.searchByID(command.getId(), LockOptions.READ);
        user.changeMoney(user.getMoney().subtract(command.getMoney()));
        userRepository.update(user);

        //创建资金明细
        CreateMoneyDetailedCommand moneyDetailedCommand = new CreateMoneyDetailedCommand();
        moneyDetailedCommand.setDescription(command.getDescribe());
        moneyDetailedCommand.setFlowType(FlowType.OUT_FLOW);
        moneyDetailedCommand.setMoney(command.getMoney());
        moneyDetailedCommand.setUserName(user.getUserName());
        moneyDetailedService.create(moneyDetailedCommand);
    }

    @Override
    public void apiCreate(RegisterCommand command) {
        if (null != this.searchByName(command.getUserName())) {
            throw new ExistException("userName[" + command.getUserName() + "]的User数据已存在");
        }
        if (!this.checkDeviceNo(command.getUserName())) {
            throw new ExistException("deviceNo[" + command.getUserName() + "]注册次数过多");
        }
        List<Role> roles = new ArrayList<>();
        Role role = roleService.searchByName("user");
        roles.add(role);
        String salt = PasswordHelper.getSalt();
        String password = PasswordHelper.encryptPassword(command.getPassword(), salt);

        //默认头像
        Picture picture;
        picture = pictureService.searchByDescribes("默认用户头像图片");
        if (null == picture) {
            CreatePictureCommand pictureCommand = new CreatePictureCommand();
            pictureCommand.setDescribes("默认用户头像图片");
            String iconPath = fileUploadService.getDoMainName() + "/resources/images/default_user_head.png";
            pictureCommand.setName("default_user_head.png");
            pictureCommand.setPicPath(iconPath);
            pictureCommand.setMiniPicPath(iconPath);
            pictureCommand.setSize(0.0);
            picture = pictureService.create(pictureCommand);
        }
        User user = new User(command.getUserName(), password, salt, roles, picture, EnableStatus.ENABLE, command.getDeviceNo());
        userRepository.save(user);
    }

    @Override
    public void apiResetPassword(ResetPasswordCommand command) {
        User user = this.searchByName(command.getUserName());
        if (null == user) {
            throw new NoFoundException("账户不存在");
        }

        String password = PasswordHelper.encryptPassword(command.getPassword(), user.getSalt());
        user.changePassword(password);
        userRepository.update(user);
    }

    @Override
    public void apiResetPayPassword(ResetPasswordCommand command) {
        User user = this.searchByName(command.getUserName());
        if (null == user) {
            throw new NoFoundException("账户不存在");
        }
        String password = PasswordHelper.encryptPassword(command.getPassword(), user.getSalt());
        user.changePayPassword(password);
        userRepository.update(user);
    }

    @Override
    public void apiUpdateHeadPic(EditUserCommand command) {
        User user = this.searchByID(command.getId());
        Picture oldHeadPic = user.getHeadPic();

        user.changeHeadPic(null);

        CreatePictureCommand picCommand = fileUploadService.moveToImg(command.getHeadPic());
        picCommand.setDescribes("头像图片");
        Picture newHeadPic = pictureService.create(picCommand);

        user.changeHeadPic(newHeadPic);
        userRepository.update(user);

        if (null != oldHeadPic && !oldHeadPic.getDescribes().equals("默认用户头像图片")) {
            fileUploadService.deleteImg(oldHeadPic.getName());
            pictureService.delete(oldHeadPic.getId());
        }
    }

    @Override
    public void apiUpdateInfo(EditUserCommand command) {
        User user = this.searchByID(command.getId());
        Area area = areaService.searchByID(command.getArea());
        user.changeArea(area);
        user.changeName(command.getName());
        userRepository.update(user);
    }

    @Override
    public Pagination<User> apiRanking(RankingListCommand command) {
        List<Order> orderList = new ArrayList<>();
        if (command.getGameType() == GameType.LANDLORDS) {
            orderList.add(Order.asc("landlords.winCount"));
        } else if (command.getGameType() == GameType.THREE_CARD) {
            //TODO 金花记录
        }
        return userRepository.pagination(command.getPage(), command.getPageSize(), null, orderList);
    }

    @Override
    public Pagination<User> userRanking(BasicPaginationCommand command) {
        List<Criterion> criterionList = new ArrayList<>();

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("money"));
        return userRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public void updateVip(SharedCommand command) {
        User user = this.searchByID(command.getId());
        user.fainWhenConcurrencyViolation(command.getVersion());
        if (user.getVip()) {
            user.changeVip(false);
        } else {
            user.changeVip(true);
        }
        userRepository.update(user);
    }
}
