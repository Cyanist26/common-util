package com.cuiwq.common.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author cuiwq
 * @date 2020-08-17 星期一
 */
public class MergeSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        mergeSort(0, result.length - 1);
    }
    
    private void mergeSort(int left, int right) {
        if(left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(left, mid);
        mergeSort(mid + 1, right);
        merge(left, right, mid);
    }
    
    private void merge(int left, int right, int mid) {
        int length = right - left + 1;
        int[] temp = new int[length];
        
        int i = left, j = mid + 1, k = 0;
        while(i <= mid && j <= right) {
            if(result[i] < result[j]) {
                temp[k++] = result[i++];
            } else {
                temp[k++] = result[j++];
            }
        }
        
        while(i <= mid) {
            temp[k++] = result[i++];
        }
        
        while(j <= right) {
            temp[k++] = result[j++];
        }
    
        System.arraycopy(temp, 0, result, left, length);
    }
    
}