package org.example.tol.infrastructure.redis;

import org.example.tol.infrastructure.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    public void publish(String channel, String message);

    public void delete(String channel);

    public String getValue(String channel);

    public void getValue(String channel, String value, long time, TimeUnit timeUnit);

    public Map<String, String> hash(String channel);

    public String hash(String channel, String message);

    public void hashDelete(String channel, String key);

    public void hashDeleteAll(String channel, Set<String> key);

    public void expire(String key, Long time, TimeUnit timeUnit);

    public Long add(List<User> users, String key);

    public Long increment(String key);

}
