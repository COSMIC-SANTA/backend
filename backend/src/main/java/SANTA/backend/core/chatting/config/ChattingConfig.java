package SANTA.backend.core.chatting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChattingConfig {
    public static final String CHATTING_EXCHANGE = "chatting.exchange";
    public static final String CHATTING_QUEUE0 = "chatting.queue.0";
    public static final String CHATTING_QUEUE1 = "chatting.queue.1";
    public static final String CHATTING_QUEUE2 = "chatting.queue.2";
    public static final String CHATTING_QUEUE3 = "chatting.queue.3";
    public static final String CHATTING_QUEUE4 = "chatting.queue.4";
    public static final String CHATTING_QUEUE5 = "chatting.queue.5";
    public static final String CHATTING_QUEUE6 = "chatting.queue.6";
    public static final String CHATTING_QUEUE7 = "chatting.queue.7";
    public static final String CHATTING_QUEUE8 = "chatting.queue.8";
    public static final String CHATTING_QUEUE9 = "chatting.queue.9";


    @Bean(name = "chatting_exchange") @Qualifier(value = "chattingExchange")
    public TopicExchange chattingExchange(){
        return new TopicExchange(CHATTING_EXCHANGE);
    }

    @Bean
    public Queue chattingQueue0(){
        return QueueBuilder.durable(CHATTING_QUEUE0).build();
    }

    @Bean
    public Queue chattingQueue1(){
        return QueueBuilder.durable(CHATTING_QUEUE1).build();
    }

    @Bean
    public Queue chattingQueue2(){
        return QueueBuilder.durable(CHATTING_QUEUE2).build();
    }

    @Bean
    public Queue chattingQueue3(){
        return QueueBuilder.durable(CHATTING_QUEUE3).build();
    }

    @Bean
    public Queue chattingQueue4(){
        return QueueBuilder.durable(CHATTING_QUEUE4).build();
    }

    @Bean
    public Queue chattingQueue5(){
        return QueueBuilder.durable(CHATTING_QUEUE5).build();
    }

    @Bean
    public Queue chattingQueue6(){
        return QueueBuilder.durable(CHATTING_QUEUE6).build();
    }

    @Bean
    public Queue chattingQueue7(){
        return QueueBuilder.durable(CHATTING_QUEUE7).build();
    }

    @Bean
    public Queue chattingQueue8(){
        return QueueBuilder.durable(CHATTING_QUEUE8).build();
    }

    @Bean
    public Queue chattingQueue9(){
        return QueueBuilder.durable(CHATTING_QUEUE9).build();
    }

    @Bean
    public Binding chattingBinding0(){
        return BindingBuilder.bind(chattingQueue0()).to(chattingExchange()).with("0");
    }

    @Bean
    public Binding chattingBinding1(){
        return BindingBuilder.bind(chattingQueue1()).to(chattingExchange()).with("1");
    }

    @Bean
    public Binding chattingBinding2(){
        return BindingBuilder.bind(chattingQueue2()).to(chattingExchange()).with("2");
    }

    @Bean
    public Binding chattingBinding3(){
        return BindingBuilder.bind(chattingQueue3()).to(chattingExchange()).with("3");
    }

    @Bean
    public Binding chattingBinding4(){
        return BindingBuilder.bind(chattingQueue4()).to(chattingExchange()).with("4");
    }

    @Bean
    public Binding chattingBinding5(){
        return BindingBuilder.bind(chattingQueue5()).to(chattingExchange()).with("5");
    }

    @Bean
    public Binding chattingBinding6(){
        return BindingBuilder.bind(chattingQueue6()).to(chattingExchange()).with("6");
    }

    @Bean
    public Binding chattingBinding7(){
        return BindingBuilder.bind(chattingQueue7()).to(chattingExchange()).with("7");
    }

    @Bean
    public Binding chattingBinding8(){
        return BindingBuilder.bind(chattingQueue8()).to(chattingExchange()).with("8");
    }

    @Bean
    public Binding chattingBinding9(){
        return BindingBuilder.bind(chattingQueue9()).to(chattingExchange()).with("9");
    }

}
