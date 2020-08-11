package com.cuiwq.common.sort;

/**
 * 快速排序
 *
 * @author cuiwq
 * @date 2020-08-04 星期二
 */
public class QuickSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        recursionSort(0, result.length - 1);
    }
    
    private void recursionSort(int left, int right) {
        if(left >= right) {
            return;
        }
        int mid = optimizePartition(left, right);
        recursionSort(left, mid - 1);
        recursionSort(mid + 1, right);
    }
    
    private int partition(int left, int right) {
        int current = result[left];
        int currentIndex = left;
        while(left < right) {
            while(left < right && result[right] >= current) {
                right --;
            }
            while(left < right && result[left] <= current) {
                left++;
            }
            swap(result, left, right);
        }
        swap(result, currentIndex, left);
        return left;
    }
    
    private int optimizePartition(int left, int right) {
        int current = result[left];
        while(left < right) {
            while(left < right && result[right] >= current) {
                right --;
            }
            result[left] = result[right];
            while(left < right && result[left] <= current) {
                left++;
            }
            result[right] = result[left];
        }
        result[left] = current;
        return left;
    }
    
}