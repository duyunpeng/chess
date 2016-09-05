package chess.infrastructure.persistence.hibernate.withdraw;

import chess.domain.model.withdraw.IWithdrawRepository;
import chess.domain.model.withdraw.Withdraw;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/5/6.
 */
@Repository("withdrawRepository")
public class WithdrawRepository extends AbstractHibernateGenericRepository<Withdraw, String>
        implements IWithdrawRepository<Withdraw, String> {
}
