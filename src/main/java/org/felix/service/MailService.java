package org.felix.service;

import freemarker.template.Template;
import org.felix.model.dto.EmailNoticeDto;
import org.felix.model.ro.EmailRO;
import org.felix.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Felix
 */
@Component
public class MailService {

    private Logger log = LoggerFactory.getLogger(MailService.class);

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送普通文本邮件
     * @param emailRO
     */
    @Async
    public void sendSimpleMail(EmailRO emailRO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emailRO.getTo());
        message.setSubject(emailRO.getObject());
        message.setText(emailRO.getContent());
        try {
            mailSender.send(message);
            log.info("发送给[{}]的普通邮件成功！", emailRO.getTo());
        } catch (Exception e) {
            log.error("发送普通邮件失败：{}", e);
        }
    }

    /**
     * 发送带HTML标签的邮件
     * @param emailRO
     */
    @Async
    public void sendHtmlMail(EmailRO emailRO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //true 表⽰示需要创建⼀一个 multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(emailRO.getTo());
            helper.setSubject(emailRO.getObject());
            helper.setText(emailRO.getContent(), true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送HTML邮件失败：{}", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param emailRO
     * @param filePath
     */
    @Async
    public void sendAttachmentMail(EmailRO emailRO, String filePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //true 表⽰示需要创建⼀一个 multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(emailRO.getTo());
            helper.setSubject(emailRO.getObject());
            helper.setText(emailRO.getContent(), true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送带附件邮件失败：{}", e);
        }
    }

    /**
     * 实现模板发送邮件
     * @param to
     * @param object
     * @param dto
     * @param templateName
     * @param filePath
     */
    //filePath:E:\Test\linchanglan.jpg
    @Async
    public void sendEmailByFreemarker(String to, String object, EmailNoticeDto dto, String templateName, String filePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            /*
            发送者
             */
            helper.setFrom(from);
            /**
             * 接收者
             */
            helper.setTo(to);
            /**
             * 邮件标题
             */
            helper.setSubject(object);
            if(!Tool.isBlank(filePath)){
                //添加附件
                FileSystemResource file = new FileSystemResource(new File(filePath));
                helper.addAttachment("attachment" + filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()), file);
            }
            Map<String, Object> model = new HashMap<>();
            model.put("params", dto);
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送模板邮件失败：{}", e);
        }
    }


}
