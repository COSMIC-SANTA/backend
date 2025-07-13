package SANTA.backend.global.utils.api;

import SANTA.backend.global.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KoreanTourInfoServiceResponder implements APIRequestConsumer{

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @RabbitListener(queues = RabbitMQConfig.KOREAN_TOUR_INFO_SERVICE_QUEUE)
    public void consumeRequest(String url) {
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

    }
}
