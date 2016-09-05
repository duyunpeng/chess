package chess.domain.service.account;


import chess.application.account.command.*;
import chess.application.auth.command.LoginCommand;
import chess.application.picture.command.CreatePictureCommand;
import chess.application.shared.command.SharedCommand;
import chess.core.common.PasswordHelper;
import chess.core.enums.EnableStatus;
import chess.core.exception.ExistException;
import chess.core.exception.NoFoundException;
import chess.core.upload.IFileUploadService;
import chess.core.util.CoreHttpUtils;
import chess.core.util.CoreStringUtils;
import chess.domain.model.account.Account;
import chess.domain.model.account.IAccountRepository;
import chess.domain.model.ip.TaoBaoIPResponse;
import chess.domain.model.picture.Picture;
import chess.domain.model.role.Role;
import chess.domain.model.user.User;
import chess.domain.service.picture.IPictureService;
import chess.domain.service.role.IRoleService;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by YJH on 2016/3/30.
 */
@Service("accountService")
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository<Account, String> accountRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPictureService pictureService;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IUserService userService;

    @Override
    public Pagination<Account> pagination(ListAccountCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.asc("createDate"));
        return accountRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public List<Account> list(ListAccountCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return accountRepository.list(criterionList, orderList);
    }

    @Override
    public Account searchByID(String id) {
        Account account = accountRepository.getById(id);
        if (null == account) {
            throw new NoFoundException("没有找到ID[" + id + "]的Account数据");
        }
        return account;
    }

    @Override
    public Account searchByAccountName(String userName) {
        return accountRepository.searchByAccountName(userName);
    }

    @Override
    public Account create(CreateAccountCommand command) {
        List<Role> roleList = roleService.searchByIDs(command.getRoles());
        if (null != this.searchByAccountName(command.getUserName())) {
            throw new ExistException("userName[" + command.getUserName() + "]的Account数据已存在");
        }
        String salt = PasswordHelper.getSalt();
        String password = PasswordHelper.encryptPassword(command.getPassword(), salt);
        for (Role item : roleList) {
            if (item.getName().equals("admin")) {
                Account account = new Account(command.getUserName(), password, salt, null, null, null, roleList, command.getEmail(),
                        command.getStatus(), null, "");
                accountRepository.save(account);
                return account;
            }
        }
        User user = new User(command.getUserName(), password, salt, roleList, null, command.getStatus(), null);
        userService.save(user);
        return user;
    }

    @Override
    public Account edit(EditAccountCommand command) {
        Account account = this.searchByID(command.getId());
        account.fainWhenConcurrencyViolation(command.getVersion());
        account.changeEmail(command.getEmail());
        accountRepository.update(account);
        return account;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        Account account = this.searchByID(command.getId());
        account.fainWhenConcurrencyViolation(command.getVersion());
        if (account.getStatus() == EnableStatus.DISABLE) {
            account.changeStatus(EnableStatus.ENABLE);
        } else {
            account.changeStatus(EnableStatus.DISABLE);
        }
        accountRepository.update(account);
    }

    @Override
    public void resetPassword(ResetPasswordCommand command) {
        Account account = this.searchByID(command.getId());
        account.fainWhenConcurrencyViolation(command.getVersion());
        String password = PasswordHelper.encryptPassword(command.getPassword(), account.getSalt());
        account.changePassword(password);
        accountRepository.update(account);
    }

    @Override
    public void authorized(AuthorizeAccountCommand command) {
        List<Role> roleList = roleService.searchByIDs(command.getRoles());
        Account account = this.searchByID(command.getId());
        account.fainWhenConcurrencyViolation(command.getVersion());
        account.changeRoles(roleList);
        accountRepository.update(account);
    }

    @Override
    public Account login(LoginCommand command) {
        Account account = this.searchByAccountName(command.getUserName());
        if (null == account) {
            throw new UnknownAccountException();
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                command.getUserName(),
                command.getPassword());
        subject.login(token);

        account.changeLastLoginIP(command.getLoginIP());
        account.changeLastLoginPlatform(command.getLoginPlatform());
        account.changeLastLoginDate(new Date());
        String area = CoreHttpUtils.urlConnection("http://ip.taobao.com/service/getIpInfo.php?ip=" + command.getLoginIP(), "");
        if (null != area && !area.isEmpty()) {
            area = new String(area.getBytes(), Charset.forName("GBK"));
            TaoBaoIPResponse response = JSON.parseObject(area, TaoBaoIPResponse.class);
            if (0 == response.getCode() && null != response.getData() && !response.getData().getCity().isEmpty()) {
                account.setAreaString(response.getData().getCity());
            }
        }
        accountRepository.update(account);

        return account;
    }

    @Override
    public List<Account> searchByIDs(List<String> ids) {
        return ids.stream().map(this::searchByID).collect(Collectors.toList());
    }

    @Override
    public List<Account> searchByRoleIDs(List<String> ids) {
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.in("role.id", ids));
        Map<String, String> alias = new HashMap<>();
        alias.put("roles", "role");
        return accountRepository.list(criterionList, null, null, null, alias);
    }

    @Override
    public void updateHeadPic(UpdateHeadPicCommand command) {
        Account account = this.searchByID(command.getId());
        Picture oldHeadPic = account.getHeadPic();

        CreatePictureCommand picCommand = fileUploadService.moveToImg(command.getHandPic());
        picCommand.setDescribes("头像图片");
        Picture newHeadPic = pictureService.create(picCommand);

        account.changeHeadPic(newHeadPic);
        accountRepository.update(account);

        if (null != oldHeadPic) {
            fileUploadService.deleteImg(oldHeadPic.getName());
            pictureService.delete(oldHeadPic.getId());
        }
    }
}
