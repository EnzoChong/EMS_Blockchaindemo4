package com.sap.ems.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil
{
    /**
     * JedisCluster和 jedisPool.getResource()类似，与JedisCluster差别在于jedisPool.getResource()采用默认的getBytes(),
     * 以下实现统一采用jedisPool.getResource()
     */
    private static JedisPool jedisPool;
    /**
     * 单例类，类初始化时，完成实例化 类在创建的同时，已经创建好一个对象供外部使用，以后不再改变，所以天生是线程安全的。
     */
    private static final RedisUtil INSTANCE = new RedisUtil();

    private RedisUtil()
    {
        if (INSTANCE == null)
        {
            init();
        }
    }

    public static RedisUtil getInstence()
    {
        return INSTANCE;
    }

    /**
     * 初始化jedisPool.getResource()
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: init
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static void init()
    {
        //初始化节点信息
        System.out.println("===========start to get hostAndPort==========");
        //设置Jedis连接池信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(300);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(600);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(10);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPool = new JedisPool(jedisPoolConfig, "10.11.241.63", 43661, 6000, "k3-NgMzYbuzfZ0xl");

        System.out.println("=========jedisPool.getResource() start========================");
    }

    /**
     * 设置key过期时间
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: keyTimeOut
     * @param key
     * @param seconds
     * @return 设置成功返回1 ，key不存在或者不能为 key设置过期时间时返回 0
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long keyTimeOut(String key, int seconds)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.expire(key.getBytes(), seconds);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 以时间戳设置过期时间
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: keyTimeOutByMilliseconds
     * @param key
     * @param milliseconds
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long keyTimeOutByMilliseconds(String key, long unixTime)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.expireAt(key.getBytes(), unixTime);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 取得key的过期时间
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: getTimeOfKey
     * @param key
     * @return 距离过期还有多少秒
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long getTimeOfKey(String key)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.ttl(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 移除key的过期时间
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: rmKeyTimeOut
     * @param key
     * @return 过期时间移除成功返回 1，key 不存在或 key 没有设置过期时间，返回 0
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long rmKeyTimeOut(String key)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.persist(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 判断key是否存在
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: existsKey
     * @param key
     * @return 设置成功返回1 ，key不存在或者不能为 key设置过期时间时返回 0
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public boolean existsKey(String key)
    {
        BinaryJedis binaryJedis = null;
        boolean result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.exists(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 仅当 newkey不存在时,将 key改名为 newkey.
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: renameKey
     * @param key
     * @param newKey
     * @return 修改成功时,返回 1
     * @exception:
     * @version: 1.0
     * @description: key和newKey需要在一个slot上，如name，{name}new，以括号内内容hash 否则会引起CROSSSLOT Keys in request don't hash to the
     *               same slot update_version: update_date: update_author: update_note:
     */
    public long renameKey(String key, String newKey)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.renamenx(key.getBytes(), newKey.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 删除key
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: delKey
     * @param key
     * @return 返回删除key的数量
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long delKey(String key)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.del(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 获取map，set，list，zset，String的长度，未传type返还0
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: getLength
     * @param key
     * @param type
     *            想要获取长度的类型,map请传：1;set请传:2;list请传:3;zset请传:4;String请传:5
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long getLength(String key, int type)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            switch (type)
            {
                case 1:
                {
                    result = binaryJedis.hlen(key.getBytes());
                    break;
                }
                case 2:
                {
                    result = binaryJedis.scard(key.getBytes());
                    break;
                }
                case 3:
                {
                    result = binaryJedis.hlen(key.getBytes());
                    break;
                }
                case 4:
                {
                    result = binaryJedis.zcard(key.getBytes());
                    break;
                }
                case 5:
                {
                    result = binaryJedis.strlen(key.getBytes());
                    break;
                }
                default:
                {
                    result = 0;
                }
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /***************** String 操作 ********************/

    /**
     * 设置key和value
     * 
     * @date: 2017年5月19日
     * @author: chenhao
     * @title: set
     * @param key
     * @param value
     * @param nx
     *            key不存在时，是否新建key，新建true，不新建false
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void set(String key, String value, boolean nx)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            if (true == nx)
            {
                binaryJedis.set(key.getBytes(), value.getBytes());
            }
            else
            {
                binaryJedis.setnx(key.getBytes(), value.getBytes());
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }
    //	public void set(){
    //		jedisPool.getResource().set(key, value, nxxx, expx, time)
    //	}

    /**
     * 根据key，取得String的value值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: get
     * @param key
     * @return String类型的value值
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public String get(String key)
    {
        BinaryJedis binaryJedis = null;
        String result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = new String(jedisPool.getResource().get(key.getBytes()));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 根据key，返还截取的字符串
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: getrange
     * @param key
     * @param start
     * @param end
     * @exception:
     * @version: 1.0
     * @description: 如：value值=chen hao 。start=1，end，2 返回 he update_version: update_date: update_author: update_note:
     */
    public String getrange(String key, long start, long end)
    {
        BinaryJedis binaryJedis = null;
        String result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = new String(jedisPool.getResource().getrange(key.getBytes(), start, end));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 用value覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: setTrange
     * @param key
     * @param offset
     * @param value
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void setrange(String key, long offset, String value)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            binaryJedis.setrange(key.getBytes(), offset, value.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 取得旧值，set新值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: getOldSetNew
     * @param key
     * @param newValue
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public String getOldSetNew(String key, String newValue)
    {
        BinaryJedis binaryJedis = null;
        String result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = new String(jedisPool.getResource().getSet(key.getBytes(), newValue.getBytes()));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * set对应偏移量的值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: setBit
     * @param key
     * @param offset
     * @param value
     * @return key所在偏移量原持有的值，true and false
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public boolean setBit(String key, long offset, boolean value)
    {
        BinaryJedis binaryJedis = null;
        Boolean result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.setbit(key.getBytes(), offset, value);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 取得偏移量位值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: getBit
     * @param key
     * @param offset
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public boolean getBit(String key, long offset)
    {
        BinaryJedis binaryJedis = null;
        Boolean result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.getbit(key.getBytes(), offset);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 取得对应key的偏移量为true/1的个数
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: getBitCount
     * @param key
     * @return 偏离位为true/1的个数
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long getBitCount(String key)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.bitcount(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 批量插入
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: mset
     * @param keysvalues
     * @exception:
     * @version: 1.0
     * @description:慎用。集群模式下，需要不同的key需要在一个slot上 。slot分配根据key的hash值，可以使用{aa}b,aa,让之在一个slot上 update_version: update_date:
     *                                          update_author: update_note:
     */
    public void mset(byte[]... keysvalues)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            binaryJedis.mset(keysvalues);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 批量多取
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: mget
     * @param keys
     * @return
     * @exception: 慎用，集群模式下，需要不同的key需要在一个slot上，可以使用{aa}b,aa,让之在一个slot上
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public List<byte[]> mget(byte[]... keys)
    {
        BinaryJedis binaryJedis = null;
        List<byte[]> result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.mget(keys);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 增加1，拥有原子性，可以用作秒杀活动
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: incr
     * @param key
     * @return 返回增加后的值。如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误
     * @exception:
     * @version: 1.0
     * @description: 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR update_version: update_date: update_author: update_note:
     */
    public long incr(String key)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.incr(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 增加value值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: incrby
     * @param key
     * @param incrValue
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long incrby(String key, long incrValue)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.incrBy(key.getBytes(), incrValue);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * value值减去1
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: decr
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long decr(String key)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.decr(key.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 减去原子值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: decrby
     * @param key
     * @param decrValue
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long decrby(String key, long decrValue)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.decrBy(key.getBytes(), decrValue);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 用于为指定的 key 追加值
     * 
     * @date: 2017年5月21日
     * @author: chenhao
     * @title: appendString
     * @param key
     * @param appendValue
     * @return 追加后的数目
     * @exception:
     * @version: 1.0
     * @description: ey 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET
     *               key value 一样。 update_version: update_date: update_author: update_note:
     */
    public long appendString(String key, String appendValue)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.append(key.getBytes(), appendValue.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /********************************* Map ****************************************/
    /**
     * 添加一个hash
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hset
     * @param key
     * @param field
     *            新增map的key
     * @param value
     *            新增map的value
     * @param ifnx
     *            是否覆盖原field的value，true时不覆盖原value。
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void hset(String key, String field, Object value, Boolean ifnx)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            if (null != value)
            {
                if (true == ifnx)
                {
                    binaryJedis.hsetnx(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
                }
                else
                {
                    binaryJedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
                }
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 批量添加hash
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hmset
     * @param key
     * @param inMap
     *            放入hash的Map
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void hmset(String key, Map<String, Object> inMap)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
            if (!inMap.isEmpty())
            {
                for (Map.Entry<String, Object> m : inMap.entrySet())
                {
                    Object valueResult = m.getValue();
                    if (valueResult != null)
                    {
                        map.put(m.getKey().getBytes(), SerializeUtil.serialize(valueResult));
                    }
                }
                binaryJedis.hmset(key.getBytes(), map);
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 取hash
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hget
     * @param key
     * @param field
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Object hget(String key, String field)
    {
        BinaryJedis binaryJedis = null;
        Object result;
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[] getRes = binaryJedis.hget(key.getBytes(), field.getBytes());
            if (getRes != null)
            {
                result = SerializeUtil.deSerialize(getRes);
            }
            else
            {
                result = null;
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 批量取hash
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hmget
     * @param key
     * @param fields
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public List<Object> hmget(String key, List<String> fields)
    {
        BinaryJedis binaryJedis = null;
        List<Object> resultList = new ArrayList<Object>();
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[][] in = new byte[fields.size()][];
            for (int i = 0; i < fields.size(); i++)
            {
                byte[] b = fields.get(i).getBytes();
                in[i] = b;
            }
            List<byte[]> l = binaryJedis.hmget(key.getBytes(), in);
            for (byte[] r : l)
            {
                if (r != null)
                    resultList.add(SerializeUtil.deSerialize(r));
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return resultList;

    }

    /**
     * 取得map所有值
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hgetAll
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Map<String, Object> hgetAll(String key)
    {
        BinaryJedis binaryJedis = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            binaryJedis = jedisPool.getResource();
            Map<byte[], byte[]> res = binaryJedis.hgetAll(key.getBytes());
            for (Map.Entry<byte[], byte[]> r : res.entrySet())
            {
                resultMap.put(new String(r.getKey()), SerializeUtil.deSerialize(r.getValue()));
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return resultMap;
    }

    /**
     * 根据key，field删除
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hdel
     * @param key
     * @param field
     * @return 删除成功返回1，失败返回0
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long hdel(String key, String field)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.hdel(key.getBytes(), field.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 批量删除
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hdel
     * @param key
     * @param fields
     * @return 返回删除成功数，失败返回0
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long hdel(String key, List<String> fields)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[][] in = new byte[fields.size()][];
            for (int i = 0; i < fields.size(); i++)
            {
                byte[] s = fields.get(i).getBytes();
                in[i] = s;
            }
            result = binaryJedis.hdel(key.getBytes(), in);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * hash的field值增加increment值（可以为负数）
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: hincr
     * @param key
     * @param field
     * @param increment
     * @return 增加的结果
     * @exception:
     * @version: 1.0
     * @description: 如果哈希表的 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果指定的字段不存在，那么在执行命令前，字段的值被初始化为 0 。 对一个储存字符串值的字段执行 HINCRBY
     *               命令将造成一个错误。 update_version: update_date: update_author: update_note:
     */
    public long hincr(String key, String field, long increment)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.hincrBy(key.getBytes(), field.getBytes(), increment);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /************************* List **************************************/
    /**
     * list插入
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: listAdd
     * @param key
     * @param value
     * @param nx
     *            列表不存在时是否新建列表，不新建列表false，新建列表true
     * @param formWhere
     *            l:表头添加 ；r：表尾添加
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void listAdd(String key, Object value, boolean nx, String formWhere)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            if (true == nx)
            {
                if (Const.REDIS_ADDLIST_LEFT.equals(formWhere))
                {
                    binaryJedis.lpush(key.getBytes(), SerializeUtil.serialize(value));
                }
                else
                {
                    binaryJedis.rpush(key.getBytes(), SerializeUtil.serialize(value));
                }
            }
            else
            {
                if (Const.REDIS_ADDLIST_LEFT.equals(formWhere))
                {
                    binaryJedis.lpushx(key.getBytes(), SerializeUtil.serialize(value));
                }
                else
                {
                    binaryJedis.rpushx(key.getBytes(), SerializeUtil.serialize(value));
                }
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 批量插入list
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: listAdd
     * @param key
     * @param listValue
     * @param nx
     *            nx 列表不存在时是否新建列表，不新建列表false，新建列表true
     * @param formWhere
     *            传入left从表头插入，right从表尾插入
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void listAdds(String key, List<Object> listValue, boolean nx, String formWhere)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[][] in = new byte[listValue.size()][];
            if (true == nx)
            {
                if (Const.REDIS_ADDLIST_LEFT.equals(formWhere))
                {
                    for (int i = 0; i < listValue.size(); i++)
                    {
                        byte[] b = SerializeUtil.serialize(listValue.get(i));
                        in[i] = b;
                    }
                    binaryJedis.lpush(key.getBytes(), in);
                }
                else
                {
                    for (int i = 0; i < listValue.size(); i++)
                    {
                        byte[] b = SerializeUtil.serialize(listValue.get(i));
                        in[i] = b;
                    }
                    binaryJedis.rpush(key.getBytes(), in);
                }
            }
            else
            {
                if (Const.REDIS_ADDLIST_LEFT.equals(formWhere))
                {
                    for (int i = 0; i < listValue.size(); i++)
                    {
                        byte[] b = SerializeUtil.serialize(listValue.get(i));
                        in[i] = b;
                    }
                    binaryJedis.lpushx(key.getBytes(), in);
                }
                else
                {
                    for (int i = 0; i < listValue.size(); i++)
                    {
                        byte[] b = SerializeUtil.serialize(listValue.get(i));
                        in[i] = b;
                    }
                    binaryJedis.rpushx(key.getBytes(), in);
                }
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 移除并返回list头或者尾（非阻塞方式）list空时返回null
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: listGet
     * @param key
     * @param formWhere
     *            传入left从表头弹出，right从表尾弹出
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Object listGet(String key, String formWhere)
    {
        BinaryJedis binaryJedis = null;
        byte[] result = null;
        try
        {
            binaryJedis = jedisPool.getResource();

            if (Const.REDIS_ADDLIST_LEFT.equals(formWhere))
            {
                result = binaryJedis.lpop(key.getBytes());
            }
            else
            {
                result = binaryJedis.rpop(key.getBytes());
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return SerializeUtil.deSerialize(result);

    }

    /**
     * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: listGetSafe
     * @param key
     * @param backUpKey
     * @param formWhere
     * @return
     * @exception: 可以用于安全队列和循环列表
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Object listGetSafe(String key, String backUpKey)
    {
        BinaryJedis binaryJedis = null;
        byte[] result = null;
        try
        {

            binaryJedis = jedisPool.getResource();
            result = binaryJedis.rpoplpush(key.getBytes(), backUpKey.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return SerializeUtil.deSerialize(result);
    }

    /**
     * 按值删除list中的某个值，
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: lRem
     * @param key
     * @param count
     * @param value
     * @return
     * @exception:
     * @version: 1.0
     * @description: count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT
     *               的绝对值。count = 0 : 移除表中所有与 VALUE 相等的值。 update_version: update_date: update_author: update_note:
     */
    public long lRem(String key, long count, Object value)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.lrem(key.getBytes(), count, SerializeUtil.serialize(value));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: ltrim
     * @param key
     * @param start
     * @param end
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void ltrim(String key, long start, int end)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            binaryJedis.ltrim(key.getBytes(), start, end);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 批量获取List
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: lgetAll
     * @param key
     * @param start
     * @param end
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public List<Object> lgetAll(String key, long start, long end)
    {
        BinaryJedis binaryJedis = null;
        List<Object> result = new ArrayList<Object>();
        try
        {
            binaryJedis = jedisPool.getResource();
            List<byte[]> l = binaryJedis.lrange(key.getBytes(), start, end);
            for (byte[] b : l)
            {
                result.add(SerializeUtil.deSerialize(b));
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /******************************** set ****************************************************/
    /**
     * 单个添加到set中
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: setAdd
     * @param key
     * @param value
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void setAdd(String key, Object value)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            binaryJedis.sadd(key.getBytes(), SerializeUtil.serialize(value));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 批量添加到set
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: setAdds
     * @param key
     * @param value
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void setAdds(String key, Set<Object> value)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[][] in = new byte[value.size()][];
            int i = 0;
            Iterator<Object> iter = value.iterator();
            while (iter.hasNext())
            {
                in[i] = SerializeUtil.serialize(iter.next());
                i++;
            }
            binaryJedis.sadd(key.getBytes(), in);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * set求差，交，并。
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: setCalculate
     * @param keys
     * @param sign
     *            1:求差集（注意差集仅为前集减去后集） ;2:求交集;3:求并集
     * @param ifstore
     *            是否将计算结果保存到storeKey
     * @param storeKey
     * @return 计算后的结果
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Set<Object> setCalculate(List<String> keys, int sign, boolean ifstore, String storeKey)
    {
        BinaryJedis binaryJedis = null;
        Set<byte[]> result = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[][] in = new byte[keys.size()][];
            for (int i = 0; i < keys.size(); i++)
            {
                byte[] b = keys.get(i).getBytes();
                in[i] = b;
            }
            switch (sign)
            {
                case 1:
                {
                    if (ifstore == true)
                    {
                        binaryJedis.sdiffstore(storeKey.getBytes(), in);
                        result = binaryJedis.smembers(storeKey.getBytes());
                    }
                    else
                    {
                        result = binaryJedis.sdiff(in);
                    }
                    break;
                }
                case 2:
                {
                    if (ifstore == true)
                    {
                        binaryJedis.sinterstore(storeKey.getBytes(), in);
                        result = binaryJedis.smembers(storeKey.getBytes());
                    }
                    else
                    {
                        result = binaryJedis.sinter(in);
                    }
                    break;
                }
                case 3:
                {
                    if (ifstore == true)
                    {
                        binaryJedis.sunionstore(storeKey.getBytes(), in);
                        result = binaryJedis.smembers(storeKey.getBytes());
                    }
                    else
                    {
                        result = binaryJedis.sunion(in);
                    }
                    break;
                }
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        Set<Object> resultSet = new HashSet<Object>();
        Iterator<byte[]> iter = result.iterator();
        while (iter.hasNext())
        {
            resultSet.add(SerializeUtil.deSerialize(iter.next()));
        }
        return resultSet;

    }

    /**
     * 返回集合中的所有成员
     * 
     * @date: 2017年5月22日
     * @author: chenhao
     * @title: sgetAll
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Set<Object> sgetAll(String key)
    {
        BinaryJedis binaryJedis = null;
        Set<Object> resultSet = new HashSet<Object>();
        try
        {
            binaryJedis = jedisPool.getResource();
            Set<byte[]> result = binaryJedis.smembers(key.getBytes());
            Iterator<byte[]> iter = result.iterator();
            while (iter.hasNext())
            {
                resultSet.add(SerializeUtil.deSerialize(iter.next()));
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return resultSet;
    }

    /**
     * 判断一个对象是否在set中
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: sIsMember
     * @param key
     * @param member
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public boolean sIsMember(String key, Object member)
    {
        BinaryJedis binaryJedis = null;
        Boolean result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.sismember(key.getBytes(), SerializeUtil.serialize(member));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 把一个对象从一个key移动到另一个key
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: smove
     * @param fromkey
     * @param toKey
     * @param obj
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long smove(String fromkey, String toKey, Object obj)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.smove(fromkey.getBytes(), toKey.getBytes(), SerializeUtil.serialize(obj));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 移除并返回集合中的一个随机元素
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: sget
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Object sget(String key)
    {
        BinaryJedis binaryJedis = null;
        Object result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = SerializeUtil.deSerialize(binaryJedis.spop(key.getBytes()));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 移除集合中一个或者多个成员
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: srem
     * @param key
     * @param value
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long sDelete(String key, Object... values)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            Object[] objs = values;
            byte[][] in = new byte[values.length][];
            for (int i = 0; i < values.length; i++)
            {
                in[i] = SerializeUtil.serialize(objs[i]);
            }
            result = binaryJedis.srem(key.getBytes(), in);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /************************** zset *******************************/
    /**
     * zset添加
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: zsetAdd
     * @param key
     * @param score
     * @param member
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void zsetAdd(String key, double score, Object member)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            binaryJedis.zadd(key.getBytes(), score, SerializeUtil.serialize(member));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * zset批量添加
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: zsetAdd
     * @param key
     * @param scoreMembers
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public void zsetAdd(String key, Map<Object, Double> scoreMembers)
    {
        BinaryJedis binaryJedis = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            Map<byte[], Double> in = new HashMap<byte[], Double>();
            for (Map.Entry<Object, Double> entry : scoreMembers.entrySet())
            {
                in.put(SerializeUtil.serialize(entry.getKey()), entry.getValue());
            }
            binaryJedis.zadd(key.getBytes(), in);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: zcount
     * @param key
     * @param scoreStart
     * @param scoreEnd
     * @return 成员数
     * @exception:
     * @version: 1.0
     * @description: 入参形如："(1.5", "3.0" 表示1.5<sorce<=3.0。加"("代表不取等。 update_version: update_date: update_author:
     *               update_note:
     */
    public long zcount(String key, String scoreStart, String scoreEnd)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.zcount(key.getBytes(), scoreStart.getBytes(), scoreEnd.getBytes());
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }
    //	public long zlexcount(String key,Object startMember,Object endMember){
    //		return jedisPool.getResource().zlexcount(key.getBytes(), SerializeUtil.serialize(startMember), SerializeUtil.serialize(endMember));
    //	}

    /**
     * 通过索引区间,或者分数返回有序集合成指定区间内的成员
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: zrange
     * @param key
     * @param methods
     *            索引区间查询：sequence；分数排序查询：score
     * @param orderMethod
     *            分数高-->低（区间大-->小）：desc ；分数低-->高（区间小-->大）：asc
     * @return
     * @exception:
     * @version: 1.0
     * @description: 按分数排序时：入参形如："(1.5", "3.0" 表示1.5<sorce<=3.0。加"("代表不取等。 按序列排序时：入参请传整数 update_version: update_date:
     *               update_author: update_note:
     */
    public List<Object> zrange(String key, String methods, String orderMethod, String start, String end)
    {
        BinaryJedis binaryJedis = null;
        Set<byte[]> result = null;
        try
        {
            binaryJedis = jedisPool.getResource();
            List<Object> resultList = new ArrayList<Object>();
            if (Const.REDIS_ZRANGE_BYSEQUENCE.equals(methods))
            {
                if (Const.REDIS_ZRANGE_ASC.equals(orderMethod))
                {
                    result = binaryJedis.zrange(key.getBytes(), Long.parseLong(start), Long.parseLong(end));
                }
                else
                {
                    result = binaryJedis.zrevrange(key.getBytes(), Long.parseLong(start), Long.parseLong(end));
                }
            }
            else if (Const.REDIS_ZRANGE_BYSCORE.equals(methods))
            {
                if (Const.REDIS_ZRANGE_ASC.equals(orderMethod))
                {
                    result = binaryJedis.zrangeByScore(key.getBytes(), start.getBytes(), end.getBytes());
                }
                else
                {
                    result = binaryJedis.zrevrangeByScore(key.getBytes(), end.getBytes(), start.getBytes());
                }
            }
            else
            {
                try
                {
                    throw new Exception("please pass a current value!");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        Iterator<byte[]> iter = result.iterator();
        List<Object> resultList = new ArrayList<>();
        while (iter.hasNext())
        {
            resultList.add(SerializeUtil.deSerialize(iter.next()));
        }
        return resultList;
    }

    /**
     * 有序集合中对指定成员的分数加上增量 score
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: zincr
     * @param key
     * @param member
     * @param score
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public double zincr(String key, Object member, Double score)
    {
        BinaryJedis binaryJedis = null;
        double result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.zincrby(key.getBytes(), score, SerializeUtil.serialize(member));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 返回有序集合中指定成员的排名，从0开始
     * 
     * @date: 2017年5月23日
     * @author: chenhao
     * @title: zrank
     * @param key
     * @param member
     * @param orderMethod
     *            分数高-->低（区间大-->小）：desc ；分数低-->高（区间小-->大）：asc
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long zrank(String key, Object member, String orderMethod)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            if (Const.REDIS_ZRANGE_DESC.equals(orderMethod))
            {
                result = binaryJedis.zrevrank(key.getBytes(), SerializeUtil.serialize(member));
            }
            else
            {
                result = binaryJedis.zrank(key.getBytes(), SerializeUtil.serialize(member));
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 通过索引区间,或者分数删除有序集合成指定区间内的成员
     * 
     * @date: 2017年5月24日
     * @author: chenhao
     * @title: zremInterval
     * @param key
     * @param methods
     * @param start
     * @param end
     * @return
     * @exception:
     * @version: 1.0
     * @description: 按分数排序时：入参形如："(1.5", "3.0" 表示1.5<sorce<=3.0。加"("代表不取等。 按序列排序时：入参请传整数 update_version: update_date:
     *               update_author: update_note:
     */
    public long zDeleteInterval(String key, String methods, String start, String end)
    {
        BinaryJedis binaryJedis = null;
        long result = 0;
        try
        {
            binaryJedis = jedisPool.getResource();
            if (Const.REDIS_ZRANGE_BYSEQUENCE.equals(methods))
            {
                result = binaryJedis.zremrangeByRank(key.getBytes(), Long.parseLong(start), Long.parseLong(end));
            }
            if (Const.REDIS_ZRANGE_BYSCORE.equals(methods))
            {
                result = binaryJedis.zremrangeByScore(key.getBytes(), start.getBytes(), end.getBytes());
            }
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 批量删除
     * 
     * @date: 2017年5月24日
     * @author: chenhao
     * @title: zDeletes
     * @param key
     * @param members
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long zDeletes(String key, Set<Object> members)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            byte[][] in = new byte[members.size()][];
            int i = 0;
            for (Object obj : members)
            {
                byte[] b = SerializeUtil.serialize(obj);
                in[i] = b;
                i++;
            }
            result = binaryJedis.zrem(key.getBytes(), in);
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 删除zset对象
     * 
     * @date: 2017年5月24日
     * @author: chenhao
     * @title: zDelete
     * @param key
     * @param member
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public long zDelete(String key, Object member)
    {
        BinaryJedis binaryJedis = null;
        long result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.zrem(key.getBytes(), SerializeUtil.serialize(member));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }

    /**
     * 返回对象的分数
     * 
     * @date: 2017年5月24日
     * @author: chenhao
     * @title: zGetScore
     * @param key
     * @param member
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public Double zGetScore(String key, Object member)
    {
        BinaryJedis binaryJedis = null;
        Double result;
        try
        {
            binaryJedis = jedisPool.getResource();
            result = binaryJedis.zscore(key.getBytes(), SerializeUtil.serialize(member));
        }
        finally
        {
            if (binaryJedis != null)
            {
                binaryJedis.close();
            }
        }
        return result;
    }
}
