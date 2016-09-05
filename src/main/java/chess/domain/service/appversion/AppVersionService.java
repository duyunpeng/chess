package chess.domain.service.appversion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chess.application.appversion.command.CreateAppVersionCommand;
import chess.application.appversion.command.EditAppVersionCommand;
import chess.application.appversion.command.ListAppVersionCommand;
import chess.application.shared.command.SharedCommand;
import chess.core.enums.EnableStatus;
import chess.core.exception.ExistException;
import chess.core.exception.NoFoundException;
import chess.domain.model.appversion.AppVersion;
import chess.domain.model.appversion.IAppVersionRepository;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LvDi on 2016/4/19.
 */
@Service("appVersionService")
public class AppVersionService implements IAppVersionService {

    @Autowired
    private IAppVersionRepository<AppVersion, String> appVersionRepository;

    @Override
    public Pagination<AppVersion> pagination(ListAppVersionCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (null != command.getAppVersion()) {
            criterionList.add(Restrictions.like("appVersion", command.getAppVersion(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createDate"));
        return appVersionRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }



    @Override
    public List<AppVersion> allList() {
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createDate"));
        return appVersionRepository.list(null,orderList);
    }



    @Override
    public AppVersion searchByID(String id) {
        AppVersion appVersion = appVersionRepository.getById(id);
        if (null == appVersion) {
            throw new NoFoundException("没有找到ID[" + id + "]的AppVersion版本");
        }
        return appVersion;
    }

    @Override
    public AppVersion create(CreateAppVersionCommand command) {

        if (null != appVersionRepository.getByAppVersion(command.getAppVersion())) {
            throw new ExistException("appVersion[" + command.getAppVersion() + "]的AppVersion数据已存在");
        }
        AppVersion appVersion = new AppVersion(command.getAppVersion(), new Date(), command.getUpdateContent(), command.getStatus());
        appVersionRepository.save(appVersion);
        return appVersion;
    }


    @Override
    public AppVersion edit(EditAppVersionCommand command) {
        AppVersion  appVersion = this.searchByID(command.getId());
        if (null==appVersion){
            throw new NoFoundException("没有找到ID[" + command.getId() + "]的AppVersion版本");
        }
        appVersion.fainWhenConcurrencyViolation(command.getVersion());
        appVersion.changeAppVersion(command.getAppVersion());
        appVersion.changeUpdateContent(command.getUpdateContent());
        appVersion.changeStatus(command.getStatus());
        appVersionRepository.update(appVersion);
        return appVersion;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        AppVersion appVersion = this.searchByID(command.getId());
        appVersion.fainWhenConcurrencyViolation(command.getVersion());
        if (appVersion.getStatus() == EnableStatus.ENABLE) {
            appVersion.changeStatus(EnableStatus.DISABLE);
        } else {
            appVersion.changeStatus(EnableStatus.ENABLE);
        }
        appVersionRepository.update(appVersion);
    }


}
