package SANTA.backend.weather.api;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherApiTest extends ServiceContext {

    @Test
    public void 날씨를_가져올_수_있다() {
        Double mapX = 128.9460030322;
        Double mapY = 35.1045320626;
        Position position = new Position(mapX, mapY);
        List<WeatherResponseDto> weather = apiRequester.getWeather(position);
        for (WeatherResponseDto weatherResponseDto : weather) {
            System.out.println(weatherResponseDto.getTime() + ": " + weatherResponseDto.getTemperature() + ", "+ weatherResponseDto.getWeatherCode() +", "+ weatherResponseDto.getGloomyLevel());
        }


        assertThat(weather).extracting(WeatherResponseDto::getTemperature).isNotNull();
        assertThat(weather).extracting(WeatherResponseDto::getWeatherCode).isNotNull();
    }

}