package chess.infrastructure.persistence.hibernate.account;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import chess.domain.model.account.Account;
import chess.domain.model.account.IAccountRepository;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;

/**
 * Created by YJH on 2016/3/30.
 */
@Repository("accountRepository")
public class AccountRepository extends AbstractHibernateGenericRepository<Account, String>
        implements IAccountRepository<Account, String> {
    @Override
    public Account searchByAccountName(String userName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userName", userName));
        return (Account) criteria.uniqueResult();
    }
}
