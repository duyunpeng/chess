package chess.infrastructure.persistence.redis.generic;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by YJH
 * Date : 2016/3/21.
 */
public interface IRedisGenericRepository<K, V> {

    long push(K key, V value);

    void addCache(final K key, final V value, final long timeout, final TimeUnit timeUnit);

    V getCache(final K key);

    V pop(K key, long timeout, TimeUnit unit);

    void remove(K key);

    void flushAll();

    boolean exists(final K key);

    long size(final K key);

    Set<K> keys(K key);
}
