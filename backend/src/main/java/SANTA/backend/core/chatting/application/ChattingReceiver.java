package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingReceiver {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = "chatting.queue.0")
    public void receive0(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.1")
    public void receive1(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.2")
    public void receive2(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.3")
    public void receive3(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.4")
    public void receive4(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.5")
    public void receive5(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.6")
    public void receive6(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.7")
    public void receive7(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.8")
    public void receive8(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }

    @RabbitListener(queues = "chatting.queue.9")
    public void receive9(ChattingRoomMessageEntity message, @Header("amqp_receivedRoutingKey") String routingKey) {
        String clientUrl = message.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        String payload = message.getMessage();
        simpMessagingTemplate.convertAndSend(clientUrl,payload);
    }
}
