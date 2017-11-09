package com.wt.lbsWeb.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UTF8Util {

	public static String isoConverUtf8(String s) {
		try {
			s = new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static String convertUtf8(String s) {
		try {
			s = URLDecoder.decode(s, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
