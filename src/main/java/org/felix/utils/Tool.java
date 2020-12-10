package org.felix.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * @author Felix
 */
public final class Tool {


    /**
     * 判断传入的字符是否为空
     * @param charSequence
     * @return
     */
    public static boolean isBlank(final CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return true;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获得当前时间字符串
     *
     * @return
     */
    public static String getCurrentTimeString() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        String t = "test";
        System.err.println(isBlank(t));
    }

    /**
     *
     * @return
     */
    public static String getPrimaryKey() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(format);
        return nowStr + getRandomString(1, 9, 4);
    }

    /**
     * 使用了synchronized防止并发
     * @param min
     * @param max
     * @param length
     * @return
     */
    public synchronized static String getRandomString(int min, int max, int length) {
        Random rand = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            result.append(String.valueOf(rand.nextInt(max - min + 1) + min));
        }
        return result.toString();
    }
}
