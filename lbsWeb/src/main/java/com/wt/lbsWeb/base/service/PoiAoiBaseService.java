package com.wt.lbsWeb.base.service;

import java.util.HashSet;
import java.util.Set;

import com.wt.lbsWeb.base.constant.PoiAoiConstant;
import com.wt.lbsWeb.base.util.CommonMethod;
import com.wt.lbsWeb.base.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;


@Service("poiAoiBaseService")
public class PoiAoiBaseService {

	/**
	 * 判断矩形范围（格式：左下角坐标;右上角坐标）是否正确
	 * @param rectangle 矩形范围（lng,lat;lng,lat）
	 * @return
	 */
    public boolean fitRectangleFormat(String rectangle) {
    	if (!rectangle.matches("[0-9]*(\\.?)[0-9]*,[0-9]*(\\.?)[0-9]*;[0-9]*(\\.?)[0-9]*,[0-9]*(\\.?)[0-9]*")) {
    		return false;
    	}
    	try {
    		String leftPointStr = rectangle.split(PoiAoiConstant.SEPARATOR_J)[0];
    		String rightPointStr = rectangle.split(PoiAoiConstant.SEPARATOR_J)[1];
    		double leftPointLngX = Double.valueOf(leftPointStr.split(PoiAoiConstant.SPLIT_COMMA)[0]);
    		double leftPointLatY = Double.valueOf(leftPointStr.split(PoiAoiConstant.SPLIT_COMMA)[1]);
    		double rightPointLngX = Double.valueOf(rightPointStr.split(PoiAoiConstant.SPLIT_COMMA)[0]);
    		double rightPointLatY = Double.valueOf(rightPointStr.split(PoiAoiConstant.SPLIT_COMMA)[1]);
    		if (leftPointLngX < rightPointLngX && leftPointLatY < rightPointLatY) {
    			return true;
			} else {
				return false;
			}
    	} catch (Exception e) {
			return false;
		}
    }
    
    /**
     * 判断圆形范围是否正确
     * @param center 圆心
     * @param radius 半径
     * @return
     */
    public boolean fitCircleFormat(String center, String radius) {
    	if (!center.matches("[0-9]*(\\.?)[0-9]*,[0-9]*(\\.?)[0-9]*")) {
    		return false;
    	}
    	if (StringUtils.isBlank(radius)) {
			return false;
		}
    	try {
    		Double.parseDouble(radius);
    	} catch (Exception e) {
			return false;
		}
    	return true;
    }
    
    /**
     * 根据圆形圆心和半径获得范围（格式：左下角坐标;右上角坐标）
     * @param center 圆心
     * @param radius 半径
     * @return
     */
    public String getRangeByCircle(String center, String radius) {
    	double unit = Double.valueOf(radius) / 2;
    	double centerLngX = Double.valueOf(center.split(PoiAoiConstant.SPLIT_COMMA)[0]);
    	double centerLatY = Double.valueOf(center.split(PoiAoiConstant.SPLIT_COMMA)[1]);
    	return (centerLngX-unit) + PoiAoiConstant.SPLIT_COMMA + (centerLatY-unit) + PoiAoiConstant.SEPARATOR_J + (centerLngX+unit) + PoiAoiConstant.SPLIT_COMMA + (centerLatY+unit);
    }
    
    /**
	 * 根据范围（格式：左下角坐标;右上角坐标）获得栅格
	 * @param prefix 前缀
	 * @param range 范围
	 * @return gridKeys
	 *         格式：
	 *             poi_grid_lng_lat
	 *             aoi_grid_lng_lat
	 */
    public Set<String> getGridKeys(String prefix, String range) {
		String minPointStr = range.split(PoiAoiConstant.SEPARATOR_J)[0];
		String maxPointStr = range.split(PoiAoiConstant.SEPARATOR_J)[1];
		double minPointLngX = CommonMethod.retainDigitsDouble(Double.valueOf(minPointStr.split(PoiAoiConstant.SPLIT_COMMA)[0]));
		double minPointLatY = CommonMethod.retainDigitsDouble(Double.valueOf(minPointStr.split(PoiAoiConstant.SPLIT_COMMA)[1]));
		double maxPointLngX = CommonMethod.retainDigitsDouble(Double.valueOf(maxPointStr.split(PoiAoiConstant.SPLIT_COMMA)[0])) + PoiAoiConstant.UNIT_DISTANCE;
		double maxPointLatY = CommonMethod.retainDigitsDouble(Double.valueOf(maxPointStr.split(PoiAoiConstant.SPLIT_COMMA)[1])) + PoiAoiConstant.UNIT_DISTANCE;
		Set<String> gridKeys = new HashSet<String>();
        double lng_x;
        double lat_y;
        for (int i = 0; (minPointLngX + PoiAoiConstant.UNIT_DISTANCE * i) < maxPointLngX; i++) {
            for (int j = 0; (minPointLatY + PoiAoiConstant.UNIT_DISTANCE * j) < maxPointLatY; j++) {
                lng_x = minPointLngX + PoiAoiConstant.UNIT_DISTANCE * i;
                lat_y = minPointLatY + PoiAoiConstant.UNIT_DISTANCE * j;
                gridKeys.add(prefix + PoiAoiConstant.SEPARATOR_R + CommonMethod.retainDigits(lng_x) + PoiAoiConstant.SEPARATOR_R + CommonMethod.retainDigits(lat_y));
            }
        }
        return gridKeys;
    }
    
    /**
	 * 根据范围（格式：左下角坐标;右上角坐标）获得栅格
	 * @param prefix 前缀
	 * @param range 范围
	 * @return gridKeys
	 *         格式：
	 *             poi_grid_lng_lat
	 *             aoi_grid_lng_lat
	 * @param center 圆心
	 * @param radius 半径            
	 */
    public Set<String> getGridKeysCircle(String prefix, String range, String center, String radius) {
		String minPointStr = range.split(PoiAoiConstant.SEPARATOR_J)[0];
		String maxPointStr = range.split(PoiAoiConstant.SEPARATOR_J)[1];
		double minPointLngX = CommonMethod.retainDigitsDouble(Double.valueOf(minPointStr.split(PoiAoiConstant.SPLIT_COMMA)[0]));
		double minPointLatY = CommonMethod.retainDigitsDouble(Double.valueOf(minPointStr.split(PoiAoiConstant.SPLIT_COMMA)[1]));
		double maxPointLngX = CommonMethod.retainDigitsDouble(Double.valueOf(maxPointStr.split(PoiAoiConstant.SPLIT_COMMA)[0])) + PoiAoiConstant.UNIT_DISTANCE;
		double maxPointLatY = CommonMethod.retainDigitsDouble(Double.valueOf(maxPointStr.split(PoiAoiConstant.SPLIT_COMMA)[1])) + PoiAoiConstant.UNIT_DISTANCE;
		//double r = Double.valueOf(radius);
		Set<String> gridKeys = new HashSet<String>();
        double lng_x;
        double lat_y;
        for (int i = 0; (minPointLngX + PoiAoiConstant.UNIT_DISTANCE * i) < maxPointLngX; i++) {
            for (int j = 0; (minPointLatY + PoiAoiConstant.UNIT_DISTANCE * j) < maxPointLatY; j++) {
                lng_x = minPointLngX + PoiAoiConstant.UNIT_DISTANCE * i;
                lat_y = minPointLatY + PoiAoiConstant.UNIT_DISTANCE * j;
                gridKeys.add(prefix + PoiAoiConstant.SEPARATOR_R + CommonMethod.retainDigits(lng_x) + PoiAoiConstant.SEPARATOR_R + CommonMethod.retainDigits(lat_y));
            }
        }
        return gridKeys;
    }
	
	public static void main(String[] args) {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Jedis redis = redisUtil.getJedisFromPool();
		redis.sadd("aoi_grid_12344.98_2312.30", "u01","u02","u03");
		redis.hset("u01", "city", "北京");
		redis.hset("u02", "city", "上海");
		redis.hset("u03", "city", "广州");
		System.out.println("end");
	}
}
