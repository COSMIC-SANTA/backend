package SANTA.backend.mountain.api;

import SANTA.backend.context.ControllerTest;
import SANTA.backend.core.banner.dto.BannerMountainResponse;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.mountain.dto.MountainDTO;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BannerApiTest extends ControllerTest {

    @Nested
    class 배너_조회 {

        @ParameterizedTest
        @WithMockUser(username = "testuser", roles = {"USER"})
        @MethodSource("provideInterest")
        void 배너_리스트_조회_요청(Interest interest) throws Exception {
            //given
            List<BannerMountainResponse> mountainResponseList = new ArrayList<>();
            BannerMountainResponse bannerMountainResponse = new BannerMountainResponse(1L, "새로운 산", "image_url", 1L);
            mountainResponseList.add(bannerMountainResponse);
            BannerResponse bannerResponse = new BannerResponse(interest, mountainResponseList);

            given(bannerService.getInterestingMountains(interest)).willReturn(bannerResponse);

            // when & then
            mockMvc.perform(get("/api/main/banner")
                            .param("interest", interest.name())
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.interest").value(interest.name()))
                    .andExpect(jsonPath("$.data.mountains[0].name").value("새로운 산"))
                    .andExpect(jsonPath("$.data.mountains[0].image_url").value("image_url"));

            verify(bannerService).getInterestingMountains(interest);
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
            BannerResponse bannerResponse = new BannerResponse(Interest.POPULAR, mountainResponseList);

            given(bannerService.getInterestingMountains(any(Interest.class))).willReturn(bannerResponse);

            // when & then
            mockMvc.perform(get("/api/main/banner")
                            .param("interest", "POPULAR")
                            .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.interest").value("POPULAR"))
                    .andExpect(jsonPath("$.data.mountains[0].name").value("부모산"))
                    .andExpect(jsonPath("$.data.mountains[0].viewCount").value(300L))
                    .andExpect(jsonPath("$.data.mountains[1].name").value("한라산"))
                    .andExpect(jsonPath("$.data.mountains[1].viewCount").value(200L))
                    .andExpect(jsonPath("$.data.mountains[2].name").value("지리산"))
                    .andExpect(jsonPath("$.data.mountains[2].viewCount").value(150L));

            verify(bannerService).getInterestingMountains(Interest.POPULAR);
        }


        public static Stream<Arguments> provideInterest() {
            return Stream.of(
                    Arguments.of(Interest.LOW),
                    Arguments.of(Interest.ACTIVITY),
                    Arguments.of(Interest.HIGH)
            );
        }
    }

    @Nested
    class 배너_클릭 {
        @Test
        @WithMockUser(username = "testuser", roles = {"USER"})
        void 배너_클릭_성공() throws Exception {
            // given
            String mountainName = "부모산";
            MountainDTO mountainDTO = new MountainDTO("부모산", "충북 청주시 흥덕구 비하동 산 10-3", "127.41026890180636", "36.63420831918445");
            List<MountainDTO> mountainResponseList = new ArrayList<>();
            mountainResponseList.add(mountainDTO);
            MountainSearchResponse searchResponse = new MountainSearchResponse(mountainResponseList);

            given(mountainService.searchMountains(anyString())).willReturn(searchResponse);

            // when & then
            mockMvc.perform(post("/api/main/banner/click")
                            .param("mountainName", mountainName)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("success"));

            verify(bannerService).incrementViewCount(mountainName);
            verify(mountainService).searchMountains(mountainName);
        }
    }
}
