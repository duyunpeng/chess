package chess.domain.model.moneydetailed;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by YJH
 * Date : 2016/3/9.
 */
public interface IMoneyDetailedRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
