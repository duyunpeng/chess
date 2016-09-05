package chess.application.feedback.representation.mapping;

import ma.glasnost.orika.CustomMapper;
import org.springframework.stereotype.Component;
import chess.application.feedback.representation.FeedBackRepresentation;
import chess.domain.model.feedback.FeedBack;


/**
 * Created by Administrator on 2016/4/21.
 */
@Component
public class FeedBackRepresentationMapper extends CustomMapper<FeedBack, FeedBackRepresentation> {
}
