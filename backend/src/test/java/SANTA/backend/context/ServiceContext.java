package SANTA.backend.context;

import SANTA.backend.core.chatting.application.ChattingService;
import SANTA.backend.core.chatting.domain.ChattingRepository;
import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.course.domain.CourseRepository;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.global.utils.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
    protected BannerInfoServiceRequester mountainInfoServiceRequester;

    @Autowired
    protected ChattingService chattingService;

    @Autowired
    protected ChattingRepository chattingRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected KaKaoMapServiceRequester kaKaoMapServiceRequester;

    @Autowired
    protected KakaoFacilityServiceRequester kakaoFacilityServiceRequester;

}
