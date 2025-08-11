package SANTA.backend.global.utils.api;

import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.weather.dto.Grid;
import SANTA.backend.core.weather.dto.WeatherResponseDto;
import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.utils.api.domain.WeatherCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.nimbusds.openid.connect.sdk.assurance.claims.ISO3166_1Alpha2CountryCode.RE;

@Component
@RequiredArgsConstructor
public class WeatherServiceRequester extends URIGenerator{

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

    public Mono<WeatherResponseDto> getWeather(Position position){
        String url = appProperties.getWeather().getUrl();
        String key = appProperties.getWeather().getKey();
        Grid grid = toGrid(position.getMapY(), position.getMapX());
        URI uri = weatherURIGenerator(url,key,grid);
        Mono<JsonNode> items = webClientService.request(uri);;
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

    private WeatherResponseDto extractT1HAndRN1(JsonNode items) {
        WeatherResponseDto resultNode = new WeatherResponseDto();

        for (JsonNode item : items) {
            String category = item.path("category").asText();
            String value = item.path("obsrValue").asText();

            if ("T1H".equals(category)) {
                resultNode.setTemperature(Double.parseDouble(value));
            } else if ("PTY".equals(category)) {
                resultNode.setWeatherCode(WeatherCode.fromCode(Integer.parseInt(value)));
            }
        }
        return resultNode;
    }

}
