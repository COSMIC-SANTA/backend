package SANTA.backend.core.mountain.application;

import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.dto.MountainFacilityRequest;
import SANTA.backend.core.mountain.dto.MountainFacilityResponse;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.mountain.dto.OptimalRouteRequest;
import SANTA.backend.core.mountain.dto.OptimalRouteResponse;
import SANTA.backend.core.mountain.entity.MountainEntity;
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
import java.util.Optional;

@Service
@Slf4j
public class MountainService {

    private final WebClient webClient;

    @Value("${forest.api.key}")
    private String forestApiKey;

    private final MountainRepository mountainRepository;
    private final BannerService bannerService;
    private final APIRequester apiRequester;

    public MountainService(@Qualifier("forestApiClient") WebClient webClient, MountainRepository mountainRepository, APIRequester apiRequester, BannerService bannerService) {
        this.webClient = webClient;
        this.mountainRepository = mountainRepository;
        this.bannerService = bannerService;
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

    @Transactional(readOnly = true)
    public List<Mountain> findByName(String name) {
        return mountainRepository.findByName(name).stream().map(Mountain::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public MountainNearByResponse searchNearByPlacesByLocation(String location, Long pageNo) {
        return apiRequester.searchNearByPlacesByLocation(location, pageNo)
                .block();
    }

    @Transactional(readOnly = true)
    public MountainFacilityResponse searchFacilities(MountainFacilityRequest request) {
        Mono<MountainFacilityResponse> FacilityResponseMono = apiRequester.searchFacility(request);
        return FacilityResponseMono.block();
    }

    @Transactional
    public OptimalRouteResponse searchOptimalRoute(OptimalRouteRequest request) {
        Mono<OptimalRouteResponse> routeResponseMono = apiRequester.searchOptimalRoute(request);
        return routeResponseMono.block();
    }

}
