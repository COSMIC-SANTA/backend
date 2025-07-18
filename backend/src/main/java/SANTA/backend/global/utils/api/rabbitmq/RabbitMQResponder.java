package SANTA.backend.global.utils.api.rabbitmq;

import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.global.config.RabbitMQConfig;
import SANTA.backend.global.utils.api.APIRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RabbitMQResponder {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;
    private final APIRequester apiRequester;
    private final MountainService mountainService;

    @RabbitListener(queues = RabbitMQConfig.MOUNTAIN_INFO_QUEUE)
    public void updateMountain(String locationName) {
        retryTemplate.execute(context -> {
            try {
                List<Mountain> mountains = new ArrayList<>();
                if (locationName.isEmpty() || locationName.isBlank()){
                    mountains = apiRequester.getMountains();

                }else{
                    mountains = apiRequester.getMountains(locationName);
                }
                mountainService.saveMountains(mountains);
            } catch (Exception e) {
                if (context.getRetryCount() >= 2) {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.INFO_DLX, locationName);
                } else {
                    throw e;
                }
            }
            return null;
        });
    }
}
