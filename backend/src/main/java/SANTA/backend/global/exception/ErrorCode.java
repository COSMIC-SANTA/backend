package SANTA.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    TEMPORARY_ERROR(500,"C_000","임시 에러코드입니다."),
    INTERNAL_SERVER_ERROR(500,"C_001","서버가 터졌습니다."),

    FOREST_API_CONNECTION_ERROR(502, "M_001", "산림청 API 연결에 실패했습니다."),
    FOREST_API_TIMEOUT(408, "M_002", "산림청 API 요청 시간이 초과되었습니다."),
    FOREST_API_INVALID_RESPONSE(502, "M_003", "산림청 API 응답이 올바르지 않습니다."),
    FOREST_API_EMPTY_RESPONSE(502, "M_004", "산림청 API 응답이 비어있습니다."),
    FOREST_API_SERVICE_ERROR(503, "M_005", "산림청 API 서비스에 문제가 발생했습니다."),
    FOREST_API_QUOTA_EXCEEDED(429, "M_006", "산림청 API 요청 한도를 초과했습니다."),
    FOREST_API_UNAUTHORIZED(401, "M_007", "산림청 API 인증에 실패했습니다."),

    FOREST_API_APPLICATION_ERROR(500, "M_010", "산림청 API 애플리케이션 오류가 발생했습니다."),    // 01
    FOREST_API_DATABASE_ERROR(500, "M_011", "산림청 API 데이터베이스 오류가 발생했습니다."),      // 02
    FOREST_API_NO_DATA(404, "M_012", "산림청 API에서 데이터를 찾을 수 없습니다."),           // 03
    FOREST_API_HTTP_ERROR(500, "M_013", "산림청 API HTTP 오류가 발생했습니다."),          // 04
    FOREST_API_INVALID_PARAMETER(400, "M_014", "산림청 API 요청 파라미터가 잘못되었습니다."),   // 10
    FOREST_API_MISSING_PARAMETER(400, "M_015", "산림청 API 필수 파라미터가 누락되었습니다."),   // 11
    FOREST_API_OPENAPI_SERVICE_ERROR(503, "M_016", "산림청 API 서비스가 없거나 폐기되었습니다."), // 12
    FOREST_API_ACCESS_DENIED(403, "M_017", "산림청 API 서비스 접근이 거부되었습니다."),       // 20
    FOREST_API_REQUEST_LIMIT_EXCEEDED(429, "M_018", "산림청 API 요청제한횟수를 초과했습니다."), // 22
    FOREST_API_UNREGISTERED_KEY(401, "M_019", "등록되지 않은 서비스키입니다."),            // 30
    FOREST_API_EXPIRED_KEY(401, "M_020", "기한만료된 서비스키입니다."),                  // 31
    FOREST_API_UNKNOWN_ERROR(500, "M_021", "산림청 API에서 알 수 없는 오류가 발생했습니다."), // 99

    MOUNTAIN_NOT_FOUND(404, "M_022", "검색한 산 정보를 찾을 수 없습니다.");


    private final int state;
    private final String code;
    private final String message;

    public static ErrorCode fromForestApiCode(String apiCode) {
        return switch (apiCode) {
            case "01" -> FOREST_API_APPLICATION_ERROR;
            case "02" -> FOREST_API_DATABASE_ERROR;
            case "03" -> FOREST_API_NO_DATA;
            case "04" -> FOREST_API_HTTP_ERROR;
            case "10" -> FOREST_API_INVALID_PARAMETER;
            case "11" -> FOREST_API_MISSING_PARAMETER;
            case "12" -> FOREST_API_OPENAPI_SERVICE_ERROR;
            case "20" -> FOREST_API_ACCESS_DENIED;
            case "22" -> FOREST_API_REQUEST_LIMIT_EXCEEDED;
            case "30" -> FOREST_API_UNREGISTERED_KEY;
            case "31" -> FOREST_API_EXPIRED_KEY;
            case "99" -> FOREST_API_UNKNOWN_ERROR;
            default -> FOREST_API_SERVICE_ERROR;
        };
    }
}
