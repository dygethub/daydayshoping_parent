package cn.itsource.jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

public enum  JedisConPoolUtil {
    instance;
    private static JedisPool pool=null;
    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/itsource/jedis/resources/jedis.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //空闲时，最小连接数
        poolConfig.setMaxIdle(5);
        //最大连接数
        poolConfig.setMaxTotal(20);
        //设置等待时间
        poolConfig.setMaxWaitMillis(20*1000);
        //设置测试,在得到连接对象的时候测试
        poolConfig.setTestOnBorrow(true);
        //JedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password)
         pool = new JedisPool(poolConfig, properties.getProperty("host"),  Integer.valueOf(properties.getProperty("port")), Integer.valueOf(properties.getProperty("timeout")), properties.getProperty("password"));
    }

    public Jedis getJedis(){
        return pool.getResource();
    }
    public void close(Jedis jedis){
        if (jedis != null) {
            jedis.close();
        }
    }
}
