package SANTA.backend.mountain.api;

import SANTA.backend.context.ControllerTest;
import SANTA.backend.core.banner.dto.BannerMountainResponse;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
            BannerMountainResponse bannerMountainResponse = new BannerMountainResponse(1L, "새로운 산", "image_url", 1L);
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

        @Test
        @WithMockUser(username = "testuser", roles = {"USER"})
        void 인기_산_배너_조회_요청() throws Exception {
            // given
            List<BannerMountainResponse> mountainResponseList = new ArrayList<>();
            BannerMountainResponse bannerMountainResponse1 = new BannerMountainResponse(1L, "부모산", "http://image", 300L);
            BannerMountainResponse bannerMountainResponse2 = new BannerMountainResponse(2L, "한라산", "http://image", 200L);
            BannerMountainResponse bannerMountainResponse3 = new BannerMountainResponse(3L, "지리산", "http://image", 150L);
            mountainResponseList.add(bannerMountainResponse1);
            mountainResponseList.add(bannerMountainResponse2);
            mountainResponseList.add(bannerMountainResponse3);
            BannerResponse bannerResponse = new BannerResponse(null, mountainResponseList);

            given(bannerService.getPopularMountains()).willReturn(bannerResponse);

            // when & then
            mockMvc.perform(get("/api/main/banner")
                            .param("type", "best")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.mountains[0].name").value("부모산"))
                    .andExpect(jsonPath("$.data.mountains[0].visitCount").value(300L))
                    .andExpect(jsonPath("$.data.mountains[1].name").value("한라산"))
                    .andExpect(jsonPath("$.data.mountains[1].visitCount").value(200L))
                    .andExpect(jsonPath("$.data.mountains[2].name").value("지리산"))
                    .andExpect(jsonPath("$.data.mountains[2].visitCount").value(150L));
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
