package com.cuiwq.common.sort;

/**
 * 折半插入排序
 *
 * @author cuiwq
 * @date 2020-08-05 星期三
 */
public class HalfInsertSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        /* 假设第一个元素已经有序，从第二个元素开始向前查找 */
        for(int i = 1; i < result.length; i++) {
            /* 当前需要找到位置的元素 */
            int current = result[i];
            
            /* 二分查找最终需要插入的位置 */
            int left = 0;
            int right = i - 1;
            while(left <= right) {
                int mid = (left + right) / 2;
                if(result[mid] > current) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
    
            /* 将找位置的元素插入到对应位置 */
            for(int j = i; j > right + 1; j--) {
                result[j] = result[j - 1];
            }
            result[right + 1] = current;
        }
    }
    
}