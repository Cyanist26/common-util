package com.cuiwq.common.dingtalk.chatbot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cuiwq.common.HttpClient;
import com.cuiwq.common.dingtalk.chatbot.model.LinkMessage;
import com.cuiwq.common.dingtalk.chatbot.model.MarkdownMessage;
import com.cuiwq.common.dingtalk.chatbot.model.Message;
import com.cuiwq.common.dingtalk.chatbot.model.TextMessage;
import com.cuiwq.common.dingtalk.chatbot.quota.Quota;
import com.cuiwq.common.domain.constant.FormaterConstant;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 钉钉机器人客户端
 *
 * @author cuiwq
 * @date 2019-01-22 星期二
 */
@Slf4j
public class ChatbotClient {
    
    public static final int MAX_QUOTA = 20;
    
    private static final int WARNING_QUEUE_SIZE = 300;
    
    private static final String HOOK_ADDRESS_PREFIX = "https://oapi.dingtalk.com/robot/send?access_token=";
    
    private final String HOOK_ADDRESS;
    
    private final String NAME;
    
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    
    private final BlockingQueue<Message> retryQueue = new LinkedBlockingQueue<>();
    
    private boolean active = false;
    
    private Thread sendThread;
    
    private Thread retryThread;
    
    private ScheduledExecutorService scheduler;
    
    private Quota quota;
    
    public ChatbotClient(String token, String clientName, Quota quota) {
        this.HOOK_ADDRESS = HOOK_ADDRESS_PREFIX + token;
        this.NAME = clientName;
        this.quota = quota;
        initThread();
        initQuotaKey();
        log.info("Dingtalk ChatBot - {} init successfully.", NAME);
    }
    
    public void sendTextMessage(String text) {
        TextMessage msg = new TextMessage(text);
        append(msg);
    }
    
    public void sendTextMessage(String text, String... atPhones) {
        TextMessage msg = new TextMessage(text);
        msg.setAtMobiles(Arrays.asList(atPhones));
        append(msg);
    }
    
    public void sendTextMessageAtAll(String text) {
        TextMessage msg = new TextMessage(text);
        msg.setIsAtAll(true);
        append(msg);
    }
    
    public void sendLinkMessage(String title, String text, String picUrl, String messageUrl) {
        LinkMessage msg = new LinkMessage(title, text, picUrl, messageUrl);
        append(msg);
    }
    
    public void sendMessage(Message msg) {
        append(msg);
    }
    
    public void clearQueue() {
        queue.clear();
    }
    
    public void clearRetryQueue() {
        retryQueue.clear();
    }
    
    public void stop() {
        log.warn("Destroy Dingtalk ChatBot - {}.", NAME);
        log.warn("queueSize = {}, retryQueueSize = {}", queue.size(), retryQueue.size());
        active = false;
        sendThread.interrupt();
        retryThread.interrupt();
        scheduler.shutdown();
        quota.destoryQuota();
        queue.clear();
        retryQueue.clear();
        
        log.warn("Dingtalk ChatBot - {} has been destroyed", NAME);
    }
    
    private void initThread() {
        sendThread = new Thread(new SendThread());
        sendThread.setName(NAME);
        retryThread = new Thread(new RetryThread());
        retryThread.setName(NAME + "-RETRY");
        sendThread.start();
        retryThread.start();
    }
    
    private void initQuotaKey() {
        quota.initQuota();
    
        /* 检查队列长度定时任务 */
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if(queue.size() + retryQueue.size() > WARNING_QUEUE_SIZE) {
                MarkdownMessage msg = new MarkdownMessage("Warn : queue to long");
                msg.addHeaderText(3, "Too many Messages in the queue.");
                msg.addHeaderText(3, "Check this server now.");
                msg.addUnorderListText("ClientName : " + NAME,
                        "Time : " + LocalDateTime.now().format(FormaterConstant.timestampFormatter),
                        "QueueSize : " + queue.size(),
                        "RetryQueueSize : " + retryQueue.size());
                msg.setIsAtAll(true);
                sendMessageExec(msg);
            }
        }, 0L, 30L, TimeUnit.MINUTES);
    }
    
    private void append(Message msg) {
        msg.verify();
        try {
            queue.put(msg);
            log.info("Put message into queue. [msg = {}]", msg.toJsonString());
        } catch(InterruptedException e) {
            log.error("Fail to put message into queue.", e);
        }
    }
    
    private void sendMessageExec(Message msg) {
        log.info("Use send quota [ {} ]", quota.getQuota());
        String response = HttpClient.sendJsonPost(HOOK_ADDRESS, msg.toJsonString());
        try {
            JSONObject obj = JSON.parseObject(response);
            Integer errCode = obj.getInteger("errcode");
            if(errCode == null || !errCode.equals(0)) {
                log.warn("Send message failure, retry send later [msg = {}]", obj.getString("errmsg"));
                retryQueue.offer(msg);
            }
        } catch(Exception e) {
            log.warn("Parse send result error, retry send later", e);
            retryQueue.offer(msg);
        }
    }
    
    private class SendThread implements Runnable {
    
        @Override
        public void run() {
            log.info("Starting Dingtalk ChatBot - {} send thread", NAME);
            active = true;
            try {
                while(active) {
                    Message msg = queue.take();
                    sendMessageExec(msg);
                }
            } catch(InterruptedException e) {
                log.warn("Shutting down Dingtalk ChatBot - {} send thread", NAME);
            }
        }
        
    }
    
    
    private class RetryThread implements Runnable {
    
        @Override
        public void run() {
            log.info("Starting Dingtalk ChatBot - {} retry thread", NAME);
            active = true;
            try {
                while(active) {
                    Message msg = retryQueue.take();
                    sendMessageExec(msg);
                }
            } catch(InterruptedException e) {
                log.warn("Shutting down Dingtalk ChatBot - {} retry thread", NAME);
            }
        }
        
    }
    
}
