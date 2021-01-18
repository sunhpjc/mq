package com.sunhp.rocketmq.utils;

import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import com.sunhp.rocketmq.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sunhp
 * @Description
 * @Date 2021/1/8 9:59
 **/
@Service("redisUtil")
public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static final int INT_1 = -1;

    @Value("${spring.redis.prefix}")
    private String cachePrefix;

    @Resource
    private JedisPool jedisPool;

    /**
     * Tips: 获取Jedis实例
     * @return 返回jedis实例
     */
    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                logger.error("===获取jedis实例失败");
                throw new MyException(ResponseCodeEnum.JEDIS_ERROR);
            }
        } catch (Exception e) {
            logger.error("获取jedis实例失败 => {}", e.getMessage());
            throw new MyException(ResponseCodeEnum.JEDIS_ERROR);
        }
    }
    /**
     * Tips: 释放jedis资源
     * @param jedis 关闭当前jedis资源
     */
    private void closeJedis(final Jedis jedis) {
        if (jedis != null && jedis.isConnected()) {
            jedis.close();
        }
    }

    /**
     * Tips: 判断key是否存在
     * @param key 需要判断的key
     * @return 若存在返回true，否则返回false，若未能获取连接池中的jedis实例，则返回false================>
     */
    public Boolean exists(String key) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        boolean flag = false;
        if (jedis != null) {
            flag = jedis.exists(key);
//            logger.info("判断redis中是否存在key[{}]结果为：{}", key, flag);
            closeJedis(jedis);
        }
        return flag;
    }
    /**
     * 延长key的时间
     * @param key
     * @param second
     */
    public void expire(String key, Integer second) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            if (second != null) {
                jedis.expire(key, second);
//                logger.info("向redis中expire，key[{}],,second[{}]", key, second);
            }
            closeJedis(jedis);
        }
    }
    /**
     * hset
     * @param key
     * @param field
     * @return
     */
    public Long hset(String key,String field,String value) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            Long res = jedis.hset(key, field, value);
            logger.info("从redis中hset key[{}]field[{}]的值[{}]]", key,field,value);
            closeJedis(jedis);
            return res;
        }
        return null;
    }

    /**
     * Tips: 向redis中设置key
     * @param key 键
     * @param value 值
     * @param second 有效期秒 若不设置时效，传入null即可
     */
    public String set(String key, String value, Integer second) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        String res = null;
        if (jedis != null) {
            res = jedis.set(key, value);
            if (second != null) {
                jedis.expire(key, second);
            }
            logger.info("向redis中设置key[{}],value[{}],second[{}]", key, value, second);
            closeJedis(jedis);
        }
        return res;
    }
    /**
     * 带锁性质的set
     * @param key
     * @param value
     * @param nxxx
     * @param expx
     * @param time
     * @return
     */
    public String set(String key, String value,String nxxx, String expx, long time) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        String res = null;
        if (jedis != null) {
            res = jedis.set(key, value, nxxx, expx, time);
//            logger.info("向redis中设置key[{}],value[{}],nxxx[{}],expx[{}],time[{}]", key, value, nxxx, expx,time);
            closeJedis(jedis);
        }
        return res;
    }


    /**
     * hget
     * @param key
     * @param field
     * @return
     */
    public String hget(String key,String field) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            String string = jedis.hget(key, field);
            logger.info("从redis中获取key[{}]field[{}]的值[{}]", key,field, string);
            closeJedis(jedis);
            return string;
        }
        return null;
    }
    /**
     * Tips: 从redis中获取key对应的值
     * @param key 键
     * @return key对应的值，若jedis实例获取失败，则返回null
     */
    public String get(String key) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            String string = jedis.get(key);
//            logger.info("从redis中获取key[{}]的值[{}]]", key, string);
            closeJedis(jedis);
            return string;
        }
        return null;
    }

    /**
     * Tips: 从redis中获取key对应的值
     * @param key 键
     * @return key对应的值，若jedis实例获取失败，则返回null
     */
    public Set<String> keys(String key) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            Set<String> keys = jedis.keys(key);
            logger.info("从redis中获取keys[{}]的值[{}]]", key, keys);
            closeJedis(jedis);
            return keys;
        }
        return null;
    }

    /**
     * Tips: 从redis中删除key
     * @param key 需要删除的key
     */
    public void del(String key) {
        key=cachePrefix+key;
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.del(key);
            logger.info("从redis中移除key[{}]", key);
            closeJedis(jedis);
        }
    }

    /**
     * 生成20位唯一值
     * @return
     */
    public String generatorId() {
        String id = generator("TABLE", "", "yyyyMMdd", "00000000", 24*60*60);
        if (StringUtils.isBlank(id)) {
            logger.error("id 生成失败，请联系相关技术检查！");
            throw new MyException(ResponseCodeEnum.REDIS_ID_ERROR);
        }
        return id;
    }
    /**
     * Tips: 相关表主建生成规则
     * @param tableEnum 表的前缀
     * @return 对应前缀的唯一表ID
     */
/*    public Long generatorTableId(TableEnum tableEnum) {
        String id = generator("TABLE"+tableEnum.getKey(), tableEnum.getKey(), "yyyyMMddHHmm", "00000", 70);
        if (StringUtils.isBlank(id)) {
            logger.error("ID 生成失败，请联系相关技术检查！");
            throw new ActivitiException(ResponseCodeEnum.SYSTEM_BUSINESS_ERROR);
        }
        //最大9223372036854775807
        return Long.parseLong(id);
    }*/

    /**
     * Tips: 统一生成规则，自定义前缀 + 年月日（+ 时分秒）+ 初始化流水
     * @param generatorKey 需要存储在redis中的key
     * @param prefix 前缀 自定义
     * @param format 日期格式化字符串 自定义
     * @param initialFlow 初始化流水
     * @param second 时效 存活时间以秒为单位：失效的时候的unix时间
     * @return 生成出来的唯一编号
     */
    private String generator(String generatorKey, String prefix, String format, String initialFlow, Integer second) {
        generatorKey=cachePrefix+generatorKey;
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        if (prefix == null) {
            prefix = "";
        } else {
            prefix = prefix.trim();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateFormat = simpleDateFormat.format(new Date());
        generatorKey = String.format("%s:%s", generatorKey, dateFormat);
        // 加锁
        String identifier = addLockInner(jedis, generatorKey,INT_1);
        if(StringUtils.isBlank(identifier)){
            logger.error("从redis中没有获取到分布式锁，generatorKey:{}", generatorKey);
            throw new RuntimeException(String.format("从redis中没有获取到分布式锁，generatorKey:%s", generatorKey));
        }
        BigDecimal tempBigDecimal;
        String value = "%s%s";
        String returnValue = null;
        boolean flag = false;
        if (jedis.exists(generatorKey)) {
            String stubNoValue = jedis.get(generatorKey);
            if (stubNoValue != null) {
                tempBigDecimal = (new BigDecimal(stubNoValue.substring(prefix.length()))).add(BigDecimal.ONE);
                returnValue = String.format(value, prefix, tempBigDecimal.toString());
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
        if (flag) {
            String temIntegerTemplate = "%s%s%s";
            tempBigDecimal = (new BigDecimal(String.format(temIntegerTemplate, prefix, dateFormat, initialFlow).substring(prefix.length()))).add(BigDecimal.ONE);
            returnValue = String.format(value, prefix, tempBigDecimal.toString());
            // 新增redis中的值, 有效期为24小时
            jedis.set(generatorKey, returnValue);
            jedis.expire(generatorKey, second);
        } else {
            // 更新redis中的值
            Long ttl = jedis.ttl(generatorKey);
            jedis.set(generatorKey, returnValue);
            jedis.expire(generatorKey,Integer.valueOf(ttl.intValue()) );
//			  jedis.set(generatorKey, returnValue);
        }
        // 释放锁
        if (!releaseLockInner(jedis, generatorKey, identifier)) {
            logger.error("释放锁 [{}] 失败", generatorKey);
        }
        // 释放redis线程池
        closeJedis(jedis);
        return returnValue;
    }
    /**
     * 外部使用
     * @param jedis
     * @param keyNoPrefix
     * @return
     */
    public String addLock(Jedis jedis, String keyNoPrefix) {
        keyNoPrefix=cachePrefix+keyNoPrefix;
        return this.addLockInner(jedis, keyNoPrefix,INT_1);
    }

    /**
     * 加锁（消息队列，去掉redis）210112
     * @param keyNoPrefix
     * @return
     */
    public String addLockMq(String keyNoPrefix,long timeoutParam) {
        Jedis jedis = getJedis();
        keyNoPrefix=cachePrefix+keyNoPrefix;
        return this.addLockInner(jedis, keyNoPrefix, timeoutParam);
    }
    /**
     * Tips: 加锁 内部使用
     * @param jedis redis客户端
     * @param key 锁的key
     * @return 锁标识
     */
    private String addLockInner(Jedis jedis, String key, long timeoutParam) {
        long acquireTimeout = 5000; // 获取超时时间
        long timeout = 5000; // 锁的超时时间
        if(INT_1 != timeoutParam){
            timeout = timeoutParam;
        }
        if (jedis == null) {
            jedis = getJedis();
            if (jedis == null) {
                return null;
            }
        }
        String identifier = UUID.randomUUID().toString(); // 随机生成一个value
        String lockKey = String.format("lock:%s", key); // 锁名，即key值
        int lockExpire = (int) (timeout / 1000); // 超时时间，上锁后超过此时间则自动释放锁
		/* // 加锁正确姿势：
		jedis.set(String key, String value, String nxxx, String expx, int time)，这个set()方法一共有五个形参：
		第一个为key，我们使用key来当锁，因为key是唯一的。
		第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成。
		第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
		第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
		第五个为time，与第四个参数相呼应，代表key的过期时间。
		总的来说，执行上面的set()方法就只会导致两种结果：
			1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。
			2. 已有锁存在，不做任何操作。
		*/
        long end = System.currentTimeMillis() + acquireTimeout; // 获取锁的超时时间，超过这个时间则放弃获取锁
        while (System.currentTimeMillis() < end) {
//			if (jedis.setnx(lockKey, identifier) == 1) {
//				jedis.expire(lockKey, lockExpire);
//				retIdentifier = identifier; // 返回value值，用于释放锁时间确认
//				return retIdentifier;
//			}
            String result = jedis.set(lockKey, identifier, "NX", "PX", timeout);
            String getLocalKey = "OK";
            if (getLocalKey.equals(result)) {
                return identifier;
            }
            // 返回-1代表key没有设置超时时间，为key设置一个超时时间
            //如果出现这种状况，那就是进程的某个实例setnx成功后 crash 导致紧跟着的expire没有被调用
            //这是因为set 方法的set值和设置值得生存时间不是原子操作可能在设置值成功后出现异常导致没有设置生存时间 ，可以用redis LUNA来把这两个操作绑定为原子操作
            if (jedis.ttl(lockKey) == -1) {
                jedis.expire(lockKey, lockExpire);
            }
            try {
//				Thread.sleep(10);
                //随机值，防止所有锁同一时间去抢锁
                Thread.sleep(new Random().nextInt(10)+1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }
    /**
     * Tips: 释放锁
     * @param jedis redis客户端
     * @param lockNameNoPrefix 锁的key
     * @param identifier 释放锁的标识
     */
    public boolean releaseLock(Jedis jedis, String lockNameNoPrefix, String identifier) {
        lockNameNoPrefix=cachePrefix+lockNameNoPrefix;
        return this.releaseLockInner(jedis, lockNameNoPrefix, identifier);
    }

    /**
     * 解锁（消息队列，去掉redis）210112
     * @param lockNameNoPrefix
     * @param identifier
     * @return
     */
    public boolean releaseLockMq(String lockNameNoPrefix, String identifier) {
        Jedis jedis = getJedis();
        lockNameNoPrefix=cachePrefix+lockNameNoPrefix;
        return this.releaseLockInner(jedis, lockNameNoPrefix, identifier);
    }
    /**
     * Tips: 释放锁
     * @param jedis redis客户端
     * @param lockName 锁的key
     * @param identifier 释放锁的标识
     */
    private boolean releaseLockInner(Jedis jedis, String lockName, String identifier) {
        boolean retFlag = false;
        if (jedis == null) {
            jedis = getJedis();
            if (jedis == null) {
                return false;
            }
        }
        String lockKey = "lock:" + lockName;
        //用redisLUNA命令实现，提高效率
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(identifier));
        if (1L == (Long) result) {
            retFlag = true;
        }
        return retFlag;
    }

    /**
     * Tips: 序列化对象，用于存储到redis中
     * @param object 需要序列化的对象，若序列化对象时，需要保证对象中没有为null的值，否则在拿到序列化的值进行redis存储时会报错
     * @return 返回序列化后的byte数组
     */
    private <T> byte[] serialize(T object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            // 序列化
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            logger.error("序列化失败==> {}", exception.getMessage());
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Tips: 反序列化，用于把redis中取出来的值还原成对象
     * @param bytes redis值
     * @return T 指定值对象类型
     */
    private <T> T unSerialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            // 反序列化
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            @SuppressWarnings("unchecked") T t = (T) object;
            return t;
        } catch (Exception exception) {
            logger.error("反序列化失败==> {}", exception.getMessage());
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return null;
    }
}
