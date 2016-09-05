package chess.domain.model.procedures;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by yjh
 * Date : 2016/8/14.
 */
public interface IProceduresRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
