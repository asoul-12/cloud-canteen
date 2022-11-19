package my.asoul.takeout_server;

import my.asoul.takeout_server.util.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 4512
 * @date 2022/10/3 17:00
 */
@SpringBootTest
public class TestEmail {

    @Autowired
    MailUtil mailUtil;

    @Test
    void sendMail() {
        mailUtil.sendMail("123","531116266@qq.com");
    }

}
