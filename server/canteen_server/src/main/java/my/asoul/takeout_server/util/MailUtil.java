package my.asoul.takeout_server.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout_server.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author 4512
 * @date 2022/10/3 14:31
 */
@Component
@Data
@Slf4j
public class MailUtil {

    @Value("${takeout.mail.sender}")
    private String sender;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String content, String email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(email);
        mail.setSubject("激活账号");
        mail.setText("点击下方链接激活账号\n"
                + "http://localhost/admin/active/"
                + content
                + "\n如不是本人操作请无视");
        try {
            mailSender.send(mail);
        } catch (Exception e) {
            log.error(e.toString());
            throw new CommonException("邮箱无效");
        }
    }

    public void sendMail(String email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        RandomGenerator generator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(generator);
        // 重新生成code
        lineCaptcha.createCode();
        mail.setFrom(sender);
        mail.setTo(email);
        mail.setSubject("激活账号");
        mail.setText("本次登录验证码\n"
                + lineCaptcha.getCode()
                + "\n如不是本人操作请无视");
        try {
            mailSender.send(mail);
        } catch (Exception e) {
            log.error(e.toString());
            throw new CommonException("邮箱无效");
        }
    }


}
