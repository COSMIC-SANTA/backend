package SANTA.backend.core.weather.api;

import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.weather.dto.WeatherResponseDto;
import SANTA.backend.global.common.ResponseHandler;
import SANTA.backend.global.utils.api.APIRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/weather")
public class WeatherApi {

    private final APIRequester apiRequester;

    @PostMapping
    public ResponseEntity<ResponseHandler<WeatherResponseDto>> getWeather(@RequestBody Position position) {
        WeatherResponseDto weather = apiRequester.getWeather(position);
        return ResponseEntity.ok().body(ResponseHandler.success(weather));
    }
}
