package com.itheima.utils;

/**
 * 包名:com.itheima.travel.utils
 * 作者:Leevi
 * 日期2019-06-22  12:04
 */
public class StringUtil {
    /**
     * 判断字符串是否为空的方法
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str == null || "".equals(str) || "null".equals(str)) {
            //为空
            return true;
        }
        //不为空
        return false;
    }
}
