package SANTA.backend.global.utils.api;

import SANTA.backend.global.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateKoreanTourInfoResponder {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;

    @RabbitListener(queues = RabbitMQConfig.KOREAN_TOUR_INFO_SERVICE_QUEUE)
    public void consumeRequest(String data) {
        retryTemplate.execute(context -> {
            try {
                if (data.isEmpty() || data.isBlank())
                    throw new RuntimeException();
                /**
                 * Todo: 외부 API로부터 가져온 정보들을 DB에 저장.
                 */
            } catch (Exception e) {
                if (context.getRetryCount() >= 2) {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.INFO_SERVICE_DLX, data);
                } else {
                    throw e;
                }
            }
            return null;
        });

    }
}
