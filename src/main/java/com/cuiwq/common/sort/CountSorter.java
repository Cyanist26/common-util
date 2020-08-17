package com.cuiwq.common.sort;

import java.util.Arrays;

/**
 * 计数排序
 *
 * @author cuiwq
 * @date 2020-08-17 星期一
 */
public class CountSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        int max = Arrays.stream(result).max().orElse(-1);
        int min = Arrays.stream(result).min().orElse(-1);
        if(min < 0) {
            /* 含有负数，无法使用计数排序 */
            return;
        }
        
        int[] count = new int[max + 1];
        Arrays.stream(result).forEach(i -> count[i]++);
    
        int k = 0;
        for(int r = 0; r < max + 1; r++) {
            for(int i = 0; i < count[r]; i++) {
                result[k++] = r;
            }
        }
    }
    
}