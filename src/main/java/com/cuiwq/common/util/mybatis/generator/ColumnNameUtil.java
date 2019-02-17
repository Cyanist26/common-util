package com.cuiwq.common.util.mybatis.generator;

/**
 * @author cuiwq
 * @date 2018-09-05 星期三
 */
public class ColumnNameUtil {
    
    public static String columnToLowerCamelCase(String columnName) {
        if (!columnName.contains("_")) {
            return columnName;
        }
        String pre = columnName.substring(0, columnName.indexOf("_"));
        String sub = columnName.substring(columnName.indexOf("_") + 2);
        String word = columnName.substring(columnName.indexOf("_") + 1, columnName.indexOf("_") + 2);
        return columnToLowerCamelCase(pre + word.toUpperCase() + sub);
    }
    
    public static String columnToUpperCamelCase(String columnName) {
        String sub = columnName.substring(1);
        String word = columnName.substring(0, 1);
        return columnToLowerCamelCase(word.toUpperCase() + sub);
    }
    
}
