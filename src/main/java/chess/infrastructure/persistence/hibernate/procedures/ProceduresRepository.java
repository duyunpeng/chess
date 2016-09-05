package chess.infrastructure.persistence.hibernate.procedures;

import chess.domain.model.procedures.IProceduresRepository;
import chess.domain.model.procedures.Procedures;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yjh
 * Date : 2016/8/14.
 */
@Repository("proceduresRepository")
public class ProceduresRepository extends AbstractHibernateGenericRepository<Procedures,String> implements IProceduresRepository<Procedures,String> {
}
