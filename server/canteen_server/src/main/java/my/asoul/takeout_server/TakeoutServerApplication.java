package my.asoul.takeout_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author hjy
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class TakeoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutServerApplication.class, args);
    }

}
