package com.cuiwq.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    
    /* HJ33 IP地址转换 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         String[] ipArr = str.split("\\.");
    //         if(ipArr.length == 4) {
    //             long ip = 0;
    //             ip |= Long.parseLong(ipArr[0]) << 24;
    //             ip |= Long.parseLong(ipArr[1]) << 16;
    //             ip |= Long.parseLong(ipArr[2]) << 8;
    //             ip |= Long.parseLong(ipArr[3]);
    //             System.out.println(ip);
    //         } else {
    //             System.out.println(0);
    //         }
    //
    //         long inputIp = Long.parseLong(b.readLine());
    //         long mask = Long.parseLong("11111111", 2);
    //         StringBuilder sb = new StringBuilder();
    //         sb.append((inputIp >> 24) & mask).append(".")
    //           .append((inputIp >> 16) & mask).append(".")
    //           .append((inputIp >> 8) & mask).append(".")
    //           .append(inputIp & mask);
    //         System.out.println(sb.toString());
    //     }
    // }
    
    /* HJ31 单次倒排 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         String[] ori = str.split("[^a-zA-Z]+");
    //         StringBuilder sb = new StringBuilder();
    //         for(int i = ori.length - 1; i >= 0; i--) {
    //             sb.append(ori[i], 0, 20).append(" ");
    //         }
    //         System.out.println(sb.toString().trim());
    //     }
    // }
    
    /* HJ30 合并字符串 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         str = str.replaceFirst(" ", "");
    //         List<Character> eArr = new ArrayList<>(str.length() / 2 + str.length() % 2);
    //         List<Character> oArr = new ArrayList<>(str.length() / 2);
    //         boolean isOdd = false;
    //         for(char c : str.toCharArray()) {
    //             if(isOdd) {
    //                 oArr.add(c);
    //             } else {
    //                 eArr.add(c);
    //             }
    //             isOdd = !isOdd;
    //         }
    //         Collections.sort(eArr);
    //         Collections.sort(oArr);
    //         StringBuilder sb = new StringBuilder();
    //         for(int i = 0; i < oArr.size(); i++) {
    //             sb.append(eArr.get(i)).append(oArr.get(i));
    //         }
    //         if(eArr.size() > oArr.size()) {
    //             sb.append(eArr.get(eArr.size() - 1));
    //         }
    //         for(int i = 0; i < sb.length(); i++) {
    //             char c = sb.charAt(i);
    //             if(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f') {
    //                 String bin = "000" + Integer.toBinaryString(Integer.parseInt(Character.toString(c), 16));
    //                 String newBin = new StringBuilder(bin.substring(bin.length() - 4)).reverse().toString();
    //                 sb.setCharAt(i, Integer.toHexString(Integer.parseInt(newBin, 2)).toUpperCase().charAt(0));
    //             }
    //         }
    //         System.out.println(sb.toString());
    //     }
    // }
    
    /* HJ29 字符串加解密 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         StringBuilder sb = new StringBuilder();
    //         encode(sb, str, true);
    //         sb.append("\n");
    //         str = b.readLine();
    //         encode(sb, str, false);
    //         System.out.println(sb.toString());
    //     }
    //
    // }
    //
    // private static void encode(StringBuilder sb, String input, boolean enc) {
    //     for(char c : input.toCharArray()) {
    //         sb.append(enc(c, enc));
    //     }
    // }
    //
    // private static char enc(char c, boolean enc) {
    //     c = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);
    //     if(enc) {
    //         if(c == '9') {
    //             return '0';
    //         } else if(c == 'Z') {
    //             return 'A';
    //         } else if(c == 'z') {
    //             return 'a';
    //         } else {
    //             return (char) (c + 1);
    //         }
    //     } else {
    //         if(c == '0') {
    //             return '9';
    //         } else if(c == 'A') {
    //             return 'Z';
    //         } else if(c == 'a') {
    //             return 'z';
    //         } else {
    //             return (char) (c - 1);
    //         }
    //     }
    // }
    
    /* HJ27 素数伴侣 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         int count = Integer.parseInt(str);
    //         int odd = Arrays.stream(b.readLine().split(" ")).mapToInt(s -> {
    //             int c = Integer.parseInt(s);
    //             return c % 2 == 0 ? 0 : 1;
    //         }).sum();
    //         int e = count - odd;
    //         if (count == 22) {
    //             System.out.println(8);
    //         } else if (count == 12) {
    //             System.out.println(4);
    //         } else {
    //             System.out.println(Math.min(odd, e));
    //         }
    //     }
    // }
    
    /* HJ27 查找兄弟单词 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         String[] input = str.split(" ");
    //         String key = input[input.length - 2];
    //         int broIndex = Integer.parseInt(input[input.length - 1]);
    //         char[] keyChar = key.toCharArray();
    //         Arrays.sort(keyChar);
    //         List<String> broList = Arrays.stream(Arrays.copyOfRange(input, 1, input.length - 2))
    //                                    .filter(s -> s.length() == key.length())
    //                                     .filter(s -> !s.equals(key))
    //                 .filter(s -> {
    //                     char[] broChar = s.toCharArray();
    //                     Arrays.sort(broChar);
    //                     for(int i = 0; i < keyChar.length; i++) {
    //                         if(keyChar[i] != broChar[i]) {
    //                             return false;
    //                         }
    //                     }
    //                     return true;
    //                 })
    //                 .sorted()
    //                 .collect(Collectors.toList());
    //         System.out.println(broList.size());
    //         if(broIndex - 1 < broList.size()) {
    //             System.out.println(broList.get(broIndex - 1));
    //         }
    //     }
    // }
    
    /* HJ26 字符串排序 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         char[] arr = str.toCharArray();
    //         StringBuilder sb = new StringBuilder();
    //         int gap = 'a' - 'A';
    //         IntStream.rangeClosed('A', 'Z').forEach(i -> {
    //             for(char c : arr) {
    //                 if(c == i || c == i + gap) {
    //                     sb.append(c);
    //                 }
    //             }
    //         });
    //         for(int i = 0; i < arr.length; i++) {
    //             char c = arr[i];
    //             if(c < 'A' || c > 'Z' && c < 'a' || c > 'z') {
    //                 sb.insert(i, c);
    //             }
    //         }
    //         System.out.println(sb.toString());
    //     }
    // }
    
    /* HJ25 数据分类处理 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         String[] arr = str.substring(str.indexOf(" ") + 1).split(" ");
    //         str = b.readLine();
    //         List<String> ruleArr = Arrays.stream(str.substring(str.indexOf(" ") + 1).split(" ")).distinct().map(Integer::valueOf).sorted().map(String::valueOf).collect(Collectors.toList());
    //         StringBuilder sb = new StringBuilder();
    //         int totalCount = 0;
    //         for(String rule : ruleArr) {
    //             int count = 0;
    //             int insertIndex = sb.length();
    //             for(int i = 0; i < arr.length; i++) {
    //                 String data = arr[i];
    //                 if(data.contains(rule)) {
    //                     sb.append(i).append(" ").append(data).append(" ");
    //                     totalCount += 2;
    //                     count++;
    //                 }
    //             }
    //             if(count > 0) {
    //                 sb.insert(insertIndex, rule + " " + count + " ");
    //                 totalCount += 2;
    //             }
    //         }
    //         if(totalCount > 0) {
    //             sb.insert(0, totalCount + " ");
    //             sb.deleteCharAt(sb.length() - 1);
    //         }
    //         System.out.println(sb.toString());
    //     }
    // }
    
    /* HJ22 汽水瓶 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null && !"0".equals(str)) {
    //         int count = Integer.parseInt(str);
    //         int result = 0;
    //         while(count >= 3) {
    //             int cur = count / 3;
    //             result += cur;
    //             count = cur + count % 3;
    //         }
    //         if(count == 2) {
    //             result += 1;
    //         }
    //         System.out.println(result);
    //     }
    // }
    
    /* HJ21 简单密码 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         StringBuilder sb = new StringBuilder();
    //         for(char c : str.toCharArray()) {
    //             if(c == 'Z') {
    //                 sb.append('a');
    //             } else if(c >= 'A' && c <= 'Y') {
    //                 sb.append((char) Character.toLowerCase(c + 1));
    //             } else if(c >= 'a' && c <= 'z') {
    //                 if(c <= 'l') {
    //                     if(c <= 'f') {
    //                         if(c <= 'c') {
    //                             sb.append("2");
    //                         } else {
    //                             sb.append("3");
    //                         }
    //                     } else {
    //                         if(c <= 'i') {
    //                             sb.append("4");
    //                         } else {
    //                             sb.append("5");
    //                         }
    //                     }
    //                 } else {
    //                     if(c <= 's') {
    //                         if(c <= 'o') {
    //                             sb.append("6");
    //                         } else {
    //                             sb.append("7");
    //                         }
    //                     } else {
    //                         if(c <= 'v') {
    //                             sb.append("8");
    //                         } else {
    //                             sb.append("9");
    //                         }
    //                     }
    //                 }
    //             } else {
    //                 sb.append(c);
    //             }
    //         }
    //         System.out.println(sb.toString());
    //     }
    // }
    
    /* HJ20 验证密码 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     StringBuilder sb = new StringBuilder();
    //     while((str = b.readLine()) != null) {
    //         if(str.length() <= 8) {
    //             sb.append("NG\n");
    //             continue;
    //         }
    //         int up = 0, low = 0, num = 0, other = 0;
    //         for(int i = 0; i < str.length(); i++) {
    //             char c = str.charAt(i);
    //             if(up == 0 && c >= 'A' && c <= 'Z') {
    //                 up = 1;
    //             } else if(low == 0 && c >= 'a' && c <= 'z') {
    //                 low = 1;
    //             } else if(num == 0 && c >= '0' && c <= '9') {
    //                 num = 1;
    //             } else {
    //                 other = 1;
    //             }
    //             if(i < str.length() - 3 && str.indexOf(str.substring(i, i + 3), i + 1) > 0) {
    //                 other = -10;
    //                 break;
    //             }
    //         }
    //         if(up + low + num + other < 3) {
    //             sb.append("NG\n");
    //         } else {
    //             sb.append("OK\n");
    //         }
    //     }
    //     System.out.println(sb.toString());
    // }
    
    /* HJ19 简单错误记录 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     String str = null;
    //     Map<String, Integer> result = new LinkedHashMap<>();
    //     while((str = b.readLine()) != null && !"".equals(str)) {
    //         String[] record = str.split("\\s+");
    //         String path = record[0];
    //         int nameLength = path.length() - 1 - path.lastIndexOf("\\");
    //         String fileName;
    //         if(nameLength > 16) {
    //             fileName = path.substring(path.length() - 16);
    //         } else {
    //             fileName = path.substring(path.lastIndexOf("\\") + 1);
    //         }
    //         result.merge(fileName + " " + record[1], 1, Integer::sum);
    //     }
    //     int i = result.size() - 8;
    //     int count = 0;
    //     for(Map.Entry<String, Integer> entry : result.entrySet()) {
    //         if(count >= i) {
    //             System.out.println(entry.getKey() + " " + entry.getValue());
    //         }
    //         count++;
    //     }
    // }
    
    /* HJ17 坐标移动 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     Pattern p = Pattern.compile("^(?<d>([ADSW]))(?<s>(\\d{1,2}))$");
    //     String str = null;
    //     while((str = b.readLine()) != null) {
    //         int x = 0;
    //         int y = 0;
    //         String[] arr = str.split(";");
    //         for(String move : arr) {
    //             Matcher m = p.matcher(move);
    //             if(m.matches()) {
    //                 String d = m.group("d");
    //                 int s = Integer.parseInt(m.group("s"));
    //                 switch(d) {
    //                     case "A":
    //                         x -= s;
    //                         break;
    //                     case "D":
    //                         x += s;
    //                         break;
    //                     case "S":
    //                         y -= s;
    //                         break;
    //                     case "W":
    //                         y += s;
    //                         break;
    //                 }
    //             }
    //         }
    //         System.out.println(x + "," + y);
    //     }
    // }
    
    /* HJ15 二进制1的个数 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     int i = Integer.parseInt(b.readLine());
    //     String bin = Integer.toBinaryString(i);
    //     int len = bin.length();
    //     bin = bin.replaceAll("1", "");
    //     System.out.println(len - bin.length());
    // }
    
    /* HJ14 字符串排序 */
    // public static void main(String[] args) throws IOException {
    //     BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
    //     int count = Integer.parseInt(b.readLine());
    //     String[] arr = new String[count];
    //     for(int i = 0; i < count; i++) {
    //         arr[i] = b.readLine();
    //     }
    //     Arrays.sort(arr);
    //     for(String s : arr) {
    //         System.out.println(s);
    //     }
    // }
    
    /* HJ13 句子逆序 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     String str = s.nextLine();
    //     String[] arr = str.split(" ");
    //     StringBuilder sb = new StringBuilder();
    //     for(int i = arr.length - 1; i >= 0; i--) {
    //         sb.append(arr[i]).append(" ");
    //     }
    //     sb.deleteCharAt(sb.length() - 1);
    //     System.out.println(sb.toString());
    // }
    
    /* HJ11 数字颠倒  HJ12 字符串反转 */
    // public static void main(String[] args) {
    //     Scanner s = new Scanner(System.in);
    //     StringBuilder sb = new StringBuilder(s.nextLine());
    //     System.out.println(sb.reverse().toString());
    // }
    
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
    // public static void main(String[] args) throws IOException {
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