package SANTA.backend.global.utils.api.domain;

public enum WeatherCode {
    CLEAN,
    RAIN,
    RAIN_SNOW,
    X,
    SNOW,
    RAINDROPS,
    RAIN_SNOW_DROPS,
    SNOW_DROPS;

    public static WeatherCode fromCode(int code) {
        return switch (code) {
            case 0 -> CLEAN;
            case 1 -> RAIN;
            case 2 -> RAIN_SNOW;
            case 3 -> X;
            case 4 -> SNOW;
            case 5 -> RAINDROPS;
            case 6 -> RAIN_SNOW_DROPS;
            case 7 -> SNOW_DROPS;
            default -> throw new IllegalArgumentException("Unknown PTY code: " + code);
        };
    }
}
