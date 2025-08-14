package SANTA.backend.global.utils.api.rabbitmq;

import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.global.config.RabbitMQConfig;
import SANTA.backend.global.utils.api.APIRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQResponder {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;
    private final APIRequester apiRequester;
    private final BannerService bannerService;

    @RabbitListener(queues = RabbitMQConfig.MOUNTAIN_INFO_QUEUE)
    public void updateBanner(String locationName) {
        log.info("✅ 메시지 수신됨: locationName={}", locationName);
        retryTemplate.execute(context -> {
            try {
                List<Banner> banners = new ArrayList<>();
                banners = apiRequester.getBannersWithImages(locationName);
                log.info("✅ 배너 수: {}", banners.size());

                for (Banner banner : banners) {
                    log.info("배너 업데이트 실행됨 {}", banner.getName());
                    banner.setVisitCount(0L);
                }
                bannerService.saveBanners(banners);
            } catch (Exception e) {
                if (context.getRetryCount() >= 2) {
                    log.error("❌ 배너 저장 중 예외 발생 {}", e.getMessage());
                    rabbitTemplate.convertAndSend(RabbitMQConfig.INFO_DLX, locationName);
                } else {
                    throw e;
                }
            }
            return null;
        });
    }
}
