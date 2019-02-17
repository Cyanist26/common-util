package com.cuiwq.common.util.mybatis.generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseMetaDateApplication
 *
 * @author cuiwq
 * @date 2018-09-05 星期三
 */
public class DatabaseMetaDateApplication {
    
    private DatabaseMetaData dbMetaData;
    
    private Connection con;
    
    public DatabaseMetaDateApplication(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPasswd) {
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPasswd);
            dbMetaData = con.getMetaData();
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 获得表或视图中的所有列信息
     */
    public Table getTableColumns(String schemaName, String tableName) {
        Table table = new Table();
        try {
            ResultSet tables = dbMetaData.getTables(null, schemaName, tableName, new String[] {"TABLE" });
            if(tables.next()) {
                table.remark = tables.getString("REMARKS");
            }
            
            ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
            while(rs.next()) {
                String tableCat = rs.getString("TABLE_CAT");// 表目录（可能为空）
                String tableSchemaName = rs.getString("TABLE_SCHEM");// 表的架构（可能为空）
                String tableName_ = rs.getString("TABLE_NAME");// 表名
                String columnName = rs.getString("COLUMN_NAME");// 列名
                int dataType = rs.getInt("DATA_TYPE"); // 对应的java.sql.Types类型
                String dataTypeName = rs.getString("TYPE_NAME");// java.sql.Types类型
                // 名称
                int columnSize = rs.getInt("COLUMN_SIZE");// 列大小
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");// 小数位数
                int numPrecRadix = rs.getInt("NUM_PREC_RADIX");// 基数（通常是10或2）
                int nullAble = rs.getInt("NULLABLE");// 是否允许为null
                String remarks = rs.getString("REMARKS");// 列描述
                String columnDef = rs.getString("COLUMN_DEF");// 默认值
                int sqlDataType = rs.getInt("SQL_DATA_TYPE");// sql数据类型
                int sqlDatetimeSub = rs.getInt("SQL_DATETIME_SUB"); // SQL日期时间分?
                int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH"); // char类型的列中的最大字节数
                int ordinalPosition = rs.getInt("ORDINAL_POSITION"); // 表中列的索引（从1开始）
                
                /**
                 * ISO规则用来确定某一列的为空性。 是---如果该参数可以包括空值; 无---如果参数不能包含空值
                 * 空字符串---如果参数为空性是未知的
                 */
                String isNullAble = rs.getString("IS_NULLABLE");
                
                /**
                 * 指示此列是否是自动递增 是---如果该列是自动递增 无---如果不是自动递增列 空字串---如果不能确定它是否
                 * 列是自动递增的参数是未知
                 */
                String isAutoincrement = rs.getString("IS_AUTOINCREMENT");
                
                // System.out.println(tableCat + "-" + tableSchemaName + "-" +
                // tableName_ + "-" + columnName + "-" + dataType + "-" +
                // dataTypeName + "-" + columnSize + "-" + decimalDigits + "-" +
                // numPrecRadix + "-" + nullAble + "-" + remarks + "-" +
                // columnDef + "-" + sqlDataType + "-" + sqlDatetimeSub +
                // charOctetLength + "-" + ordinalPosition + "-" + isNullAble +
                // "-" + isAutoincrement + "-");
                Column column = new Column();
                column.setName(columnName);
                column.setFieldName(ColumnNameUtil.columnToLowerCamelCase(columnName));
                column.setGetter("get" + ColumnNameUtil.columnToUpperCamelCase(columnName));
                column.setSetter("set" + ColumnNameUtil.columnToUpperCamelCase(columnName));
                column.setJdbcType(standardizeJdbcType(dataTypeName));
                column.setJavaType(typeTransfer(column.getJdbcType()));
                column.setRemark(remarks);
                table.columns.add(column);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return table;
    }
    
    private String standardizeJdbcType(String jdbcType) {
        switch(jdbcType) {
            case "TINYINT UNSIGNED":
                return "TINYINT";
            case "SMALLINT UNSIGNED":
                return "SMALLINT";
            case "INTEGER UNSIGNED":
                return "INTEGER";
            case "INT":
            case "INT UNSIGNED":
                return "INTEGER";
            case "BIGINT UNSIGNED":
                return "BIGINT";
            case "DATETIME":
                return "TIMESTAMP";
            case "TEXT":
                return "LONGVARCHAR";
            case "JSON":
                return "VARCHAR";
            default:
                return jdbcType;
        }
    }
    
    private String typeTransfer(String jdbcType) {
        switch(jdbcType) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
                return "String";
            case "NUMERIC":
            case "DECIMAL":
                return "BigDecimal";
            case "TINYINT":
                return "Byte";
            case "SMALLINT":
                return "Short";
            case "INTEGER":
            case "INT":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "FLOAT":
            case "DOUBLE":
                return "Double";
            case "DATE":
                return "java.time.LocalDate";
            case "DATETIME":
            case "TIMESTAMP":
                return "java.time.LocalDateTime";
            default:
                return "";
        }
    }
    
    protected static class Table {
        
        public String name;
        
        public String remark;
        
        public List<Column> columns = new ArrayList<Column>();
    }
    
}
