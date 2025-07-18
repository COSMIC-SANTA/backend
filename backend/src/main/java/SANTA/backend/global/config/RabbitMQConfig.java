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
    public static final String MOUNTAIN_INFO_QUEUE = "mountainInfoQueue";
    public static final String INFO_EXCHANGE = "infoExchange";
    public static final String INFO_DLX = "infoDeadLetterExchange";
    public static final String DLQ = "deadLetterQueue";

    private final AppProperties appProperties;

    @Bean
    public Queue koreanTourInfoServiceQueue(){
        return QueueBuilder.durable(KOREAN_TOUR_INFO_SERVICE_QUEUE)
                .withArgument("x-dead-letter-exchange", INFO_DLX)
                .withArgument("x-dead-letter-routing-key",DLQ)
                .build();
    }

    @Bean
    public Queue mountainInfoQueue(){
        return QueueBuilder.durable(MOUNTAIN_INFO_QUEUE)
                .withArgument("x-dead-letter-exchange",INFO_DLX)
                .withArgument("x-dead-letter-routing-key",DLQ)
                .build();
    }

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public TopicExchange infoServiceExchange(){
        return new TopicExchange(INFO_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange(){
        return new TopicExchange(INFO_DLX);
    }

    @Bean
    public Binding tourInfoBinding(){
        return BindingBuilder.bind(koreanTourInfoServiceQueue()).to(infoServiceExchange()).with(appProperties.getKoreaTourOrganization().getRoutingKey());
    }

    @Bean
    public Binding mountainInfoBinding(){
        return BindingBuilder.bind(mountainInfoQueue()).to(infoServiceExchange()).with(appProperties.getForest().getRoutingKey());
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
