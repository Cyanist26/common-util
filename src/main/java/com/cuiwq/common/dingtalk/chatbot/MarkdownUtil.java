package com.cuiwq.common.dingtalk.chatbot;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MAX_HEADER_TYPE;
import static com.cuiwq.common.dingtalk.chatbot.model.ChatbotConstants.MIN_HEADER_TYPE;

/**
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
public class MarkdownUtil {
    
    public static String getBoldText(String text) {
        return "**" + text + "**";
    }
    
    public static String getItalicText(String text) {
        return "*" + text + "*";
    }
    
    public static String getImageText(String imageUrl) {
        return "![image](" + imageUrl + ")";
    }
    
    public static String getReferenceText(String text) {
        return "> " + text;
    }
    
    public static String getLinkText(String text, String href) {
        return "[" + text + "](" + href + ")";
    }
    
    public static String getHeaderText(int headerType, String text) {
        if (headerType < MIN_HEADER_TYPE || headerType > MAX_HEADER_TYPE) {
            throw new IllegalArgumentException("headerType should be in [1, 6]");
        }
        
        StringBuilder header = new StringBuilder();
        IntStream.range(0, headerType).mapToObj(i -> "#").forEach(header::append);
        header.append(" ").append(text);
        return header.toString();
    }
    
    public static String getOrderListText(List<String> orderItem) {
        if (orderItem.isEmpty()) {
            return "";
        }
        
        return IntStream.range(0, orderItem.size()).mapToObj(i -> (i + 1) + ". " + orderItem.get(i) + "\n").collect(Collectors.joining());
    }
    
    public static String getUnorderListText(List<String> unorderItem) {
        if (unorderItem.isEmpty()) {
            return "";
        }
        
        return unorderItem.stream().map(s -> "- " + s + "\n").collect(Collectors.joining());
    }
    
}
