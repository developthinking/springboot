package com.wt.lbsWeb.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wt.lbsWeb.base.constant.PoiAoiConstant;
import com.wt.lbsWeb.base.util.RedisUtil;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

@Service("poiAoiRedisService")
public class PoiAoiRedisService {

	/**
	 * 根据栅格ID获得uid集合 无标签 单个key
	 * 
	 * @param gridKey
	 *            栅格ID
	 * @return uids
	 */
	public Set<String> getUidsByGrid(String gridKey) throws Exception {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		Set<String> uids = redis.smembers(gridKey);
		return uids;
	}

	/**
	 * 根据栅格ID获得uid集合 有标签 单个key
	 * 
	 * @param gridKey
	 *            栅格ID
	 * @param containUids
     *            保存的uid集合
	 * @return containUids
	 */
	public Set<String> getUidsByGrid(String gridKey, String labelKey, Set<String> containUids) throws Exception {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		Set<String> uids = redis.smembers(gridKey);
		for (String uid : uids) {
			if (redis.sismember(labelKey, uid)) {
				containUids.add(uid);
			}
		}
		return containUids;
	}

	/**
	 * 根据栅格ID获得uid集合 无标签 多个key通过管道
	 * 
	 * @param gridKeys
	 *            栅格ID集合
	 * @param count
	 *            pipeline一次执行个数
	 * @param uids
	 *            uid集合
	 * @return uids
	 */
	public Set<String> getUidsByGrid(Set<String> gridKeys, int count, Set<String> uids) throws Exception {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		
		if (gridKeys.size() <= count) {
			// 开启管道
			Pipeline pipe = redis.pipelined();
			Map<String, Response<Set<String>>> responses = new HashMap<String, Response<Set<String>>>(gridKeys.size());
			for (String gridKey : gridKeys) {
				responses.put(gridKey, pipe.smembers(gridKey));
			}
			// 关闭管道
			pipe.sync();

			for (String k : responses.keySet()) {
				uids.addAll(responses.get(k).get());
			}
		} else {
			List<String> gridKeysList = new ArrayList<>(gridKeys);
			List<String> gridKeysListWithin = new ArrayList<>();
			for (int i = 0; i < gridKeysList.size(); i++) {
				gridKeysListWithin.add(gridKeysList.get(i));
				if ((i + 1) % count == 0) {
					// 开启管道
					Pipeline pipe = redis.pipelined();
					Map<String, Response<Set<String>>> responses = new HashMap<String, Response<Set<String>>>(gridKeysListWithin.size());
					for (String gridKey : gridKeysListWithin) {
						responses.put(gridKey, pipe.smembers(gridKey));
					}
					// 关闭管道
					pipe.sync();

					for (String k : responses.keySet()) {
						uids.addAll(responses.get(k).get());
					}
					gridKeysListWithin.clear();
				}
			}
			if (gridKeysListWithin.size() > 0) {
				// 开启管道
				Pipeline pipe = redis.pipelined();
				Map<String, Response<Set<String>>> responses = new HashMap<String, Response<Set<String>>>(gridKeysListWithin.size());
				for (String gridKey : gridKeysListWithin) {
					responses.put(gridKey, pipe.smembers(gridKey));
				}
				// 关闭管道
				pipe.sync();

				for (String k : responses.keySet()) {
					uids.addAll(responses.get(k).get());
				}
			}
		}
		
		return uids;
	}

	/**
	 * 判断标签中是否含有uid
	 * 
	 * @param labelKey
	 *            标签key
	 * @param uid
     *            uid
	 */
	public boolean fitLabel(String labelKey, String uid) throws Exception {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		return redis.sismember(labelKey, uid);
	}

	/**
	 * 判断标签中是否含有uid，含有标签的加到containUids中
	 * 
	 * @param labelKey
	 *            标签
	 * @param uids
	 *            uid集合
	 * @param containUids
	 *            保留的uid集合
	 * @param count
	 *            pipeline一次执行个数
	 */
	public Set<String> fitLabel(String labelKey, Set<String> uids, Set<String> containUids, int count) throws Exception {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		
		if (uids.size() <= count) {
			// 开启管道
			Pipeline pipe = redis.pipelined();
			Map<String, Response<Boolean>> responses = new HashMap<String, Response<Boolean>>(uids.size());
			for (String uid : uids) {
				responses.put(uid, pipe.sismember(labelKey, uid));
			}
			// 关闭管道
			pipe.sync();

			for (String k : responses.keySet()) {
				if (responses.get(k).get()) {
					containUids.add(k);
				}
			}
		} 
		else {
			List<String> uidList = new ArrayList<>(uids);
			List<String> uidListWithin = new ArrayList<>();
			for (int i = 0; i < uidList.size(); i++) {
				uidListWithin.add(uidList.get(i));
				if ((i + 1) % count == 0) {
					// 开启管道
					Pipeline pipe = redis.pipelined();
					Map<String, Response<Boolean>> responses = new HashMap<String, Response<Boolean>>(uidListWithin.size());
					for (String uid : uidListWithin) {
						responses.put(uid, pipe.sismember(labelKey, uid));
					}
					// 关闭管道
					pipe.sync();

					for (String k : responses.keySet()) {
						if (responses.get(k).get()) {
							containUids.add(k);
						}
					}
					uidListWithin.clear();
				}
			}
			if (uidListWithin.size() > 0) {
				// 开启管道
				Pipeline pipe = redis.pipelined();
				Map<String, Response<Boolean>> responses = new HashMap<String, Response<Boolean>>(uidListWithin.size());
				for (String uid : uidListWithin) {
					responses.put(uid, pipe.sismember(labelKey, uid));
				}
				// 关闭管道
				pipe.sync();

				for (String k : responses.keySet()) {
					if (responses.get(k).get()) {
						containUids.add(k);
					}
				}
			}
		}
		
		return containUids;
	}

	/**
	 * 根据标签id查询uid 多个标签
	 * 
	 * @param prefix
	 *            前缀
	 * @param labels
	 *            标签集合
	 * @param province
	 *            省
	 * @param city
	 *            市
	 * @param uids
	 *            uid集合
	 */
	public Set<String> getUidsByCategories(String prefix, List<String> labels, String province, String city, Set<String> uids) throws Exception {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		
		for (String label : labels) {
			String labelKey = prefix + PoiAoiConstant.SEPARATOR_R + province + PoiAoiConstant.SEPARATOR_R + city + PoiAoiConstant.SEPARATOR_R + label;
			uids.addAll(redis.smembers(labelKey));
		}

		return uids;
	}

	/**
	 * 根据uid集合返回信息
	 * 
	 * @param prefix
	 *            前缀
	 * @param uids
	 *            uid集合
	 * @param count
	 *            pipeline一次查询个数
	 */
	public List<Map<String, String>> getDataByUid(String prefix, Set<String> uids, int count) throws Exception {
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();

		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		
		if (uids.size() <= count) {
			// 开启管道
			Pipeline pipe = redis.pipelined();
			Map<String, Response<Map<String, String>>> responses = new HashMap<String, Response<Map<String, String>>>(uids.size());
			for (String uid : uids) {
				responses.put(uid, pipe.hgetAll(prefix + PoiAoiConstant.SEPARATOR_R + uid));
			}
			// 关闭管道
			pipe.sync();

			for (String k : responses.keySet()) {
				datas.add(responses.get(k).get());
			}
		} 
		else {
			List<String> uidList = new ArrayList<>(uids);
			List<String> uidListWithin = new ArrayList<>();
			for (int i = 0; i < uidList.size(); i++) {
				uidListWithin.add(uidList.get(i));
				if ((i + 1) % count == 0) {
					// 开启管道
					Pipeline pipe = redis.pipelined();
					Map<String, Response<Map<String, String>>> responses = new HashMap<String, Response<Map<String, String>>>(uidListWithin.size());
					for (String uid : uidListWithin) {
						responses.put(uid, pipe.hgetAll(uid));
					}
					// 关闭管道
					pipe.sync();

					for (String k : responses.keySet()) {
						datas.add(responses.get(k).get());
					}
					uidListWithin.clear();
				}
			}
			if (uidListWithin.size() > 0) {
				// 开启管道
				Pipeline pipe = redis.pipelined();
				Map<String, Response<Map<String, String>>> responses = new HashMap<String, Response<Map<String, String>>>(uidListWithin.size());
				for (String uid : uidListWithin) {
					responses.put(uid, pipe.hgetAll(uid));
				}
				// 关闭管道
				pipe.sync();

				for (String k : responses.keySet()) {
					datas.add(responses.get(k).get());
				}
			}
		}
		
		return datas;
	}
	
	public static void main(String[] args) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
