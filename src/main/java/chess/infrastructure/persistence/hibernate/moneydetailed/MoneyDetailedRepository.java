package chess.infrastructure.persistence.hibernate.moneydetailed;

import chess.domain.model.moneydetailed.IMoneyDetailedRepository;
import chess.domain.model.moneydetailed.MoneyDetailed;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yjh on 16-7-9.
 */
@Repository("moneyDetailedRepository")
public class MoneyDetailedRepository extends AbstractHibernateGenericRepository<MoneyDetailed, String>
        implements IMoneyDetailedRepository<MoneyDetailed, String> {
}
