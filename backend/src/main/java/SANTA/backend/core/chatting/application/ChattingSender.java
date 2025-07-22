package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.config.ChattingConfig;
import SANTA.backend.core.chatting.domain.ChattingRoomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingSender {

    private final RabbitTemplate rabbitTemplate;

    public String sendMessage(Long roomId, ChattingRoomMessage message) {
        String routingKey = String.valueOf(roomId % 10);
        rabbitTemplate.convertAndSend(ChattingConfig.CHATTING_EXCHANGE, routingKey, message);
        return routingKey;
    }
}
