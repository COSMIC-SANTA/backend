package SANTA.backend.global.utils.api.rabbitmq;

import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.config.RabbitMQConfig;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQRequester {

    private final RabbitTemplate rabbitTemplate;
    private final AppProperties appProperties;

    public void updateMountain(@Nullable String locationName) {
        String exchange = RabbitMQConfig.INFO_EXCHANGE;
        String routingKey = appProperties.getForest().getRoutingKey();
        rabbitTemplate.convertAndSend(exchange,routingKey,locationName);
    }
}
