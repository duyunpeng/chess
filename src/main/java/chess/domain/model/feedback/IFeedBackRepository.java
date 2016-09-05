package chess.domain.model.feedback;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by YJH on 2016/4/15.
 */
public interface IFeedBackRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
