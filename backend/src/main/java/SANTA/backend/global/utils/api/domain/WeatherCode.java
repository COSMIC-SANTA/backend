package SANTA.backend.global.utils.api.domain;

public enum WeatherCode {
    NO_RAIN(0),
    RAIN(1),
    RAIN_SNOW(2),
    X(3),
    SNOW(4),
    RAINDROPS(5),
    RAIN_SNOW_DROPS(6),
    SNOW_DROPS(7);

    private final int code;

    WeatherCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static WeatherCode fromCode(int code) {
        for (WeatherCode value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown PTY code: " + code);
    }
}
