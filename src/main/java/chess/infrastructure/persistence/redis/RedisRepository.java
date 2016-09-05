package chess.infrastructure.persistence.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import chess.infrastructure.persistence.redis.generic.AbstractRedisGenericRepository;

/**
 * Created by YJH
 * Date : 2016/3/21.
 */
@Repository("redisRepository")
public class RedisRepository extends AbstractRedisGenericRepository<String,String> implements IRedisRepository<String,String> {

    @Autowired
    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }
}
