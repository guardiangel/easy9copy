package org.felix.captcha;

import org.felix.aop.constants.Constant;
import org.felix.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
@Component
public class ArithmeticCaptcha {

    private final Logger log = LoggerFactory.getLogger(ArithmeticCaptcha.class);

    @Resource
    private RedisService redisService;

    @Value("${system.loginCodeTimeOut}")
    private Integer loginCodeTimeOut;

    private final int imageWidth = 70;

    private final int imageHigh = 26;

    public void createImage(HttpServletRequest request, HttpServletResponse response, String time) {

        log.info("登录图片验证码存Redis有效时间：{}分钟", loginCodeTimeOut);

        try {
            BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHigh, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.getGraphics();
            createBackground(graphics);
            // 生成min至max质检的随机整数
            int min = 1;
            int max = 9;
            int i1 = new Random().nextInt(max - min + 1) + min;
            int i2 = new Random().nextInt(max - min + 1) + min;
            createCharacter(graphics, i1, i2);

            String captchaCodeKey = Constant.PICTURE_VERIFICATION + time;
            redisService.set(captchaCodeKey, String.valueOf(i1 + i2), loginCodeTimeOut, TimeUnit.MINUTES);
            graphics.dispose();
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "JPEG", outputStream);
            outputStream.close();

        } catch (Exception e) {
            log.error("生成图片验证码时发生异常：{}", e);
        }
    }

    private void createCharacter(Graphics graphics, int i1, int i2) {
        String[] fontTypes = {"\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66"};
        Random random = new Random();
        String r = i1 + "+" + i2 + "=?";
        graphics.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
        graphics.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, 18));
        graphics.drawString(r, 1, 20);
    }

    private void createBackground(Graphics graphics) {

        graphics.setColor(getRandColor(220, 250));
        graphics.fillRect(0, 0, imageWidth, imageHigh);
// 加入干扰线条
        for (int i = 0; i < 3; i++) {
            graphics.setColor(getRandColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(imageWidth);
            int y = random.nextInt(imageHigh);
            int x1 = random.nextInt(imageWidth);
            int y1 = random.nextInt(imageHigh);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    // 颜色
    private Color getRandColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f),
                f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

}
