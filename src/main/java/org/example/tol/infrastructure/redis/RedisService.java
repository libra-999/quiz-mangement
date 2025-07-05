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

    public void putHash(String channel, String key, String value);

    public String hash(String channel, String key);

    public void hashDelete(String channel, String key);

    public void hashDeleteAll(String channel, Set<String> key);

    public void expire(String key, Long time, TimeUnit timeUnit);

    public Long add(List<User> users, String key);

    public Long increment(String key);

    public Set<String> topPlayer(String channel, int topNumber);

    public Double score(String channel, String user);

    public Long rank(String channel, String userId);

    public void addToZSet(String channel, String member, int score);

}
