package chess.domain.model.gamemultiple;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Author pengyi
 * Create date 16-7-19
 */
public interface IGameMultipleRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
