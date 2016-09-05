package chess.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chess.infrastructure.persistence.redis.IRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by YJH on 2016/3/21.
 */
@Service("redisService")
public class RedisService {

    static private final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    private final IRedisRepository<String, String> redisRepository;

    @Autowired
    public RedisService(IRedisRepository<String, String> redisRepository) {
        this.redisRepository = redisRepository;
    }

    public boolean exists(final String key) {
        return redisRepository.exists(key);
    }

    public void addCache(final String key, final String value,final Integer time) {
        redisRepository.addCache(key, value, time, DEFAULT_TIME_UNIT);
    }

    public void addCaches(final String key, final List value,final Integer time) {
        redisRepository.addCache(key, value.toString(), time, DEFAULT_TIME_UNIT);
    }

    public String getCache(final String key) {
        return redisRepository.getCache(key);
    }

    public List getCaches(final String key) {
        String a = redisRepository.getCache(key);
        List<String> b = new ArrayList<>();
        b.add(a);
        return b;
    }

    public void delete(final String key) {
        if (redisRepository.exists(key)) {
            redisRepository.remove(key);
        }
    }

}
