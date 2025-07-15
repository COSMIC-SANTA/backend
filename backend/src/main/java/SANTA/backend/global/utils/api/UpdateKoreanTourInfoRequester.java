package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateKoreanTourInfoRequester {

    private final RabbitTemplate rabbitTemplate;
    private final AppProperties appProperties;

    public void updateDatabase() {
        String exchange = RabbitMQConfig.INFO_SERVICE_DLX;
        String routingKey = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceRoutingKey();
        /**
         * Todo: 특정 시점마다 외부 API로부터 우리 테이블 형식에 맞도록 응답을 파싱 후 우리 DB에 데이터를 저장
         * ex) 외부 API에서 제공하는 모든 산 정보, 식당 정보 등을 가져와서 DB에 저장.
         */

         //Data data = ~Service.getData();
         //rabbitTemplate.convertAndSend(exchange,routingKey,data);
    }
}
