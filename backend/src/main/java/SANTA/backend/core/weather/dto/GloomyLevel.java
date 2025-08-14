package SANTA.backend.core.weather.dto;

public enum GloomyLevel {
    NO_CLOUD(1),    // 맑음
    MANY_CLOUD(3),  // 구름많음
    GLOOMY(4);      // 흐림

    private final int code;

    GloomyLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // 코드값으로 enum 찾기
    public static GloomyLevel fromCode(int code) {
        for (GloomyLevel level : values()) {
            if (level.code == code) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
