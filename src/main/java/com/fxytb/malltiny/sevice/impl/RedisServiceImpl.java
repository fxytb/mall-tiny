package com.fxytb.malltiny.sevice.impl;

import cn.hutool.core.collection.CollUtil;
import com.fxytb.malltiny.sevice.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> opsForValue;
    private final HashOperations<String, Object, Object> opsForHash;
    private final SetOperations<String, Object> opsForSet;
    private final ListOperations<String, Object> opsForList;
    private final ZSetOperations<String, Object> opsForZSet;

    private RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForValue = redisTemplate.opsForValue();
        this.opsForHash = redisTemplate.opsForHash();
        this.opsForSet = redisTemplate.opsForSet();
        this.opsForList = redisTemplate.opsForList();
        this.opsForZSet = redisTemplate.opsForZSet();
    }


    /**
     * 保存属性并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    @Override
    public void set(String key, Object value, long time) {
        opsForValue.set(key, value, Duration.ofMillis(time));
    }

    /**
     * 保存属性
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, Object value) {
        opsForValue.set(key, value);
    }

    /**
     * 获取属性
     *
     * @param key
     * @return value
     */
    @Override
    public Object get(String key) {
        return opsForValue.get(key);
    }

    /**
     * 删除属性
     *
     * @param key
     * @return 是否删除成功
     */
    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     * @return 删除数据条数
     */
    @Override
    public Long del(String... keys) {
        return redisTemplate.delete(CollUtil.toList(keys));
    }

    /**
     * 设置失效时间
     *
     * @param key
     * @param time
     * @return 是否设置成功
     */
    @Override
    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, Duration.ofMillis(time));
    }

    /**
     * 获取过期时间
     *
     * @param key
     * @return 过期时间
     */
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return 是否存在key
     */
    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 按delta自增
     *
     * @param key
     * @param delta
     * @return 返回更新后的值
     */
    @Override
    public Long incr(String key, long delta) {
        return opsForValue.increment(key, delta);
    }

    /**
     * 按delta递减
     *
     * @param key
     * @param delta
     * @return 返回更新后的值
     */
    @Override
    public Long decr(String key, long delta) {
        return opsForValue.decrement(key, delta);
    }

    /**
     * 使用hash结构设置属性值
     *
     * @param key
     * @param hashKey
     * @param value
     */
    @Override
    public void hSet(String key, String hashKey, Object value) {
        opsForHash.put(key, hashKey, value);
    }

    /**
     * 使用hash结构设置属性值并设置过期时间
     *
     * @param key
     * @param hashKey
     * @param value
     * @param time
     */
    @Override
    public void hSet(String key, String hashKey, Object value, long time) {
        opsForHash.put(key, hashKey, value);
        redisTemplate.expire(key, Duration.ofMillis(time));
    }

    /**
     * 批量设置hash结构属性值
     *
     * @param key
     * @param map
     * @return 是否设置成功
     */
    @Override
    public void hSetAll(String key, Map<String, Object> map) {
        opsForHash.putAll(key, map);
    }

    /**
     * 批量设置hash结构属性值并对hash结构设置过期时间
     *
     * @param key
     * @param map
     * @param time
     * @return 是否设置成功
     */
    @Override
    public void hSetAll(String key, Map<String, Object> map, long time) {
        opsForHash.putAll(key, map);
        redisTemplate.expire(key, Duration.ofMillis(time));
    }

    /**
     * 获取hash结构中的属性值
     *
     * @param key
     * @param hashKey
     * @return hash结构中的属性值
     */
    @Override
    public Object hGet(String key, String hashKey) {
        return opsForHash.get(key, hashKey);
    }

    /**
     * 获取hash结构中全部属性值
     *
     * @param key
     * @return hash结构中全部属性值
     */
    @Override
    public Map<Object, Object> hGetAll(String key) {
        return opsForHash.entries(key);
    }

    /**
     * 删除hash结构中属性值
     *
     * @param key
     * @param keys
     */
    @Override
    public void hDel(String key, Object... keys) {
        opsForHash.delete(key, CollUtil.toList(keys));
    }

    /**
     * 判断hash结构中是否存在key
     *
     * @param key
     * @param hashKey
     * @return hash结构中是否存在指定key
     */
    @Override
    public Boolean hHasKey(String key, String hashKey) {
        return opsForHash.hasKey(key, hashKey);
    }

    /**
     * 对hash结构中的属性值进行自增
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return 返回hash结构中自增后的值
     */
    @Override
    public Long hIncr(String key, String hashKey, long delta) {
        return opsForHash.increment(key, hashKey, delta);
    }

    /**
     * 对hash结构中的属性值进行自减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return 返回hash结构中自减后的值
     */
    @Override
    public Long hDecr(String key, String hashKey, long delta) {
        return opsForHash.increment(key, hashKey, -delta);
    }

    /**
     * 向set结构中设置值
     *
     * @param key
     * @param values
     * @return 返回设置条数
     */
    @Override
    public Long sSet(String key, Object... values) {
        return opsForSet.add(key, values);
    }

    /**
     * 向set结构中设置值并设置过期时间
     *
     * @param key
     * @param time
     * @param values
     * @return 返回设置条数
     */
    @Override
    public Long sSet(String key, long time, Object... values) {
        Long add = opsForSet.add(key, values);
        redisTemplate.expire(key, Duration.ofMillis(time));
        return add;
    }

    /**
     * 获取set结构数据
     *
     * @param key
     * @return set结构数据
     */
    @Override
    public Set<Object> sGet(String key) {
        return opsForSet.members(key);
    }

    /**
     * 判断值是否存在于set结构中
     *
     * @param key
     * @param value
     * @return 值是否存在于set结构中
     */
    @Override
    public Boolean sIsExistsValue(String key, Object value) {
        return opsForSet.isMember(key, value);
    }

    /**
     * 获取set结构长度
     *
     * @param key
     * @return set结构长度
     */
    @Override
    public Long sSize(String key) {
        return opsForSet.size(key);
    }

    /**
     * 删除set结构中的值
     *
     * @param key
     * @param values
     * @return 删除数据条数
     */
    @Override
    public Long sRemove(String key, Object... values) {
        return opsForSet.remove(key, values);
    }

    /**
     * 向list结构中设置值
     *
     * @param key
     * @param value
     * @return 执行条数
     */
    @Override
    public Long lPush(String key, Object value) {
        return opsForList.leftPush(key, value);
    }

    /**
     * 向list结构中设置值并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return 执行条数
     */
    @Override
    public Long lPush(String key, Object value, long time) {
        Long l = opsForList.leftPush(key, value);
        redisTemplate.expire(key, Duration.ofMillis(time));
        return l;
    }

    /**
     * 向list结构中批量设置值
     *
     * @param key
     * @param values
     * @return 执行条数
     */
    @Override
    public Long lPushAll(String key, Object... values) {
        return opsForList.leftPushAll(key, values);
    }

    /**
     * 向list结构中批量设置值并设置过期时间
     *
     * @param key
     * @param time
     * @param values
     * @return 执行条数
     */
    @Override
    public Long lPushAll(String key, long time, Object... values) {
        Long l = opsForList.leftPushAll(key, values);
        redisTemplate.expire(key, Duration.ofMillis(time));
        return l;
    }

    /**
     * 获取list结构中指定位置的值
     *
     * @param key
     * @param index
     * @return list结构中的值
     */
    @Override
    public Object lIndex(String key, long index) {
        return opsForList.index(key, index);
    }

    /**
     * 范围获取list结构中的值
     *
     * @param key
     * @param start
     * @param end
     * @return list结构中的值
     */
    @Override
    public List<Object> lRange(String key, long start, long end) {
        return opsForList.range(key, start, end);
    }

    /**
     * 移除list结构中的属性
     *
     * @param key
     * @param count
     * @param value
     * @return 执行条数
     */
    @Override
    public Long lRemove(String key, long count, Object value) {
        return opsForList.remove(key, count, value);
    }

    /**
     * 获取list结构长度
     *
     * @param key
     * @return list结构长度
     */
    @Override
    public Long lSize(String key) {
        return opsForList.size(key);
    }
}
