package SANTA.backend.core.mountain.application;

import SANTA.backend.global.utils.api.rabbitmq.RabbitMQRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MountainDataScheduler {

//    private final RabbitMQRequester rabbitMQRequester;
//
//    @Scheduled(cron = "0 0 0 0 1 * *", zone = "Asia/Seoul")
//    public void updateMountainDataMonthly(){
//        rabbitMQRequester.updateMountain(null);
//    }
}
