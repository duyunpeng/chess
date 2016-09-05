package chess.domain.service.message;

import chess.application.message.command.ListMessageCommand;
import chess.domain.model.message.Message;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
public interface IMessageService {
    Pagination<Message> pagination(ListMessageCommand command);
}
