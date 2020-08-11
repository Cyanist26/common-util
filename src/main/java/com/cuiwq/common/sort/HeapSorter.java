package com.cuiwq.common.sort;

/**
 * 堆排序
 *
 * @author cuiwq
 * @date 2020-08-05 星期三
 */
public class HeapSorter extends AbstractStatSorter implements Sorter {
    
    @Override
    public void sortInt() {
        int length = result.length;
        
        buildMaxHeap();
        
        for(int i = length - 1; i > 0; i--) {
            swap(result, 0, i);
            length--;
            heapify(0, length);
        }
    }
    
    private void buildMaxHeap() {
        /* 从最后一个父节点开始建立大顶堆 */
        for(int pIndex = (int) Math.floor(result.length >> 1); pIndex >= 0; pIndex--) {
            heapify(pIndex, result.length);
        }
    }
    
    private void heapify(int pIndex, int length) {
        int leftIndex = 2 * pIndex + 1;
        int rightIndex = 2 * pIndex + 2;
        int maxIndex = pIndex;
        
        if(leftIndex < length && result[leftIndex] > result[maxIndex]) {
            maxIndex = leftIndex;
        }
        
        if(rightIndex < length && result[rightIndex] > result[maxIndex]) {
            maxIndex = rightIndex;
        }
        
        /* 需要交换父子节点，递归向下检查 */
        if(maxIndex != pIndex) {
            swap(result, maxIndex, pIndex);
            heapify(maxIndex, length);
        }
    }
    
}