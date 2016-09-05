package chess.domain.service.feedback;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chess.application.feedback.command.CreateFeedBackCommand;
import chess.application.feedback.command.EditFeedbackCommand;
import chess.application.feedback.command.ListFeedbackCommand;
import chess.application.shared.command.SharedCommand;
import chess.core.enums.HandleStatus;
import chess.core.exception.NoFoundException;
import chess.core.util.CoreStringUtils;
import chess.domain.model.feedback.FeedBack;
import chess.domain.model.feedback.IFeedBackRepository;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyp on 2016/4/19.
 */
@Service("feedBackService")
public class FeedBackService implements IFeedBackService{

    @Autowired
    private IFeedBackRepository<FeedBack,String> feedBackRepository;

    @Override
    public Pagination<FeedBack> pagination(ListFeedbackCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if(!CoreStringUtils.isEmpty(command.getEmail())){
            criterionList.add(Restrictions.like("email",command.getEmail(),MatchMode.ANYWHERE));
        }
        if (!CoreStringUtils.isEmpty(command.getPhone())){
            criterionList.add(Restrictions.like("phone",command.getPhone(),MatchMode.ANYWHERE));
        }
        if (!CoreStringUtils.isEmpty(command.getQq())){
            criterionList.add(Restrictions.like("qq",command.getQq(),MatchMode.ANYWHERE));
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.asc("createDate"));
        return feedBackRepository.pagination(command.getPage(),command.getPageSize(),criterionList,orderList);
    }

    @Override
    public FeedBack searchByID(String id) {
        FeedBack feedBack = feedBackRepository.getById(id);
        if(null == feedBack){
            throw new NoFoundException("没有找到ID["+id+"]的FeedBack数据");
        }
        return feedBack;
    }

    @Override
    public FeedBack create(CreateFeedBackCommand command) {
        FeedBack feedBack = new FeedBack(command.getEmail(),command.getPhone(),command.getQq(),command.getContent(),command.getStatus());
        feedBackRepository.save(feedBack);
        return feedBack;
    }

    @Override
    public FeedBack apiCreate(CreateFeedBackCommand command) {
        FeedBack feedBack = new FeedBack(command.getEmail(),command.getPhone(),command.getQq(),command.getContent(),HandleStatus.WAIT_HANDLE);
        feedBackRepository.save(feedBack);
        return feedBack;
    }

    @Override
    public FeedBack edit(EditFeedbackCommand command) {
        FeedBack feedBack = this.searchByID(command.getId());
        feedBack.fainWhenConcurrencyViolation(command.getVersion());
        feedBack.changeEmail(command.getEmail());
        feedBack.changePhone(command.getPhone());
        feedBack.changeQq(command.getQq());
        feedBack.changeContent(command.getContent());
        feedBack.changeStatus(command.getStatus());
        feedBackRepository.update(feedBack);
        return feedBack;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        FeedBack feedBack = this.searchByID(command.getId());

        feedBack.fainWhenConcurrencyViolation(command.getVersion());
        if (feedBack.getStatus()== HandleStatus.WAIT_HANDLE){
            feedBack.changeStatus(HandleStatus.IN_HANDLE);
        }else if(feedBack.getStatus()==HandleStatus.IN_HANDLE){
            feedBack.changeStatus(HandleStatus.OK_HANDLE);
        }
        feedBackRepository.update(feedBack);
    }

    @Override
    public List<FeedBack> list(ListFeedbackCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.asc("createData"));
        return feedBackRepository.list(criterionList,orderList);
    }


}
