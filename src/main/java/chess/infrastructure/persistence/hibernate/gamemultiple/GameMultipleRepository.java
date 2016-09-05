package chess.infrastructure.persistence.hibernate.gamemultiple;

import chess.domain.model.gamemultiple.GameMultiple;
import chess.domain.model.gamemultiple.IGameMultipleRepository;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Author pengyi
 * Create date 16-7-19
 */
@Repository("gameMultipleRepository")
public class GameMultipleRepository extends AbstractHibernateGenericRepository<GameMultiple, String>
        implements IGameMultipleRepository<GameMultiple, String> {
}
