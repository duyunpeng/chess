package chess.infrastructure.persistence.hibernate.area;

import org.springframework.stereotype.Repository;
import chess.domain.model.area.Area;
import chess.domain.model.area.IAreaRepository;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;

/**
 * Created by YJH on 2016/4/14.
 */
@Repository("areaRepository")
public class AreaRepository extends AbstractHibernateGenericRepository<Area, String>
        implements IAreaRepository<Area, String> {
}
