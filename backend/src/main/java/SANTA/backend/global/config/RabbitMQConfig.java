package SANTA.backend.global.config;

import SANTA.backend.global.common.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    public static final String KOREAN_TOUR_INFO_SERVICE_QUEUE = "koreanTourInfoServiceQueue";
    public static final String INFO_SERVICE_EXCHANGE = "infoServiceExchange";
    public static final String INFO_SERVICE_DLX = "infoServiceDeadLetterExchange";
    public static final String DLQ = "deadLetterQueue";

    private final AppProperties appProperties;

    @Bean
    public Queue koreanTourInfoServiceQueue(){
        return QueueBuilder.durable(KOREAN_TOUR_INFO_SERVICE_QUEUE)
                .withArgument("x-dead-letter-exchange",INFO_SERVICE_DLX)
                .withArgument("x-dead-letter-routing-key",DLQ)
                .build();
    }

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public TopicExchange infoServiceExchange(){
        return new TopicExchange(INFO_SERVICE_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange(){
        return new TopicExchange(INFO_SERVICE_DLX);
    }

    @Bean
    public Binding tourServiceBinding(){
        return BindingBuilder.bind(koreanTourInfoServiceQueue()).to(infoServiceExchange()).with(appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceRoutingKey());
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
