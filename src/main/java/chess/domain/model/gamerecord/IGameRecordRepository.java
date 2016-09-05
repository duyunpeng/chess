package chess.domain.model.gamerecord;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.Serializable;

/**
 * Created by yjh on 16-7-5.
 */
public interface IGameRecordRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
