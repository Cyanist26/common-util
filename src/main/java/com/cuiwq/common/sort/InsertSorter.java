package com.cuiwq.common.sort;

/**
 * 插入排序
 *
 * @author cuiwq
 * @date 2020-08-05 星期三
 */
public class InsertSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        /* 假设第一个元素已经有序，从第二个元素开始向前查找 */
        for(int i = 1; i < result.length; i++) {
            int j;
            /* 当前需要找到位置的元素 */
            int current = result[i];

            /* 往前查找，找到该元素的位置，过程中不断移动需要后移的元素 */
            for(j = i; j > 0 && result[j - 1] > current; j--) {
                countVisit();
                countSwap();
                result[j] = result[j -1];
            }

            /* 将找位置的元素插入到对应位置 */
            result[j] = current;
        }
    }
    
}