package whenyourcar_chat.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"whenyourcar_chat"})
@EnableJpaRepositories(basePackages = "whenyourcar_chat.domain.repository")
@EntityScan(basePackages = "whenyourcar_chat/domain/entity")
@EnableJpaAuditing
public class WhenYourCarChatServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhenYourCarChatServerApplication.class, args);
    }
}
