package chess.domain.model.recharge;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by yjh on 16-7-9.
 */
public interface IRechargeRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
