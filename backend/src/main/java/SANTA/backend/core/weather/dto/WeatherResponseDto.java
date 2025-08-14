package SANTA.backend.core.weather.dto;

import SANTA.backend.global.utils.api.domain.WeatherCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponseDto {
    private String time;
    private Double temperature;
    private WeatherCode weatherCode;
    private GloomyLevel gloomyLevel;
}
