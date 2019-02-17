package com.cuiwq.common.domain.type;

import com.cuiwq.common.api.CommonEnum;

/**
 * 接口调用日志 - 方法类型
 *
 * @author cuiwq
 * @date 2018-09-10 星期一
 */
public enum AccessLogMethodType implements CommonEnum<AccessLogMethodType> {
    
    UNKNOWN(0, "未知"),
    PAY(1, "支付"),
    QUERY(2, "查询订单"),
    REFUND(3, "退款"),
    REVOKE(4, "撤销订单"),
    CALLBACK(5, "回调"),
    UNION_CALLBACK(6, "银联回调"),
    CLOSE(7, "关闭订单"),
    BATCH_REVIEW(8, "跑批"),
    DB_INSERT(10, "数据库新增"),
    DB_QUERY(11, "数据库查询"),
    DB_UPDATE(12, "数据库更新"),
    DB_DELETE(13, "数据库删除"),
    SYSTEM(20, "系统操作")
    ;
    
    private final int code;
    
    private final String name;
    
    AccessLogMethodType(int code, String name) {
        this.code = code;
        this.name = name;
    }
    
    @Override
    public int getCode() {
        return code;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public AccessLogMethodType getTypeByCode(int code) {
        return valueOf(code);
    }
    
    public static AccessLogMethodType valueOf(int code) {
        for (AccessLogMethodType type : AccessLogMethodType.values()) {
            if (code == type.getCode()) {
                return type;
            }
        }
        return AccessLogMethodType.UNKNOWN;
    }
    
}