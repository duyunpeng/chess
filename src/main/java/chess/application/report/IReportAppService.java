package chess.application.report;

import chess.application.report.command.CreateReportCommand;
import chess.application.report.command.EditReportCommand;
import chess.application.report.command.ListReportCommand;
import chess.application.report.representation.ReportRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by LvDi on 2016/4/19.
 */
public interface IReportAppService {
    Pagination<ReportRepresentation> pagination(ListReportCommand command);

    ReportRepresentation searchByID(String id);

    ReportRepresentation create(CreateReportCommand command);

    void updateHandleStatus(SharedCommand command);

    void finishReport(EditReportCommand command);
}
