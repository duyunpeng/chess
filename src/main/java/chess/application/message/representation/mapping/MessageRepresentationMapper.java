package chess.application.message.representation.mapping;

import chess.application.message.representation.MessageRepresentation;
import chess.application.user.representation.UserRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.message.Message;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
@Component
public class MessageRepresentationMapper extends CustomMapper<Message, MessageRepresentation> {

    private final IMappingService mappingService;

    @Autowired
    public MessageRepresentationMapper(IMappingService mappingService) {
        this.mappingService = mappingService;
    }

    public void mapAtoB(Message message, MessageRepresentation representation, MappingContext context) {
        if (null != message.getReceiveUser()) {
            UserRepresentation data = mappingService.map(message.getReceiveUser(), UserRepresentation.class, false);
            representation.setReceiveUser(data);
        }
    }

}
