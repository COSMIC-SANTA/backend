package SANTA.backend.context;

import SANTA.backend.core.banner.api.BannerApi;
import SANTA.backend.core.mountain.api.MountainApi;
import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.global.jwt.JWTFilter;
import SANTA.backend.global.jwt.JWTUtil;
import SANTA.backend.global.utils.api.rabbitmq.RabbitMQRequester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({MountainApi.class, BannerApi.class})
@AutoConfigureMockMvc(addFilters = false)
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    protected BannerService bannerService;

    @MockitoBean
    protected MountainService mountainService;

    @MockitoBean
    protected RabbitMQRequester rabbitMQRequester;

    @MockitoBean
    protected JWTUtil jwtUtil;

    @MockitoBean
    protected JWTFilter jwtFilter;

}
