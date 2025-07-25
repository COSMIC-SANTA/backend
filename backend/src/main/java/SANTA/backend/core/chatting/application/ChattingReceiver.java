package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.dto.ChattingRoomMessageDto;
import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChattingReceiver {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = "chatting.queue.0")
    public void receive0(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive0 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.1")
    public void receive1(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive1 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.2")
    public void receive2(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive2 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.3")
    public void receive3(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive3 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.4")
    public void receive4(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive4 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.5")
    public void receive5(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive5 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.6")
    public void receive6(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive6 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.7")
    public void receive7(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive7 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.8")
    public void receive8(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive8 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.9")
    public void receive9(ChattingRoomMessageDto message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.clientUrl();
        String payload = message.message();
        log.info("receive9 메시지 전달받음 clientUrl = {} payload ={}",clientUrl,payload);
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }
}
