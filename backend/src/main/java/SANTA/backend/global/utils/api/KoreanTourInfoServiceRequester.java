package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KoreanTourInfoServiceRequester implements APIRequestProducer{

    private final RabbitTemplate rabbitTemplate;
    private final AppProperties appProperties;

    @Override
    public void sendRequest() {
        String exchange = RabbitMQConfig.INFO_SERVICE_DLX;
        String routingKey = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceRoutingKey();
        String url = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceURL();
        rabbitTemplate.convertAndSend(exchange,routingKey,url);
    }
}
