package com.cuiwq.common.util.mybatis.generator;

import lombok.Getter;
import lombok.Setter;

/**
 * @author cuiwq
 * @date 2018-09-05 星期三
 */
@Getter
@Setter
public class Column {
    
    private String name;
    
    private String fieldName;
    
    private String getter;
    
    private String setter;
    
    private String jdbcType;
    
    private String javaType;
    
    private String remark;
    
    private boolean haveEnum;
    
}
