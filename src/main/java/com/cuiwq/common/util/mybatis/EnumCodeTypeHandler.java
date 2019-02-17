package com.cuiwq.common.util.mybatis;

import com.cuiwq.common.api.CommonEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举类处理器
 *
 * @author cuiwq
 * @date 2019-01-08 星期二
 */
public class EnumCodeTypeHandler<E extends Enum<E> & CommonEnum<E>> extends BaseTypeHandler<E> {
    
    private final E type;
    
    public EnumCodeTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        E[] types = type.getEnumConstants();
        if (types == null || types.length == 0) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum jdbcType.");
        }
        this.type = types[0];
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }
    
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int s = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return type.getTypeByCode(s);
        }
    }
    
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int s = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return type.getTypeByCode(s);
        }
    }
    
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return type.getTypeByCode(i);
        }
    }
    
}
