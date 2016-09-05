package chess.domain.service.gamemultiple;

import chess.application.gamemultiple.command.EditGameMultipleCommand;
import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.core.exception.NoFoundException;
import chess.domain.model.gamemultiple.GameMultiple;
import chess.domain.model.gamemultiple.IGameMultipleRepository;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Author pengyi
 * Create date 16-7-19
 */
@Service("gameMultipleService")
public class GameMultipleService implements IGameMultipleService {

    private final IGameMultipleRepository<GameMultiple, String> gameMultipleRepository;

    @Autowired
    public GameMultipleService(IGameMultipleRepository<GameMultiple, String> gameMultipleRepository) {
        this.gameMultipleRepository = gameMultipleRepository;
    }

    @Override
    public Pagination<GameMultiple> pagination(ListGameMultipleCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if (null != command.getGameType()) {
            criterionList.add(Restrictions.eq("gameType", command.getGameType()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return gameMultipleRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public void create(EditGameMultipleCommand command) {
        GameMultiple gameMultiple = new GameMultiple(command.getGameType(), command.getMultiple(), command.getMinMoney());
        gameMultipleRepository.save(gameMultiple);
    }

    @Override
    public GameMultiple byId(String id) {

        GameMultiple gameMultiple = gameMultipleRepository.getById(id);

        if (null == gameMultiple) {
            throw new NoFoundException("没有找到ID[" + id + "]的gameMultiple数据");
        }
        return gameMultiple;
    }

    @Override
    public void edit(EditGameMultipleCommand command) {
        GameMultiple gameMultiple = this.byId(command.getId());
        gameMultiple.fainWhenConcurrencyViolation(command.getVersion());
        gameMultiple.setGameType(command.getGameType());
        gameMultiple.setMultiple(command.getMultiple());
        gameMultiple.setMinMoney(command.getMinMoney());
        gameMultipleRepository.save(gameMultiple);
    }

    @Override
    public void delete(String id) {
        GameMultiple gameMultiple = this.byId(id);
        gameMultipleRepository.delete(gameMultiple);
    }

    @Override
    public List<GameMultiple> list(ListGameMultipleCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("gameType", command.getGameType()));
        if (null != command.getMultiple() && command.getMultiple().compareTo(new BigDecimal(0)) > 0) {
            criterionList.add(Restrictions.eq("multiple", command.getMultiple()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.asc("multiple"));
        return gameMultipleRepository.list(criterionList, orderList);
    }
}
