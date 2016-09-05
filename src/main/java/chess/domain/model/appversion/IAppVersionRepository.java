package chess.domain.model.appversion;

import chess.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by YJH on 2016/4/15.
 */
public interface IAppVersionRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
    AppVersion getByAppVersion(String appVersion) ;
}
