package chess.infrastructure.persistence.hibernate.permission;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import chess.domain.model.permission.IPermissionRepository;
import chess.domain.model.permission.Permission;
import chess.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;

/**
 * Created by YJH on 2016/3/30.
 */
@Repository("permissionRepository")
public class PermissionRepository extends AbstractHibernateGenericRepository<Permission, String>
        implements IPermissionRepository<Permission, String> {
    @Override
    public Permission searchByName(String name) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", name));
        return (Permission) criteria.uniqueResult();
    }
}
