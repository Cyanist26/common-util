package com.cuiwq.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认httpClient
 *
 * @author cuiwq
 * @date 2018-09-29 星期六
 */
@Slf4j
public class HttpClient {
    
    private static final int SOCKET_TIMEOUT = 2 * 60 * 1000;
    
    private static final int CONNECT_TIMEOUT = 2 * 60 * 1000;
    
    private static final int MAX_TOTAL = 100;
    
    private static final int DEFAULT_MAX_PER_ROUTE = 20;
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    public static final Map<String, String> UTF8_FORM_HEADER = Collections.unmodifiableMap(new HashMap<String, String>(){
        {put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");}
    });
    
    public static final Map<String, String> GBK_FORM_HEADER = Collections.unmodifiableMap(new HashMap<String, String>(){
        {put("Content-type", "application/x-www-form-urlencoded; charset=GBK");}
    });
    
    public static final Map<String, String> UTF8_JSON_HEADER = Collections.unmodifiableMap(new HashMap<String, String>() {
        {put("Content-type", "application/json; charset=UTF-8");}
    });
    
    public static final Map<String, String> GBK_JSON_HEADER = Collections.unmodifiableMap(new HashMap<String, String>() {
        {put("Content-type", "application/json; charset=GBK");}
    });
    
    private static final CloseableHttpClient CLIENT = getClient();
    
    private static class DefaultTrustManager implements X509TrustManager {
        
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }
    
    private static CloseableHttpClient getClient() {
        CloseableHttpClient client;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, (hostname, session) -> {
                // 默认认证通过。
                return true;
            });
            
            /* 连接池 */
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(MAX_TOTAL);
            connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
            
            /* 重试机制 */
            HttpRequestRetryHandler customRetryHandler = (exception, executionCount, context) -> {
                // 如果已经重试了3次，就放弃
                if (executionCount >= 3) {
                    return false;
                }
                // 如果服务器丢掉了连接，重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                // 超时
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                // 目标服务器不可达
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                // 连接被拒绝
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                // ssl握手异常
                if (exception instanceof SSLException) {
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                return !(request instanceof HttpEntityEnclosingRequest);
            };
            
            /* 构建client */
            client = HttpClients.custom()
                                .setDefaultRequestConfig(RequestConfig.custom()
                                                                      .setSocketTimeout(SOCKET_TIMEOUT)
                                                                      .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                                                                      .build())
                                .setConnectionManager(connManager)
                                .setRetryHandler(customRetryHandler)
                                .build();
            
            log.info("初始化默认HttpClient成功");
            return client;
        } catch(Exception e) {
            log.error("初始化默认HttpClient失败 : [msg : {}]", e.getMessage(), e);
            return HttpClients.createDefault();
        }
    }
    
    /**
     * 发送GET请求，使用默认的UTF-8编码
     * @param url url
     * @param params params
     * @return 响应报文
     */
    public static String sendGet(String url, String params) {
        return sendGet(url, UTF8_FORM_HEADER, params, DEFAULT_CHARSET);
    }
    
    /**
     * 发送GET请求
     * @param url url
     * @param headers headers
     * @param params params
     * @param charset charset
     * @return 响应报文
     */
    public static String sendGet(String url, Map<String, String> headers, String params, final String charset) {
        HttpGet httpGet = null;
        try {
            URL targetUrl = buildGetUrl(url, params);
            httpGet = new HttpGet(targetUrl.toString());
            
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
            return CLIENT.execute(httpGet, new BasicResponseHandler() {
                @Override
                public String handleEntity(final HttpEntity entity) throws IOException {
                    return EntityUtils.toString(entity, charset);
                }
            });
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码格式 [msg = {}]", e.getMessage());
        } catch (HttpResponseException e) {
            log.error("服务端响应异常 [statusCode = {}][msg = {}]", e.getStatusCode(), e.getMessage());
        } catch (ClientProtocolException e) {
            log.error("客户端协议异常 [msg = {}]", e.getMessage());
        } catch(IOException e) {
            log.error("数据流读写错误 [msg = {}]", e.getMessage());
        } finally {
            if(httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return "";
    }
    
    /**
     * 构造GET URL
     * @param strUrl 字符串URL
     * @param query 查询条件
     * @return 响应报文
     * @throws IOException
     */
    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return url;
        }
        
        StringBuilder sb = new StringBuilder(strUrl);
        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                sb.append(query);
            } else {
                sb.append("?").append(query);
            }
        } else {
            if (strUrl.endsWith("&")) {
                sb.append(query);
            } else {
                sb.append("&").append(query);
            }
        }
        
        return new URL(sb.toString());
    }
    
    /**
     * 发送FROM格式的POST请求，使用默认的UTF-8编码
     * @param url url
     * @param param param
     * @return 响应报文
     */
    public static String sendFormPost(String url, String param) {
        return sendPost(url, UTF8_FORM_HEADER, param, DEFAULT_CHARSET);
    }
    
    /**
     * 发送JSON格式的POST请求，使用默认的UTF-8编码
     * @param url url
     * @param param param
     * @return 响应报文
     */
    public static String sendJsonPost(String url, String param) {
        return sendPost(url, UTF8_JSON_HEADER, param, DEFAULT_CHARSET);
    }
    
    /**
     * 发送POST请求
     * @param url url
     * @param headers headers
     * @param param param
     * @param charset charset
     * @return 响应报文
     */
    public static String sendPost(String url, Map<String, String> headers, String param, final String charset) {
        HttpPost httpPost = new HttpPost(url);
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(new StringEntity(param, charset));
        try {
            return CLIENT.execute(httpPost, new BasicResponseHandler() {
                @Override
                public String handleEntity(final HttpEntity entity) throws IOException {
                    return EntityUtils.toString(entity, charset);
                }
            });
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码格式 [msg = {}]", e.getMessage());
        } catch (HttpResponseException e) {
            log.error("服务端响应异常 [statusCode = {}][msg = {}]", e.getStatusCode(), e.getMessage());
        } catch (ClientProtocolException e) {
            log.error("客户端协议异常 [msg = {}]", e.getMessage());
        } catch(IOException e) {
            log.error("数据流读写错误 [msg = {}]", e.getMessage());
        } finally {
            httpPost.releaseConnection();
        }
        return "";
    }
    
}
