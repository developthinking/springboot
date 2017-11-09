package com.wt.lbsWeb.base.util;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * Jedis连接Redis工具类
 * @author Administrator
 */
public class RedisUtil {
	private static String ADDR_ARRAY; //IP
    private static int PORT; //端口  
    private static String AUTH; //密码
    private static int   MAX_ACTIVE; //最大连接数
    private static int   MAX_IDLE; //设置最大空闲数
    private static int   MAX_WAIT; //等待可用连接的最大时间
    private static int   TIMEOUT; //超时时间  
    private static boolean TEST_ON_BORROW = true; //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的 
    
    private static JedisPool jedisPool = null; //Jedis连接池
    //private static Jedis jedis;
	
    /**
     * 构造函数
     */
	private RedisUtil() {
		Properties properties = LoadProperties.getProperties("properties/redis/redis_config.properties");
		ADDR_ARRAY = properties.getProperty("ADDR_ARRAY");
    	PORT = Integer.parseInt(properties.getProperty("PORT"));
    	AUTH =properties.getProperty("AUTH","");
    	MAX_ACTIVE = Integer.parseInt(properties.getProperty("MAX_ACTIVE","8"));
    	MAX_IDLE = Integer.parseInt(properties.getProperty("MAX_IDLE","8"));
    	MAX_WAIT = Integer.parseInt(properties.getProperty("MAX_WAIT","-1"));
    	TIMEOUT = Integer.parseInt(properties.getProperty("TIMEOUT","0"));
    	TEST_ON_BORROW = Boolean.parseBoolean(properties.getProperty("TEST_ON_BORROW","true"));
    	initialPool();
	}
	
	/**
	 * 单例模式
	 */
	private static RedisUtil instance;
	
	/**
	 * 获取实例
	 * @return
	 */
	public static synchronized RedisUtil getInstance() {
		if (instance == null) {
			instance = new RedisUtil();
		}
		return instance;
	}
	
	/**
	 * 初始化Jedis连接池
	 */
	public void initialPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		try {
			if (AUTH != null && !"".equals(AUTH)) { //如果密码有配置
				setJedisPool(new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT, AUTH));
			} else {
				setJedisPool(new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT));
			} 
		} catch (Exception e1) {
			if (ADDR_ARRAY.split(",").length > 1) { //如果IP不止一个 第一个IP异常 访问第二个IP
				try {
					if (AUTH != null && !"".equals(AUTH)) {
						setJedisPool(new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT, AUTH));
					} else {
						setJedisPool(new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT));
					} 
				} catch (Exception e2) {
					e2.printStackTrace();
				} 
			} else {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 在多线程环境同步初始化
	 */
	private synchronized void poolInit() {
		if (getJedisPool() == null) {
			initialPool();
		}
	}
	
	/**
	 * 释放Jedis资源
	 * @param jedis
	 */
	private void returnResource(final Jedis jedis) {
		if (jedis != null && getJedisPool() !=null) {
        	getJedisPool().returnResource(jedis);
        }
	}
	
	public synchronized Jedis getJedisFromPool() {
		if (getJedisPool() == null) {  
        	poolInit();
        }
		Jedis jedis = null;
		try {
			if (getJedisPool() != null) {  
            	jedis = getJedisPool().getResource(); 
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return jedis;
	}
    
	public static JedisPool getJedisPool() {
		return jedisPool;
	}

	public static void setJedisPool(JedisPool jedisPool) {
		RedisUtil.jedisPool = jedisPool;
	}
}
