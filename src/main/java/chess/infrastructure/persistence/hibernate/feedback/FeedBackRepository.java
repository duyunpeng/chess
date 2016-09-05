package chess.infrastructure.persistence.hibernate.feedback;

import org.springframework.stereotype.Repository;
import chess.domain.model.feedback.FeedBack;
import chess.domain.model.feedback.IFeedBackRepository;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;

/**
 * Created by YJH on 2016/4/15.
 */
@Repository("feedBackRepository")
public class FeedBackRepository extends AbstractHibernateGenericRepository<FeedBack, String>
        implements IFeedBackRepository<FeedBack, String> {
}
