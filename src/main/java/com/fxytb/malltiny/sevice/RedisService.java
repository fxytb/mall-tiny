package com.fxytb.malltiny.sevice;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    /**
     * 保存属性并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);


    /**
     * 获取属性
     *
     * @param key
     * @return value
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key
     * @return 是否删除成功
     */
    Boolean del(String key);


    /**
     * 批量删除key
     *
     * @param keys
     * @return 删除数据条数
     */
    Long del(String... keys);


    /**
     * 设置失效时间
     *
     * @param key
     * @param time
     * @return 是否设置成功
     */
    Boolean expire(String key, long time);


    /**
     * 获取过期时间
     *
     * @param key
     * @return 过期时间
     */
    Long getExpire(String key);

    /**
     * 判断是否存在key
     *
     * @param key
     * @return 是否存在key
     */
    Boolean hasKey(String key);


    /**
     * 按delta自增
     *
     * @param key
     * @param delta
     * @return 返回更新后的值
     */
    Long incr(String key, long delta);


    /**
     * 按delta递减
     *
     * @param key
     * @param delta
     * @return 返回更新后的值
     */
    Long decr(String key, long delta);


    /**
     * 使用hash结构设置属性值
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 使用hash结构设置属性值并设置过期时间
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value, long time);

    /**
     * 批量设置hash结构属性值
     *
     * @param key
     * @param map
     * @return 是否设置成功
     */
    void hSetAll(String key, Map<String, Object> map);


    /**
     * 批量设置hash结构属性值并对hash结构设置过期时间
     *
     * @param key
     * @param map
     * @param time
     * @return 是否设置成功
     */
    void hSetAll(String key, Map<String, Object> map, long time);


    /**
     * 获取hash结构中的属性值
     *
     * @param key
     * @param hashKey
     * @return hash结构中的属性值
     */
    Object hGet(String key, String hashKey);

    /**
     * 获取hash结构中全部属性值
     *
     * @param key
     * @return hash结构中全部属性值
     */
    Map<Object, Object> hGetAll(String key);


    /**
     * 删除hash结构中属性值
     *
     * @param key
     * @param keys
     */
    void hDel(String key, Object... keys);


    /**
     * 判断hash结构中是否存在key
     *
     * @param key
     * @param hashKey
     * @return hash结构中是否存在指定key
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * 对hash结构中的属性值进行自增
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return 返回hash结构中自增后的值
     */
    Long hIncr(String key, String hashKey, long delta);

    /**
     * 对hash结构中的属性值进行自减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return 返回hash结构中自减后的值
     */
    Long hDecr(String key, String hashKey, long delta);


    /**
     * 向set结构中设置值
     *
     * @param key
     * @param values
     * @return 返回设置条数
     */
    Long sSet(String key, Object... values);

    /**
     * 向set结构中设置值并设置过期时间
     *
     * @param key
     * @param time
     * @param values
     * @return 返回设置条数
     */
    Long sSet(String key, long time, Object[] values);

    /**
     * 获取set结构数据
     *
     * @param key
     * @return set结构数据
     */
    Set<Object> sGet(String key);

    /**
     * 判断值是否存在于set结构中
     *
     * @param key
     * @param value
     * @return 值是否存在于set结构中
     */
    Boolean sIsExistsValue(String key, Object value);


    /**
     * 获取set结构长度
     *
     * @param key
     * @return set结构长度
     */
    Long sSize(String key);


    /**
     * 删除set结构中的值
     *
     * @param key
     * @param values
     * @return 删除数据条数
     */
    Long sRemove(String key, Object... values);


    /**
     * 向list结构中设置值
     *
     * @param key
     * @param value
     * @return 执行条数
     */
    Long lPush(String key, Object value);

    /**
     * 向list结构中设置值并设置过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return 执行条数
     */
    Long lPush(String key, Object value, long time);


    /**
     * 向list结构中批量设置值
     *
     * @param key
     * @param values
     * @return 执行条数
     */
    Long lPushAll(String key, Object... values);


    /**
     * 向list结构中批量设置值并设置过期时间
     *
     * @param key
     * @param time
     * @param values
     * @return 执行条数
     */
    Long lPushAll(String key, long time, Object... values);


    /**
     * 获取list结构中指定位置的值
     *
     * @param key
     * @param index
     * @return list结构中的值
     */
    Object lIndex(String key, long index);


    /**
     * 范围获取list结构中的值
     *
     * @param key
     * @param start
     * @param end
     * @return list结构中的值
     */
    List<Object> lRange(String key, long start, long end);


    /**
     * 移除list结构中的属性
     *
     * @param key
     * @param count
     * @param value
     * @return 执行条数
     */
    Long lRemove(String key, long count, Object value);


    /**
     * 获取list结构长度
     *
     * @param key
     * @return list结构长度
     */
    Long lSize(String key);


}
