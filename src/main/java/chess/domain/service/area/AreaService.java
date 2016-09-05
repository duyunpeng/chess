package chess.domain.service.area;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chess.application.area.command.CreateAreaCommand;
import chess.application.area.command.EditAreaCommand;
import chess.application.area.command.ListAreaCommand;
import chess.application.shared.command.SharedCommand;
import chess.core.enums.AreaLevel;
import chess.core.enums.EnableStatus;
import chess.core.exception.NoFoundException;
import chess.core.util.CoreStringUtils;
import chess.domain.model.area.Area;
import chess.domain.model.area.IAreaRepository;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YJH on 2016/4/14.
 */
@Service("areaService")
public class AreaService implements IAreaService {

    @Autowired
    private IAreaRepository<Area, String> areaRepository;

    @Override
    public Pagination<Area> pagination(ListAreaCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (!CoreStringUtils.isEmpty(command.getName())) {
            criterionList.add(Restrictions.like("name", command.getName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus() && command.getStatus() != EnableStatus.ALL) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        if (null != command.getLevel() && command.getLevel() != AreaLevel.ALL) {
            criterionList.add(Restrictions.eq("level", command.getLevel()));
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.asc("level"));
        orderList.add(Order.asc("sort"));
        return areaRepository.pagination(command.getPage(), command.getPageSize(), criterionList, orderList);
    }

    @Override
    public Area searchByID(String id) {
        Area area = areaRepository.getById(id);
        if (null == area) {
            throw new NoFoundException("没有找到ID[" + id + "]的Area数据");
        }
        return area;
    }

    @Override
    public Area create(CreateAreaCommand command) {
        Area parent = null;
        if (!CoreStringUtils.isEmpty(command.getParent())) {
            parent = this.searchByID(command.getParent());
        }
        Area area = new Area(command.getName(), command.getShortName(),
                command.getLongitude(), command.getLatitude(), command.getLevel(), command.getStatus(), parent, command.getSort());
        areaRepository.save(area);
        return area;
    }

    @Override
    public Area edit(EditAreaCommand command) {
        Area parent = null;
        if (!CoreStringUtils.isEmpty(command.getParent())) {
            parent = this.searchByID(command.getParent());
        }
        Area area = this.searchByID(command.getId());
        area.fainWhenConcurrencyViolation(command.getVersion());
        area.changeName(command.getName());
        area.changeShortName(command.getShortName());
        area.changeLongitude(command.getLongitude());
        area.changeLatitude(command.getLatitude());
        area.changeLevel(command.getLevel());
        area.changeStatus(command.getStatus());
        area.changeSort(command.getSort());
        area.changeParent(parent);
        areaRepository.update(area);
        return area;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        Area area = this.searchByID(command.getId());
        area.fainWhenConcurrencyViolation(command.getVersion());
        if (area.getStatus() == EnableStatus.DISABLE) {
            area.changeStatus(EnableStatus.ENABLE);
        } else {
            area.changeStatus(EnableStatus.DISABLE);
        }
        areaRepository.update(area);
    }

    @Override
    public List<Area> list(ListAreaCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (CoreStringUtils.isEmpty(command.getParent())) {
            criterionList.add(Restrictions.isNull("parent"));
        } else {
            criterionList.add(Restrictions.eq("parent.id", command.getParent()));
        }
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.asc("level"));
        orderList.add(Order.asc("sort"));
        return areaRepository.list(criterionList, orderList);
    }
}
