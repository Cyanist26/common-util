import com.cuiwq.common.LinkList;
import com.cuiwq.common.sort.*;

import java.util.Arrays;

/**
 * @author cuiwq
 * @date 2019-01-24 星期四
 */
public class SimpleTester {
    
    public static void main(String[] args) {
        System.out.println("----------------------------- start -----------------------------");
    
        int count = 30;
        int[] array = new int[count];
        for(int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
    
        System.out.println(new BubbleSorter().sortInt(array));
        System.out.println(new SelectSorter().sortInt(array));
        System.out.println(new InsertSorter().sortInt(array));
        System.out.println(new HalfInsertSorter().sortInt(array));
        System.out.println(new QuickSorter().sortInt(array));
        System.out.println(new HeapSorter().sortInt(array));
        System.out.println(new CountSorter().sortInt(array));
        System.out.println(new MergeSorter().sortInt(array));
    
        System.out.println("----------------------------- end -----------------------------");
    }
    
}