package chess.infrastructure.persistence.hibernate.withhold;

import chess.domain.model.withhold.IWithholdRepository;
import chess.domain.model.withhold.Withhold;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yjh on 16-7-9.
 */
@Repository("withholdRepository")
public class WithholdRepository extends AbstractHibernateGenericRepository<Withhold, String>
        implements IWithholdRepository<Withhold, String> {
}
