package SANTA.backend.course.api;

import SANTA.backend.context.ControllerTest;
import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.dto.OptimalRouteRequest;
import SANTA.backend.core.mountain.dto.OptimalRouteResponse;
import SANTA.backend.core.stay.domain.Stay;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OptimalRouteApiTest extends ControllerTest {

    @Nested
    class searchOptimalRoute_메서드는 {

        private ObjectMapper objectMapper = new ObjectMapper();

        private String toJson(Object obj) {
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException("JSON 직렬화 실패", e);
            }
        }

        @Test
        @WithMockUser(username = "testuser", roles = {"USER"})
        void 산_이동_최적_경로를_조회할_수_있다() throws Exception{
            // given
            Position origin = new Position(127.10764191124568, 37.402464820205246);
            Position destinationPosition = new Position(127.11056336672839, 37.39419693653072);
            BasePlace destination = Stay.builder().position(destinationPosition).build();

            List<Cafe> cafes = List.of(
                    Cafe.builder()
                            .name("cafe")
                            .location("cafe location")
                            .position(new Position(127.17354989857544, 37.36629687436494))
                            .build()
            );

            OptimalRouteRequest request = new OptimalRouteRequest(
                    origin,
                    destination,
                    cafes,
                    null,
                    null,
                    null
            );

            OptimalRouteResponse mockResponse = new OptimalRouteResponse(
                    new OptimalRouteResponse.OriginInfo("", 127.10764191124568, 37.402464820205246),
                    new OptimalRouteResponse.DestinationInfo("", 127.11056336672839, 37.39419693653072),
                    List.of(new OptimalRouteResponse.WaypointInfo("", 127.17354989857544, 37.36629687436494)),
                    19322,  // distance
                    2892,   // duration
                    23300,  // taxi
                    List.of(
                            new OptimalRouteResponse.SectionInfo(9839, 1475),
                            new OptimalRouteResponse.SectionInfo(9483, 1417)
                    )
            );

            given(mountainService.searchOptimalRoute(any(OptimalRouteRequest.class))).willReturn(mockResponse);

            // when & then
            mockMvc.perform(post("/api/mountains/optimalRoute")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(request)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    // 출발지 검증
                    .andExpect(jsonPath("$.origin.x").value(127.10764191124568))
                    .andExpect(jsonPath("$.origin.y").value(37.402464820205246))
                    // 목적지 검증
                    .andExpect(jsonPath("$.destination.x").value(127.11056336672839))
                    .andExpect(jsonPath("$.destination.y").value(37.39419693653072))
                    // 경유지 검증
                    .andExpect(jsonPath("$.waypoints").isArray())
                    .andExpect(jsonPath("$.waypoints.length()").value(1))
                    .andExpect(jsonPath("$.waypoints[0].x").value(127.17354989857544))
                    .andExpect(jsonPath("$.waypoints[0].y").value(37.36629687436494))
                    // 거리/시간 검증
                    .andExpect(jsonPath("$.distance").value(19322))
                    .andExpect(jsonPath("$.duration").value(2892))
                    .andExpect(jsonPath("$.taxi").value(23300))
                    // 구간 정보 검증
                    .andExpect(jsonPath("$.sections").isArray())
                    .andExpect(jsonPath("$.sections.length()").value(2))
                    .andExpect(jsonPath("$.sections[0].distance").value(9839))
                    .andExpect(jsonPath("$.sections[0].duration").value(1475))
                    .andExpect(jsonPath("$.sections[1].distance").value(9483))
                    .andExpect(jsonPath("$.sections[1].duration").value(1417));
        }
    }
}

