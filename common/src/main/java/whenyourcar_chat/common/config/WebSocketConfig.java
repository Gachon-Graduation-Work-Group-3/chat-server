package whenyourcar_chat.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import whenyourcar_chat.common.handler.HttpSessionHandshakeInterceptor;
import whenyourcar_chat.common.handler.WebSocketHandler;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor;
    private final WebSocketHandler webSocketHandler;


    @Value("${spring.data.rabbitmq.host}")
    private String RABBITMQ_HOST;

    /*@Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
        MessageMatcherDelegatingAuthorizationManager.Builder messages =
                new MessageMatcherDelegatingAuthorizationManager.Builder();

        messages
                .simpTypeMatchers(SimpMessageType.CONNECT,
                        SimpMessageType.HEARTBEAT,
                        SimpMessageType.UNSUBSCRIBE,
                        SimpMessageType.DISCONNECT).permitAll()
                .simpSubscribeDestMatchers("/sub/**").permitAll()
                .simpMessageDestMatchers("/pub/**").permitAll()
                .simpSubscribeDestMatchers("/queue/**").permitAll()
                .simpSubscribeDestMatchers("/exchange/**").permitAll()
                .simpSubscribeDestMatchers("/topic/**").permitAll()
                .anyMessage().denyAll();

        return messages.build();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }*/

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(httpSessionHandshakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub")
                .enableStompBrokerRelay("/topic", "/queue", "/exchange")
                .setRelayHost(RABBITMQ_HOST)
                .setVirtualHost("/")
                .setRelayPort(61613)
                .setSystemLogin("guest")
                .setSystemPasscode("guest")
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketHandler);
    }
}
