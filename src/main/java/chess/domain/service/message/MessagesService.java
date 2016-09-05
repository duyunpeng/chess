package chess.domain.service.message;

import chess.application.message.command.ListMessageCommand;
import chess.domain.model.message.IMessageRepository;
import chess.domain.model.message.Message;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
@Service("messageService")
public class MessagesService implements IMessageService {

    private final IMessageRepository<Message, String> messageRepository;

    private final IUserService userService;

    @Autowired
    public MessagesService(IUserService userService, IMessageRepository<Message, String> messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Pagination<Message> pagination(ListMessageCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> alias = new HashMap<>();

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return messageRepository.pagination(command.getPage(), command.getPageSize(), criterionList, alias, orderList, null);
    }
}
