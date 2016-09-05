package chess.domain.model.user;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.LockOptions;

import java.io.Serializable;

/**
 * Created by YJH on 2016/4/15.
 */
public interface IUserRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
    User searchByName(String userName);

    User searchByName(String userName, LockOptions lockOptions);
}
