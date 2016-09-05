package chess.domain.model.withdraw;


import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by pengyi on 2016/5/6.
 */
public interface IWithdrawRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
