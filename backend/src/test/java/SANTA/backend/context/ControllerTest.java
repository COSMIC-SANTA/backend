package SANTA.backend.context;

import SANTA.backend.core.mountain.api.BannerApi;
import SANTA.backend.core.mountain.api.MountainApi;
import SANTA.backend.core.mountain.application.BannerService;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.global.utils.api.APIRequester;
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

}
