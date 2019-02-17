package com.cuiwq.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cuiwq
 * @create 2018-12-21 星期五
 */
public class UnicodeUtil {
    
    private static final Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
    
    public static String unicodeToString(String str) {
        Matcher matcher = pattern.matcher(str);
        char ch;
        while(matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
    
}