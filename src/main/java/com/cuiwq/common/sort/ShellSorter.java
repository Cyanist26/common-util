package com.cuiwq.common.sort;

/**
 * 希尔排序
 *
 * @author cuiwq
 * @date 2020-08-16 星期日
 */
public class ShellSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        for(int step = result.length / 2; step >= 1; step /= 2) {
            sortOne(step);
        }
    }
    
    private void sortOne(int step) {
        for(int i = step; i < result.length; i++) {
            int j;
            int current = result[i];
            
            for(j = i; j > 0 && result[j - step] > current; j -= step) {
                result[j] = result[j - step];
            }
            
            result[j] = current;
        }
    }
    
}