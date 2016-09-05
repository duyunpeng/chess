package chess.infrastructure.persistence.redis;


import chess.infrastructure.persistence.redis.generic.IRedisGenericRepository;

/**
 * Created by YJH on 2016/3/21.
 */
public interface IRedisRepository<K, V> extends IRedisGenericRepository<K, V> {
}
