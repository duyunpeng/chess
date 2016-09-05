package chess.infrastructure.persistence.hibernate.picture;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import chess.domain.model.picture.IPictureRepository;
import chess.domain.model.picture.Picture;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;

/**
 * Created by YJH on 2016/4/12.
 */
@Repository("pictureRepository")
public class PictureRepository extends AbstractHibernateGenericRepository<Picture, String>
        implements IPictureRepository<Picture, String> {
    @Override
    public Picture searchByDescribes(String describes) {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.eq("describes", describes));
        return (Picture) criteria.uniqueResult();
    }
}
