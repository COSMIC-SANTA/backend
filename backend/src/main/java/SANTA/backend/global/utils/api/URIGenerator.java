package SANTA.backend.global.utils.api;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.core.weather.dto.Grid;
import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import jakarta.annotation.Nullable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class URIGenerator {

    private static final String JSON = "json";

    protected URI KoreanTourInfoServiceURIGenerator(String url, String operation, String key, String mobileOs, String mobileApp, @Nullable Long numOfRows, @Nullable Long pageNo,
                                                    @Nullable Arrange arrange, @Nullable ContentTypeId contentTypeId, @Nullable AreaCode areaCode, @Nullable Long sigunguCode,
                                                    @Nullable Long radius, @Nullable Double mapX, @Nullable Double mapY, @Nullable String keyword,
                                                    @Nullable String eventStartDate, @Nullable String eventEndDate, @Nullable Long contentId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .pathSegment(operation)
                .queryParam("serviceKey", key)
                .queryParam("MobileOS", mobileOs)
                .queryParam("MobileApp", mobileApp)
                .queryParam("_type", JSON);

        if (numOfRows != null)
            builder.queryParam("numOfRows", numOfRows);

        if (pageNo != null)
            builder.queryParam("pageNo", pageNo);

        if (arrange != null)
            builder.queryParam("arrange", arrange.name());

        if (contentTypeId != null)
            builder.queryParam("contentTypeId", contentTypeId.getContentTypeId());

        if (areaCode != null)
            builder.queryParam("areaCode", areaCode.getCode());

        if (sigunguCode != null)
            builder.queryParam("sigunguCode", sigunguCode);
        if (radius != null)
            builder.queryParam("radius", radius);

        if (mapX != null)
            builder.queryParam("mapX", mapX);

        if (mapY != null)
            builder.queryParam("mapY", mapY);

        if (keyword != null)
            builder.queryParam("keyword", keyword);

        if (eventStartDate != null)
            builder.queryParam("eventStartDate", eventStartDate);

        if (eventEndDate != null)
            builder.queryParam("eventEndDate", eventEndDate);

        if (contentId != null)
            builder.queryParam("contentId", contentId);

        return builder.build(true).toUri();
    }

    protected URI mountainINfoServiceURIGenerator(String url, String key, @Nullable String locationName) {
        String encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8);
        Long numOfRows = 4750L; //한번에 모든 산들 가져와 버리기~

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("serviceKey", encodedKey)
                .queryParam("_type", JSON)
                .queryParam("numOfRows", numOfRows);

        if (locationName != null) {
            String encodedLocation = URLEncoder.encode(locationName, StandardCharsets.UTF_8);
            builder.queryParam("searchArNm", encodedLocation);
        }

        return builder.build(true).toUri();
    }

    protected URI mountainINfoServiceURIGenerator(String url, String key, Long mountainCode) {
        String encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("serviceKey", encodedKey)
                .queryParam("_type", JSON)
                .queryParam("searchWrd", mountainCode);

        return builder.build(true).toUri();
    }

    protected URI kakaoSearchRouteURIGenerator(String url, Position position, @Nullable List<Cafe> cafes, @Nullable List<Restaurant> restaurants, @Nullable List<Stay> stays, @Nullable List<Spot> spots,
                                               BasePlace destinationBasePlace) {
        String origin = position.getMapX() + "," + position.getMapY();
        String wayPoints = getWayPoints(stays, cafes, restaurants, spots);
        String destination = destinationBasePlace.getPosition().getMapX() + "," + destinationBasePlace.getPosition().getMapY() + ",name=" + destinationBasePlace.getName();

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("waypoints", wayPoints)
                .queryParam("priority", "RECOMMEND")
                .queryParam("alternatives", "true")
                .queryParam("road_details", "true")
                .queryParam("summary", "true");

        return componentsBuilder.build().toUri();
    }

    protected URI weatherURIGenerator(String url, String key, Grid grid) {
        int nx = grid.nx();
        int ny = grid.ny();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = (now.getMinute() < 40)
                ? now.minusHours(1).withMinute(0).withSecond(0).withNano(0)
                : now.withMinute(0).withSecond(0).withNano(0);

        String baseDate = base.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = base.format(DateTimeFormatter.ofPattern("HHmm"));


        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("serviceKey", key)
                .queryParam("dataType", "JSON")
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny);

        return componentsBuilder.build().toUri();
    }

    private static String getWayPoints(List<Stay> stays, List<Cafe> cafes, List<Restaurant> restaurants, List<Spot> spots) {

        List<String> waypoints = new ArrayList<>();

        if (cafes != null && !cafes.isEmpty()) {
            waypoints.addAll(
                    cafes.stream()
                            .map(cafe -> cafe.getPosition().getMapX() + "," +
                                    cafe.getPosition().getMapY() + ",name=" +
                                    cafe.getName())
                            .collect(Collectors.toList())
            );
        }

        if (restaurants != null && !restaurants.isEmpty()) {
            waypoints.addAll(
                    restaurants.stream()
                            .map(restaurant -> restaurant.getPosition().getMapX() + "," +
                                    restaurant.getPosition().getMapY() + ",name=" +
                                    restaurant.getName())
                            .collect(Collectors.toList())
            );
        }

        if (spots != null && !spots.isEmpty()) {
            waypoints.addAll(
                    spots.stream()
                            .map(spot -> spot.getPosition().getMapX() + "," +
                                    spot.getPosition().getMapY() + ",name=" +
                                    spot.getName())
                            .collect(Collectors.toList())
            );
        }

        if (stays != null && !stays.isEmpty()) {
            waypoints.addAll(
                    stays.stream()
                            .map(stay -> stay.getPosition().getMapX() + "," +
                                    stay.getPosition().getMapY() + ",name=" +
                                    stay.getName())
                            .collect(Collectors.toList())
            );
        }
        return String.join("|", waypoints);
    }


}
