package chess.domain.service.report;

import chess.application.report.command.CreateReportCommand;
import chess.application.report.command.EditReportCommand;
import chess.application.report.command.ListReportCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.report.Report;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface IReportService {
    Pagination<Report> pagination(ListReportCommand command);

    Report searchByID(String id);

    void updateHandleStatus(SharedCommand command);

    void finishReport(EditReportCommand command);

    /***********Api 方法 **************/

    Report apiCreate(CreateReportCommand command);

}
