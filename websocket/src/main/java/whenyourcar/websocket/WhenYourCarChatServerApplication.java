package whenyourcar.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"whenyourcar"})
@EnableMongoRepositories(basePackages = "whenyourcar.mongo.repository")
public class WhenYourCarChatServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhenYourCarChatServerApplication.class, args);
    }
}
