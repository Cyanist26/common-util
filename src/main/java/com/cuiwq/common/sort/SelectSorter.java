package com.cuiwq.common.sort;

/**
 * 选择排序
 *
 * @author cuiwq
 * @date 2020-08-05 星期三
 */
public class SelectSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        /* 外层循环，n个元素最多需要n - 1次 */
        for(int i = 0; i < result.length - 1; i++) {
            /* 最小值下标，起始值为开始比较的位置 */
            int minIndex = i;
            for(int j = i + 1; j < result.length; j++) {
                countVisit();
                /* 当前元素比最小元素小，覆盖最小值下表 */
                if(result[j] < result[minIndex]) {
                    minIndex = j;
                }
            }
            /* 如果最小值下标发生了变化，则交换 */
            if(minIndex != i) {
                swap(result, i, minIndex);
            }
        }
    }
    
}