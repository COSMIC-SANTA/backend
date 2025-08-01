package SANTA.backend.mountain.api;

import SANTA.backend.context.ControllerTest;
import SANTA.backend.core.mountain.dto.BannerMountainResponse;
import SANTA.backend.core.mountain.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BannerApiTest extends ControllerTest {

    @Nested
    class 배너_조회 {

        @ParameterizedTest
        @WithMockUser(username = "testuser", roles = {"USER"})
        @MethodSource({"provideInterest"})
        void 배너_리스트_조회_요청(Interest interest) throws Exception {
            //given
            List<BannerMountainResponse> mountainResponseList = new ArrayList<>();
            BannerMountainResponse bannerMountainResponse = new BannerMountainResponse(1L, "새로운 산", "image_url");
            mountainResponseList.add(bannerMountainResponse);
            BannerResponse bannerResponse = new BannerResponse(interest, mountainResponseList);

            given(bannerService.getInterestingMountains(interest)).willReturn(bannerResponse);
            //when & then
            mockMvc.perform(get("/api/main/banner")
                    .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("새로운 산"))
                    .andExpect(jsonPath("$.image_url").value("image_url"));
        }

        public static Stream<Arguments> provideInterest() {
            return Stream.of(
                    Arguments.of(Interest.LOW),
                    Arguments.of(Interest.ACTIVITY),
                    Arguments.of(Interest.HIGH)
            );
        }


    }
}
