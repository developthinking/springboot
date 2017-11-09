package com.wt.lbsWeb.base.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public class BaseAction {
	
	public final static String SUCCESS = "success";
	
	public final static String MSG = "msg";
	
	public final static String DATA = "data";
	
	public final static String LOGOUT_FLAG = "logoutFlag";
	
	public final static String STATUS = "status";
	
	public final static String RESULT = "result";
	
	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
	
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 所有ActionMap 统一从这里获取
	 * 
	 * @return
	 */
	public Map<String, Object> getRootMap() {
	
		Map<String, Object> rootMap = new HashMap<String, Object>();
		// 添加url到 Map中
		// rootMap.putAll(URLUtils.getUrlMap());
		return rootMap;
	}
	
	public ModelAndView forword(String viewName, Map context) {
	
		return new ModelAndView(viewName, context);
	}
	
	public ModelAndView error(String errMsg) {
	
		return new ModelAndView("error");
	}
}
