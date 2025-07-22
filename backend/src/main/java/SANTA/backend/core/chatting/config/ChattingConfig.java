package SANTA.backend.core.chatting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChattingConfig {
    public static final String CHATTING_EXCHANGE = "chatting.exchange";

    @Bean(name = "chatting_exchange") @Qualifier(value = "chattingExchange")
    public TopicExchange chattingExchange(){
        return new TopicExchange(CHATTING_EXCHANGE);
    }

}
