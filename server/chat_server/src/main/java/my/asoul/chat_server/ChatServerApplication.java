package my.asoul.chat_server;

import my.asoul.chat_server.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hjy
 */
@SpringBootApplication
public class ChatServerApplication {

    @Autowired
    NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }

}
