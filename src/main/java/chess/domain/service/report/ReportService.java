package chess.domain.service.report;

import chess.application.shared.command.SharedCommand;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chess.application.report.command.CreateReportCommand;
import chess.application.report.command.EditReportCommand;
import chess.application.report.command.ListReportCommand;
import chess.core.enums.HandleStatus;
import chess.core.exception.NoFoundException;
import chess.core.util.CoreStringUtils;
import chess.domain.model.report.IReportRepository;
import chess.domain.model.report.Report;
import chess.domain.model.user.User;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.*;

/**
 * Created by LvDi on 2016/4/19.
 */

@Service("reportService")
public class ReportService implements IReportService {

    @Autowired
    private IReportRepository<Report, String> reportRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Pagination<Report> pagination(ListReportCommand command) {

        List<Criterion> criterionList = new ArrayList<Criterion>();
        Map<String, String> aliasMap = new HashMap<String, String>();
        if (!CoreStringUtils.isEmpty(command.getReportUser())) {
            criterionList.add(Restrictions.like("reportUser.userName", command.getReportUser(), MatchMode.ANYWHERE));
            aliasMap.put("reportUser","reportUser");
        }
        if (!CoreStringUtils.isEmpty(command.getQuiltReportUser())) {
            criterionList.add(Restrictions.like("quiltReportUser.userName", command.getQuiltReportUser(), MatchMode.ANYWHERE));
            aliasMap.put("quiltReportUser","quiltReportUser");
        }
        if (null != command.getHandleStatus()&&command.getHandleStatus()!=HandleStatus.ALL) {
            criterionList.add(Restrictions.eq("handleStatus", command.getHandleStatus()));
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createDate"));
        return reportRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public Report searchByID(String id) {
        Report report = reportRepository.getById(id);
        if (null == report) {
            throw new NoFoundException("没有找到ID[" + id + "]的Report数据");
        }
        return report;
    }

    @Override
    public void updateHandleStatus(SharedCommand command) {
        Report report = this.searchByID(command.getId());
        report.fainWhenConcurrencyViolation(command.getVersion());
        if (report.getHandleStatus() == HandleStatus.WAIT_HANDLE) {
            report.changeHandleStatus(HandleStatus.IN_HANDLE);
        }
        reportRepository.update(report);

    }

    @Override
    public void finishReport(EditReportCommand command) {
        Report report = this.searchByID(command.getId());
        if (report.getHandleStatus() == HandleStatus.IN_HANDLE) {
            report.changeHandleStatus(HandleStatus.OK_HANDLE);
            report.changeHandleDate(new Date());
            report.changeHandleResult(command.getHandleResult());
        }
        reportRepository.update(report);
    }

    /**
     * 由用户举报
     */
    @Override
    public Report apiCreate(CreateReportCommand command) {

        User reportUser = userService.searchByName(command.getReportUser());

        User quiltReportUser = userService.searchByName(command.getQuiltReportUser());
        Report report = new Report(reportUser, quiltReportUser, command.getTitle(), command.getContent(),
                command.getPictures(), HandleStatus.WAIT_HANDLE, null, null);
        reportRepository.save(report);
        return report;
    }
}
