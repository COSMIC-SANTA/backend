package SANTA.backend.plan.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.mountain.dto.*;
import SANTA.backend.core.plan.dto.PlanDto;
import SANTA.backend.core.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PlanServiceTest extends ServiceContext {

    @Test @Transactional
    public void 계획을_저장할_수_있다() {
        //given
        User user = User.registerUser("username", "password", "nickname");
        User joinedUser = userRepository.join(user);


        LocalDateTime targetDate = LocalDateTime.now();

        Double mapX = 127.10764191124568;
        Double mapY = 37.402464820205246;

        MountainRequestDTO mountainRequestDTO = new MountainRequestDTO("산 이름", "산 주소", "imageUrl", mapX, mapY);

        String name = "name";
        String location = "location";
        String imageUrl = "imageUrl";

        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();
        RestaurantDTO restaurantDTO = new RestaurantDTO(name, location, imageUrl, mapX, mapY);
        restaurantDTOs.add(restaurantDTO);

        List<StayDTO> stayDTOS = new ArrayList<>();
        StayDTO stayDTO = new StayDTO(name, location, imageUrl, mapX, mapY);
        stayDTOS.add(stayDTO);

        List<CafeDTO> cafeDTOS = new ArrayList<>();
        CafeDTO cafeDTO = new CafeDTO(name, location, imageUrl, mapX, mapY);
        cafeDTOS.add(cafeDTO);

        List<TouristSpotDTO> spotDTOS = new ArrayList<>();
        TouristSpotDTO spotDTO = new TouristSpotDTO(name, location, imageUrl, mapX, mapY);
        spotDTOS.add(spotDTO);

        //when
        Long planId = planService.savePlan(joinedUser.getUserId(), targetDate, mountainRequestDTO, restaurantDTOs, stayDTOS, cafeDTOS, spotDTOS);

        //then
        System.out.println("planId = " + planId);
        Assertions.assertThat(planId).isNotNull();
    }

    @Test @Transactional
    public void 계획을_완료할_수_있다(){
        //given
        User user = User.registerUser("username", "password", "nickname");
        User joinedUser = userRepository.join(user);


        LocalDateTime targetDate = LocalDateTime.now();

        Double mapX = 127.10764191124568;
        Double mapY = 37.402464820205246;

        MountainRequestDTO mountainRequestDTO = new MountainRequestDTO("산 이름", "산 주소", "imageUrl", mapX, mapY);

        String name = "name";
        String location = "location";
        String imageUrl = "imageUrl";

        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();
        RestaurantDTO restaurantDTO = new RestaurantDTO(name, location, imageUrl, mapX, mapY);
        restaurantDTOs.add(restaurantDTO);

        List<StayDTO> stayDTOS = new ArrayList<>();
        StayDTO stayDTO = new StayDTO(name, location, imageUrl, mapX, mapY);
        stayDTOS.add(stayDTO);

        List<CafeDTO> cafeDTOS = new ArrayList<>();
        CafeDTO cafeDTO = new CafeDTO(name, location, imageUrl, mapX, mapY);
        cafeDTOS.add(cafeDTO);

        List<TouristSpotDTO> spotDTOS = new ArrayList<>();
        TouristSpotDTO spotDTO = new TouristSpotDTO(name, location, imageUrl, mapX, mapY);
        spotDTOS.add(spotDTO);

        Long planId = planService.savePlan(joinedUser.getUserId(), targetDate, mountainRequestDTO, restaurantDTOs, stayDTOS, cafeDTOS, spotDTOS);

        //when
        Long distance = 100L;
        Long completedPlanId = planService.completePlan(planId, distance);

        //then
        Assertions.assertThat(completedPlanId).isNotNull();
    }

    @Test @Transactional
    public void 계획을_조회할_수_있다(){
        //given
        User user = User.registerUser("username", "password", "nickname");
        User joinedUser = userRepository.join(user);


        LocalDateTime targetDate = LocalDateTime.now();

        Double mapX = 127.10764191124568;
        Double mapY = 37.402464820205246;

        MountainRequestDTO mountainRequestDTO = new MountainRequestDTO("산 이름", "산 주소", "imageUrl", mapX, mapY);

        String name = "name";
        String location = "location";
        String imageUrl = "imageUrl";

        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();
        RestaurantDTO restaurantDTO = new RestaurantDTO(name, location, imageUrl, mapX, mapY);
        restaurantDTOs.add(restaurantDTO);

        List<StayDTO> stayDTOS = new ArrayList<>();
        StayDTO stayDTO = new StayDTO(name, location, imageUrl, mapX, mapY);
        stayDTOS.add(stayDTO);

        List<CafeDTO> cafeDTOS = new ArrayList<>();
        CafeDTO cafeDTO = new CafeDTO(name, location, imageUrl, mapX, mapY);
        cafeDTOS.add(cafeDTO);

        List<TouristSpotDTO> spotDTOS = new ArrayList<>();
        TouristSpotDTO spotDTO = new TouristSpotDTO(name, location, imageUrl, mapX, mapY);
        spotDTOS.add(spotDTO);

        Long planId1 = planService.savePlan(joinedUser.getUserId(), targetDate, mountainRequestDTO, restaurantDTOs, stayDTOS, cafeDTOS, spotDTOS);


        //given
        LocalDateTime targetDate2 = LocalDateTime.now();

        Double mapX2 = 127.10764191124568;
        Double mapY2 = 37.402464820205246;

        MountainRequestDTO mountainRequestDTO2 = new MountainRequestDTO("산 이름", "산 주소", "imageUrl", mapX2, mapY2);

        String name2 = "name";
        String location2 = "location";
        String imageUrl2 = "imageUrl";

        List<RestaurantDTO> restaurantDTOs2 = new ArrayList<>();
        RestaurantDTO restaurantDTO2 = new RestaurantDTO(name2, location2, imageUrl2, mapX2, mapY2);
        restaurantDTOs2.add(restaurantDTO2);

        List<StayDTO> stayDTOS2 = new ArrayList<>();
        StayDTO stayDTO2 = new StayDTO(name, location, imageUrl, mapX, mapY);
        stayDTOS2.add(stayDTO2);

        List<CafeDTO> cafeDTOS2 = new ArrayList<>();
        CafeDTO cafeDTO2 = new CafeDTO(name, location, imageUrl, mapX, mapY);
        cafeDTOS2.add(cafeDTO2);

        List<TouristSpotDTO> spotDTOS2 = new ArrayList<>();
        TouristSpotDTO spotDTO2 = new TouristSpotDTO(name, location, imageUrl, mapX, mapY);
        spotDTOS.add(spotDTO2);

        Long planId2 = planService.savePlan(joinedUser.getUserId(), targetDate2, mountainRequestDTO2, restaurantDTOs2, stayDTOS2, cafeDTOS2, spotDTOS2);

        //when
        Long distance = 100L;
        Long completedPlanId = planService.completePlan(planId2, distance);

        //then
        List<PlanDto> completedPlans = planService.getCompletedPlans(joinedUser.getUserId());
        for (PlanDto completedPlan : completedPlans) {
            System.out.println("completedPlan = " + completedPlan.toString());
        }

        List<PlanDto> notCompletedPlans = planService.getNotCompletedPlans(joinedUser.getUserId());
        for (PlanDto notCompletedPlan : notCompletedPlans) {
            System.out.println("notCompletedPlan = " + notCompletedPlan);
        }

        Assertions.assertThat(completedPlans).isNotNull();
        Assertions.assertThat(notCompletedPlans).isNotNull();
    }
}
