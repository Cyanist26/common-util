package com.cuiwq.common.util;

import java.util.Random;

/**
 * 获取一定长度的随机字符串
 *
 * @author rizenguo
 * @date 2019-01-08 星期二
 */
public class RandomStringUtil {
    
    private static final String BASE = "abcdefghijklmnopqrstuvwxyz0123456789";
    
    public static String getRandomStringByLength(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(BASE.charAt(random.nextInt(BASE.length())));
        }
        return sb.toString();
    }
    
}
