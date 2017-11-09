package com.wt.lbsWeb.base.constant;

/**
 * 查询POI、AOI模块-常量类
 * @author Administrator
 *
 */
public class PoiAoiConstant {
	
	public static String SEPARATOR_R = "_";
	public static String SPLIT_COMMA = ",";
	public static String SEPARATOR_J = ";";

	/** 区域极值坐标：最小经纬度（左下角） */
    public static String EXTREME_POINT_MIN = "MIN";
    /** 区域极值坐标：最大经纬度（右上角） */
    public static String EXTREME_POINT_MAX = "MAX";
    /** 栅格单位距离 */
    public static double UNIT_DISTANCE = 0.01;
	
	//Redis key前缀
	public static String POI_PRE = "poi";
	public static String AOI_PRE = "aoi";
	public static String POI_CATEGORY_PRE = "poi_category";
	public static String AOI_CATEGORY_PRE = "aoi_category";
	public static String POI_GRID_PRE = "poi_grid";
	public static String AOI_GRID_PRE = "aoi_grid";
	
	//Redis AOI POI hash中的key
	public static String CITY_ID = "cityid";
	public static String LNG = "lng";
	public static String LAT = "lat";
	public static String CATEGORY = "category";
	public static String NAME = "name";
	public static String ADDRESS = "address";
	public static String LNGLAT = "lnglat";
	
	//Redis 管道一次查询数量
	public static int COUNT_SEARCH_UID = 3000;
	public static int COUNT_SEARCH_GRID = 1000;
	public static int COUNT_FIT_UID_GRID = 5000;
	
}
