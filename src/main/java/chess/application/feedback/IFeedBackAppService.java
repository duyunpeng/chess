package chess.application.feedback;


import chess.application.feedback.command.CreateFeedBackCommand;
import chess.application.feedback.command.EditFeedbackCommand;
import chess.application.feedback.command.ListFeedbackCommand;
import chess.application.feedback.representation.FeedBackRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface IFeedBackAppService {
    Pagination<FeedBackRepresentation> pagination(ListFeedbackCommand command);

    FeedBackRepresentation searchByID(String id);

    FeedBackRepresentation create(CreateFeedBackCommand command);

    FeedBackRepresentation edit(EditFeedbackCommand command);

    void updateStatus(SharedCommand command);

    List<FeedBackRepresentation> listJSON(ListFeedbackCommand command);
}
