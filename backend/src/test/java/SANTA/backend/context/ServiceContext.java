package SANTA.backend.context;

import SANTA.backend.core.chatting.application.ChattingService;
import SANTA.backend.core.chatting.domain.ChattingRepository;
import SANTA.backend.core.mountain.application.BannerService;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.global.config.RabbitMQConfig;
import SANTA.backend.global.utils.api.APIRequester;
import SANTA.backend.global.utils.api.KoreanTourInfoServiceRequester;
import SANTA.backend.global.utils.api.MountainInfoServiceRequester;
import jakarta.persistence.EntityManager;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest(properties = {
        "spring.rabbitmq.listener.simple.auto-startup=false",
        "spring.rabbitmq.listener.direct.auto-startup=false"
})
public abstract class ServiceContext {

    @Autowired
    protected MountainService mountainService;

    @Autowired
    protected BannerService bannerService;

    @Autowired
    protected MountainRepository mountainJPARepository;

    @Autowired
    protected APIRequester apiRequester;

    @Autowired
    protected KoreanTourInfoServiceRequester  koreanTourInfoServiceRequester;

    @Autowired
    protected MountainInfoServiceRequester mountainInfoServiceRequester;

    @Autowired
    protected ChattingService chattingService;

    @Autowired
    protected ChattingRepository chattingRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;
}
