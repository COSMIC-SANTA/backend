package SANTA.backend.core.mountain.application;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.dto.MountainFacilityRequest;
import SANTA.backend.core.mountain.dto.MountainFacilityResponse;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.mountain.dto.OptimalRouteRequest;
import SANTA.backend.core.mountain.dto.OptimalRouteResponse;
import SANTA.backend.global.utils.api.APIRequester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class MountainService {

    private final MountainRepository mountainRepository;
    private final APIRequester apiRequester;

    public MountainService(MountainRepository mountainRepository, APIRequester apiRequester) {
        this.mountainRepository = mountainRepository;
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
