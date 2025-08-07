package SANTA.backend.core.mountain.dto;

import java.util.List;

public record OptimalRouteResponse(
        OriginInfo origin,
        DestinationInfo destination,
        List<WaypointInfo> waypoints,
        double distance, // 전체 거리
        double duration, // 전체 소요 시간
        double taxi, // 택시 요금
        List<SectionInfo> sections
) {
    public record OriginInfo(String name, double x, double y) {}
    public record DestinationInfo(String name, double x, double y) {}
    public record WaypointInfo(String name, double x, double y) {}
    public record SectionInfo(double distance, double duration) {}
}
