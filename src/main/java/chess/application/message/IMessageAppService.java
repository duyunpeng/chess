package chess.application.message;

import chess.application.message.command.ListMessageCommand;
import chess.application.message.representation.MessageRepresentation;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
public interface IMessageAppService {
    Pagination<MessageRepresentation> pagination(ListMessageCommand command);
}
