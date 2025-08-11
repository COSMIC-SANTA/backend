package SANTA.backend.core.mountain.application;

import SANTA.backend.core.cafe.domain.CafeRepository;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.restaurant.domain.RestaurantRepository;
import SANTA.backend.core.spot.domain.SpotRepository;
import SANTA.backend.core.stay.domain.StayRepository;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import SANTA.backend.global.utils.api.APIRequester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class MountainService {

    private final WebClient webClient;

    @Value("${forest.api.key}")
    private String forestApiKey;

    private final MountainRepository mountainRepository;
    private final RestaurantRepository restaurantRepository;
    private final StayRepository stayRepository;
    private final CafeRepository cafeRepository;
    private final SpotRepository spotRepository;
    private final APIRequester apiRequester;

    public MountainService(@Qualifier("forestApiClient") WebClient webClient, MountainRepository mountainRepository, RestaurantRepository restaurantRepository,
                           StayRepository stayRepository, CafeRepository cafeRepository, SpotRepository spotRepository, APIRequester apiRequester) {
        this.webClient = webClient;
        this.mountainRepository = mountainRepository;
        this.restaurantRepository = restaurantRepository;
        this.stayRepository = stayRepository;
        this.cafeRepository = cafeRepository;
        this.spotRepository = spotRepository;
        this.apiRequester = apiRequester;
    }

    @Transactional(readOnly = true)
    public MountainSearchResponse searchMountains(String mountainName) {
        Mono<MountainSearchResponse> MountainResponseMono = apiRequester.searchMountains(mountainName);
        return MountainResponseMono.block();
    }

    @Transactional
    public void saveMountain(Mountain mountain) {
        mountainRepository.saveMountain(mountain);
    }

    @Transactional
    public List<Mountain> findByName(String name){
        return mountainRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public MountainNearByResponse searchNearByPlacesById(Long mountainId, Long pageNo) {
        String location = mountainRepository.findById(mountainId)
                .map(Mountain::getLocation)
                .orElseThrow(() -> new CustomException(ErrorCode.MOUNTAIN_NOT_FOUND));

        return apiRequester.searchNearByPlacesByLocation(location, pageNo)
                .block();
    }
}
