package chess.domain.model.withhold;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by yjh on 16-7-9.
 */
public interface IWithholdRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
