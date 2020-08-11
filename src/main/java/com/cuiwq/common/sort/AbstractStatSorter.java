package com.cuiwq.common.sort;

import java.util.Arrays;

/**
 * @author cuiwq
 * @date 2020-08-05 星期三
 */
public abstract class AbstractStatSorter implements Sorter {
    
    private int swapCounter = 0;
    
    private int visitCounter = 0;
    
    protected int[] ori;
    
    protected int[] result;
    
    private boolean showStep = false;
    
    private boolean success = true;
    
    private int errorIndex = -1;
    
    private long duration;
    
    protected void countSwap() {
        swapCounter++;
    }
    
    protected void countVisit() {
        visitCounter++;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends AbstractStatSorter> T showStep() {
        showStep = true;
        return (T) this;
    }
    
    protected void swap(int[] arr, int i, int j) {
        if(showStep) {
            System.out.print(Arrays.toString(arr) + " ---> ");
        }
        countSwap();
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        if(showStep) {
            System.out.println(Arrays.toString(arr));
        }
    }
    
    @Override
    public String sortInt(int[] array) {
        if(array == null || array.length < 2) {
            return "array can not be null or empty";
        }
        ori = Arrays.copyOf(array, array.length);
        result = Arrays.copyOf(ori, ori.length);
        long start = System.currentTimeMillis();
        sortInt();
        duration = System.currentTimeMillis() - start;
        checkResult();
        return "\n┌--[ " + this.getClass().getSimpleName() + " ]--\n" +
                "Ori\t\t\t = " + Arrays.toString(ori) + "\n" +
                "Result\t\t = " + Arrays.toString(result) + "\n" +
                "Length\t\t = " + ori.length + "\n" +
                "Success\t\t = " + success + "\n" +
                "ErrorIndex\t = " + errorIndex + "\n" +
                "SwapCount\t = " + swapCounter + "\n" +
                "VisitCount\t = " + visitCounter + "\n" +
                "Duration(ms) = " + duration + "\n" +
                "└--";
    }
    
    private void checkResult() {
        if(result == null || result.length < 2) {
            return;
        }
        for(int i = 0; i < result.length - 1; i++) {
            if(result[i] > result[i+1]) {
                success = false;
                errorIndex = i;
                return;
            }
        }
    }
    
    public abstract void sortInt();
    
}