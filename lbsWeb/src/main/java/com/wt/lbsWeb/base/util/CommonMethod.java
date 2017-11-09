package com.wt.lbsWeb.base.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 公用方法
 * Created by Administrator on 2017/11/1.
 */
public class CommonMethod {

    /**
     * DecimalFormat -- 定义格式
     */
    private static DecimalFormat formater = new DecimalFormat("#0.00");

    /**
     * 截取小数点后两位不四舍五入
     * @param number 数
     * @return 截取后的数
     */
    public static String retainDigits(double number) {
    	formater.setRoundingMode(RoundingMode.FLOOR); // 不四舍五入
        return formater.format(number);
    }
    
    /**
     * 截取小数点后两位不四舍五入
     * @param number 数
     * @return 截取后的数
     */
    public static Double retainDigitsDouble(double number) {
    	formater.setRoundingMode(RoundingMode.FLOOR); // 不四舍五入
        return Double.valueOf(formater.format(number));
    }
}
