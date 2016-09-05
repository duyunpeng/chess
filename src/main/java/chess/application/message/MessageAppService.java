package chess.application.message;

import chess.application.message.command.ListMessageCommand;
import chess.application.message.representation.MessageRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.message.Message;
import chess.domain.service.message.IMessageService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
@Service("messageAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MessageAppService implements IMessageAppService {

    private final IMessageService messageService;

    private final IMappingService mappingService;

    @Autowired
    public MessageAppService(IMappingService mappingService, IMessageService messageService) {
        this.mappingService = mappingService;
        this.messageService = messageService;
    }

    @Override
    public Pagination<MessageRepresentation> pagination(ListMessageCommand command) {
        command.verifyPage();
        command.verifyPageSize(20);
        Pagination<Message> pagination = messageService.pagination(command);
        List<MessageRepresentation> data = mappingService.mapAsList(pagination.getData(), MessageRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }
}
