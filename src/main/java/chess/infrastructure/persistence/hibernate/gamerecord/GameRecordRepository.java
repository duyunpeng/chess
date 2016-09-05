package chess.infrastructure.persistence.hibernate.gamerecord;

import chess.domain.model.gamerecord.GameRecord;
import chess.domain.model.gamerecord.IGameRecordRepository;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yjh on 16-7-5.
 */
@Repository("gameRecordRepository")
public class GameRecordRepository extends AbstractHibernateGenericRepository<GameRecord, String>
        implements IGameRecordRepository<GameRecord, String> {
}
