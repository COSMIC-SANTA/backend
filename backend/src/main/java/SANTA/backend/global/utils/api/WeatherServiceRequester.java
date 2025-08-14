package SANTA.backend.global.utils.api;

import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.weather.dto.GloomyLevel;
import SANTA.backend.core.weather.dto.Grid;
import SANTA.backend.core.weather.dto.WeatherResponseDto;
import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.utils.api.domain.WeatherCode;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WeatherServiceRequester extends URIGenerator {

    private final AppProperties appProperties;
    private final WebClientService webClientService;
    private static final double RE = 6371.00877; // 지구 반경(km)
    private static final double GRID = 5.0;      // 격자 간격(km)
    private static final double SLAT1 = 30.0;    // 표준위도 1(도)
    private static final double SLAT2 = 60.0;    // 표준위도 2(도)
    private static final double OLON = 126.0;    // 기준점 경도(도)
    private static final double OLAT = 38.0;     // 기준점 위도(도)
    private static final double XO = 210 / GRID; // 기준점 X좌표
    private static final double YO = 675 / GRID; // 기준점 Y좌표

    public Mono<List<WeatherResponseDto>> getWeather(Position position) {
        String url = appProperties.getWeather().getUrl();
        String forecastUrl = appProperties.getWeather().getForecastUrl();
        String key = appProperties.getWeather().getKey();
        Grid grid = toGrid(position.getMapY(), position.getMapX());
        URI uri = weatherURIGenerator(forecastUrl, key, grid);
        Mono<JsonNode> items = webClientService.request(uri);

        return items.map(this::extractT1HAndRN1);
    }

    private Grid toGrid(double lat, double lon) {
        double DEGRAD = Math.PI / 180.0;
        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int nx = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        int ny = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return new Grid(nx, ny);
    }

    private List<WeatherResponseDto> extractT1HAndRN1(JsonNode items) {

        Map<String, List<JsonNode>> grouped = new ArrayList<JsonNode>() {{
            items.forEach(this::add);
        }}.stream()
                .collect(Collectors.groupingBy(node -> node.get("fcstTime").asText()));

        List<WeatherResponseDto> result = new ArrayList<>();

        for (Map.Entry<String, List<JsonNode>> entry : grouped.entrySet()) {
            String fcstTime = entry.getKey();
            List<JsonNode> values = entry.getValue();

            WeatherResponseDto dto = new WeatherResponseDto();
            dto.setTime(formatTime(fcstTime));

            for (JsonNode v : values) {
                String category = v.get("category").asText();
                String fcstValue = v.get("fcstValue").asText();

                if ("T1H".equals(category)) { // 기온
                    dto.setTemperature(Double.valueOf(fcstValue));
                } else if ("PTY".equals(category)) {
                    dto.setWeatherCode(WeatherCode.fromCode(Integer.parseInt(fcstValue)));
                } else if ("SKY".equals(category)){
                    dto.setGloomyLevel(GloomyLevel.fromCode(Integer.parseInt(fcstValue)));
                }
            }
            result.add(dto);
        }

        // 시간순 정렬
        result.sort(Comparator.comparing(WeatherResponseDto::getTime));
        return result;
    }

    private static String formatTime (String fcstTime){
        // "1000" → "10:00"
        return fcstTime.substring(0, 2) + ":" + fcstTime.substring(2);
    }

}
