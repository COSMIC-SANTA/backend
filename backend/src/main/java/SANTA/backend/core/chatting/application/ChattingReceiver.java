package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.config.ChattingConfig;
import SANTA.backend.core.chatting.domain.ChattingRoomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingReceiver {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = "chatting.queue.0")
    public void receive0(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = "spring/getMessage/"+message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.1")
    public void receive1(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.2")
    public void receive2(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.3")
    public void receive3(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.4")
    public void receive4(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.5")
    public void receive5(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.6")
    public void receive6(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.7")
    public void receive7(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.8")
    public void receive8(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.9")
    public void receive9(ChattingRoomMessage message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoom().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }
}
