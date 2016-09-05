package chess.application.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import chess.application.feedback.command.CreateFeedBackCommand;
import chess.application.feedback.command.EditFeedbackCommand;
import chess.application.feedback.command.ListFeedbackCommand;
import chess.application.feedback.representation.FeedBackRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.core.mapping.IMappingService;
import chess.domain.model.feedback.FeedBack;
import chess.domain.service.feedback.IFeedBackService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
@Service("feedBackAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FeedBackAppService implements IFeedBackAppService{
    @Autowired
    private IFeedBackService feedBackService;

    @Autowired
    private IMappingService mappingService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<FeedBackRepresentation> pagination(ListFeedbackCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        Pagination<FeedBack> pagination = feedBackService.pagination(command);
        List<FeedBackRepresentation> data = mappingService.mapAsList(pagination.getData(),FeedBackRepresentation.class);
        return new Pagination<FeedBackRepresentation>(data,pagination.getCount(),pagination.getPage(),pagination.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public FeedBackRepresentation searchByID(String id) {
        return mappingService.map(feedBackService.searchByID(id),FeedBackRepresentation.class,false);
    }

    @Override
    public FeedBackRepresentation create(CreateFeedBackCommand command) {
        return mappingService.map(feedBackService.create(command),FeedBackRepresentation.class,false);
    }

    @Override
    public FeedBackRepresentation edit(EditFeedbackCommand command) {
        return mappingService.map(feedBackService.edit(command),FeedBackRepresentation.class,false);
    }

    @Override
    public void updateStatus(SharedCommand command) {
        feedBackService.updateStatus(command);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedBackRepresentation> listJSON(ListFeedbackCommand command) {
        return mappingService.mapAsList(feedBackService.list(command),FeedBackRepresentation.class);
    }
}
