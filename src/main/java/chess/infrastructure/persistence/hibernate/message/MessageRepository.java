package chess.infrastructure.persistence.hibernate.message;

import chess.domain.model.message.IMessageRepository;
import chess.domain.model.message.Message;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
@Repository("messageRepository")
public class MessageRepository extends AbstractHibernateGenericRepository<Message, String>
        implements IMessageRepository<Message, String> {
}
