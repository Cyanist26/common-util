package com.cuiwq.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 文件工具类
 *
 * @author cuiwq
 * @date 2019-01-08 星期二
 */
@Slf4j
public class FileUtil {
    
    public static String getContent(String fileName) {
        return getContent(fileName, null);
    }
    
    public static String getContent(String fileName, String charSet) {
        BufferedReader reader = null;
        try {
            String content = "";
            if(charSet != null) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            }
            String tempString = null;
            while((tempString = reader.readLine()) != null) {
                content += tempString + "\n";
            }
            return content;
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(reader);
        }
    }
    
    public static String getContent(InputStream is) {
        BufferedReader reader = null;
        try {
            String content = "";
            reader = new BufferedReader(new InputStreamReader(is));
            String tempString = null;
            while((tempString = reader.readLine()) != null) {
                content += tempString + "\n";
            }
            return content;
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(reader);
        }
    }
    
    public static void write(String fileName, String content) {
        BufferedWriter bw = null;
        try {
            File file = new File(fileName);
            if(file.exists()) {
                bw = new BufferedWriter(new FileWriter(file, true));
            } else {
                bw = new BufferedWriter(new FileWriter(file));
            }
            bw.write(content);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(bw);
        }
    }
    
    public static boolean copyFile(File oldFile, String newPath) {
        if(!(oldFile.exists())) {
            log.warn("文件不存在：" + oldFile);
            return false;
        }
        if(!(oldFile.isFile())) {
            log.warn(oldFile + "不是文件");
            return false;
        }
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            inStream = new FileInputStream(oldFile);
            fs = new FileOutputStream(newPath);
            copy(inStream, fs);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(fs);
            close(inStream);
        }
        return true;
    }
    
    public static boolean copyFile(InputStream inStream, String newPath) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(newPath);
            copy(inStream, os);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(os);
        }
        return true;
    }
    
    public static void copy(InputStream inStream, FileOutputStream fs) throws IOException {
        byte[] buffer = new byte[1024];
        int byteread = 0;
        while((byteread = inStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
        }
    }
    
    public static boolean download(String oldFile, String newPath) {
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int byteread = 0;
            URL url = new URL(oldFile);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            inStream = conn.getInputStream();
            fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024 * 1024];
            while((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(fs);
            close(inStream);
        }
        return true;
    }
    
    public static boolean remove(String fileName) {
        try {
            File file = new File(fileName);
            if(file.exists()) {
                return file.delete();
            }
            return false;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void close(Closeable close) {
        try {
            if(close != null) {
                close.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Random randowm = new Random();
    
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    public static String getRandomName(String fileName) {
        if(fileName.lastIndexOf(".") == -1) {
            String mockFileName = df.format(new Date());
            mockFileName += randowm.nextInt(99999);
            return mockFileName;
        } else {
            String mockFileName = df.format(new Date());
            mockFileName += randowm.nextInt(99999);
            return mockFileName + fileName.substring(fileName.lastIndexOf("."));
        }
    }
    
    public static String formatSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if(size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if(size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if(size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else return String.format("%d B", size);
    }
    
    public static File find(File desc, String fileName, Integer level) {
        level++;
        if(level > 7) {
            return null;
        }
        if(desc == null) {
            return null;
        } else if(desc.isDirectory()) {
            File[] files = desc.listFiles();
            for(File child : files) {
                File file = find(child, fileName, level);
                if(file != null) {
                    return file;
                }
            }
        }
        if(desc.getName().equals(fileName)) {
            return desc;
        }
        return null;
    }
    
}
