package chess.infrastructure.persistence.hibernate.recharge;

import chess.domain.model.recharge.IRechargeRepository;
import chess.domain.model.recharge.Recharge;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yjh on 16-7-9.
 */
@Repository("rechargeRepository")
public class RechargeRepository extends AbstractHibernateGenericRepository<Recharge, String>
        implements IRechargeRepository<Recharge, String> {
}
