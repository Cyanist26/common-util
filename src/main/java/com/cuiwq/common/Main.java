package com.cuiwq.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    
    /* HJ10 字符个数统计 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     String str = s.nextLine();
    //     char[] arr = str.toCharArray();
    //     Set<Integer> set = new HashSet<>(128);
    //     for(char c : arr) {
    //         set.add((int) c);
    //     }
    //     System.out.println(set.size());
    // }
    
    /* HJ9 提取不重复数 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     String str = s.nextLine();
    //     Set<Integer> set = new LinkedHashSet<>(str.length());
    //     String[] arr = str.split("");
    //     for(int i = arr.length - 1; i >= 0; i--) {
    //         set.add(Integer.valueOf(arr[i]));
    //     }
    //     for(Integer i : set) {
    //         System.out.print(i);
    //     }
    // }
    
    /* HJ8合并表记录 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = b.readLine();
    //     int size = Integer.parseInt(str);
    //     Map<Integer, Integer> map = new HashMap<>(size);
    //     while((str = b.readLine()) != null) {
    //         String[] entry = str.split(" ");
    //         Integer key = Integer.valueOf(entry[0]);
    //         map.put(key, map.getOrDefault(key, 0) + Integer.parseInt(entry[1]));
    //         if(map.size() >= size) {
    //             break;
    //         }
    //     }
    //     map.forEach((key, value) -> {
    //         System.out.println(key + " " + value);
    //     });
    // }
    
    /* HJ7取近似值 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     double d = Double.parseDouble(s.nextLine());
    //     int i = (int) d;
    //     System.out.println(d - i >= 0.5 ? i + 1 : i);
    // }
    
    /* HJ6 质数因子 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     long l = Long.parseLong(s.nextLine());
    //     StringBuilder sb = new StringBuilder();
    //     for(int i = 2; i <= Math.sqrt(l); i++) {
    //         if(l % i == 0) {
    //             sb.append(i).append(" ");
    //             l = l / i;
    //             i--;
    //         }
    //     }
    //     sb.append(l).append(" ");
    //     System.out.println(sb.toString());
    // }
    
    /* HJ5 进制转换 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     int zeroCode = "0".charAt(0);
    //     int gap = "A".charAt(0) - zeroCode - 10;
    //     while(s.hasNext()) {
    //         int[] num = s.nextLine().substring(2).chars().map(chr -> {
    //             int code = chr - zeroCode;
    //             if(code > 9) {
    //                 code = code - gap;
    //             }
    //             return code;
    //         }).toArray();
    //         int result = 0;
    //         for(int i = num.length - 1; i >= 0; i--) {
    //             result += (1 << 4 * (num.length - 1 - i)) * num[i];
    //         }
    //         System.out.println(result);
    //     }
    // }
    
    /* HJ4 字符串分隔 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     while(s.hasNext()) {
    //         StringBuilder str = new StringBuilder(s.nextLine());
    //         int r = str.length() % 8;
    //         int size = str.length() / 8;
    //         if(r > 0) {
    //             size += 1;
    //             str.append("00000000").substring(0, 8 * size);
    //         }
    //         for(int i = 0; i < size; i++) {
    //             System.out.println(str.substring(8 * i, 8 * (i + 1)));
    //         }
    //     }
    // }
    
    /* HJ3 明明的随机数 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     while(s.hasNext()) {
    //         int count = s.nextInt();
    //         Set<Integer> result = new TreeSet<>();
    //         for(int i = 0; i < count; i++) {
    //             result.add(s.nextInt());
    //         }
    //         for(Integer integer : result) {
    //             System.out.println(integer);
    //         }
    //     }
    // }
    
    /* HJ2 计算字符个数 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     String str = s.nextLine().toLowerCase();
    //     int oriL = str.length();
    //     String c = s.nextLine().substring(0, 1).toLowerCase();
    //     str = str.replaceAll(c, "");
    //     System.out.println(oriL - str.length());
    // }
    
    /* HJ1 计算字符串最后一个单词的长度，单词以空格隔开。 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     String str = s.nextLine().trim();
    //     System.out.println(str.length() - 1 - str.lastIndexOf(" "));
    // }
    
    /*  */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     String str = s.nextLine();
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //
    //     }
    //
    // }
    
}