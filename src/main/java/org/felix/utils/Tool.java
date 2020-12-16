package org.felix.utils;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author Felix
 */
public final class Tool {

    private static Logger log = LoggerFactory.getLogger(Tool.class);


    /**
     * 传入过期日期，过期返回true,未过期返回false
     * @param dateStr
     * @return
     */
    public static boolean dateExpired(String dateStr) {

        boolean flag = false;
        Date nowDate = new Date();
        Date parseInputDate = null;
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                //格式化日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                parseInputDate = sdf.parse(dateStr);

                flag = parseInputDate.before(nowDate);

            } catch (ParseException e) {
                log.error("dateExpired方法，日期解析异常，传入的日期为：" + dateStr);
            }
        } else {
            log.error("dateExpired方法，日期不能为空");
        }

        return flag;
    }

    /**
     * 判断传入的字符是否为空
     *
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date s = sdf.parse("1608085523065");
            //Date s = new Date();
           // s.setTime(1608085523065L);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
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
     *
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

    public static String getBrowserInfo(HttpServletRequest request) {
        String returnString = "";
        // 获取浏览器信息
        String ua = request.getHeader("User-Agent");
        try {
            // 转成UserAgent对象
            UserAgent userAgent = UserAgent.parseUserAgentString(ua);
            // 获取浏览器信息
            Browser browser = userAgent.getBrowser();
            // 获取系统信息
            OperatingSystem os = userAgent.getOperatingSystem();
            // 系统名称
            String system = os.getName();
            // 浏览器名称
            String browserName = browser.getName();
            returnString = browserName;
        } catch (Exception e) {
            log.error("获取[浏览器信息]时发生异常:{}", e.getLocalizedMessage());
            return returnString;
        }
        return returnString;
    }
}
