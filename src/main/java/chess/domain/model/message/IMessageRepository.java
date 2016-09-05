package chess.domain.model.message;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
public interface IMessageRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
