package com.cuiwq.common.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 封装分页查询返回值
 *
 * @author cuiwq
 * @date 2018-10-29 星期一
 */
@SuppressWarnings("unchecked")
public class Pager<D> implements Serializable {
    
    private List<D> records;
    
    private int totalPages;
    
    private int total;
    
    public Pager() {
    
    }
    
    public Pager(List<D> records, int total, int pageSize) {
        this.records = records;
        this.total = total;
        
        if(this.records == null) {
            this.records = Collections.EMPTY_LIST;
        }
        if(total < this.records.size()) {
            throw new IllegalArgumentException("记录总条数小于当前页记录条数");
        }
        if(pageSize < 1) {
            throw new IllegalArgumentException("分页大小必须大于0");
        }
        totalPages = total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
    }
    
    public List<D> getRecords() {
        return records;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public int getTotal() {
        return total;
    }
    
    /**
     *  仅用于缓存反序列化！
     * @param records records
     */
    public void setRecords(List<D> records) {
        this.records = records;
    }
    
    /**
     * 仅用于缓存反序列化！
     * @param totalPages totalPages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    /**
     * 仅用于缓存反序列化！
     * @param total total
     */
    public void setTotal(int total) {
        this.total = total;
    }
    
}
