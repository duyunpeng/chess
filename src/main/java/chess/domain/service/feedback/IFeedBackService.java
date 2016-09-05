package chess.domain.service.feedback;

import chess.application.feedback.command.CreateFeedBackCommand;
import chess.application.feedback.command.EditFeedbackCommand;
import chess.application.feedback.command.ListFeedbackCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.feedback.FeedBack;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface IFeedBackService {
    Pagination<FeedBack> pagination(ListFeedbackCommand command);

    FeedBack searchByID(String id);

    FeedBack create(CreateFeedBackCommand command);

    FeedBack edit(EditFeedbackCommand command);

    void updateStatus(SharedCommand command);

    List<FeedBack> list(ListFeedbackCommand command);

    /***********Api 方法 **************/

    FeedBack apiCreate(CreateFeedBackCommand command);
}
