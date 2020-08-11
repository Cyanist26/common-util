package com.cuiwq.common.sort;

/**
 * 冒泡排序
 *
 * @author cuiwq
 * @date 2020-08-04 星期二
 */
public class BubbleSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        /* 外层循环，n个元素最多需要n次 */
        for(int i = 0; i < result.length; i++) {
            boolean finish = true;
            /* 内层循环，检查乱序部分是否有需要调换的元素 */
            for(int j = result.length - 1; j > i; j--) {
                countVisit();
                if(result[j] < result[j-1]) {
                    swap(result, j-1, j);
                    finish = false;
                }
            }
            /* 内层循环结束时，可以认为前i个元素是有序的，
            下一次外循环不需要再处理这部分元素 */
            /* 本次外循环没有交换任何元素，排序已结束 */
            if(finish) {
                break;
            }
        }
    }
    
}