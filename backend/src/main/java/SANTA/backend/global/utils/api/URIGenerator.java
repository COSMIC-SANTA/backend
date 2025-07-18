package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import jakarta.annotation.Nullable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


public abstract class URIGenerator {

    private static final String JSON = "json";

    protected URI URIGenerator(String url, String operation, String key, String mobileOs, String mobileApp, @Nullable Long numOfRows, @Nullable Long pageNo,
                               @Nullable Arrange arrange, @Nullable ContentTypeId contentTypeId, @Nullable AreaCode areaCode, @Nullable Long sigunguCode,
                               @Nullable Long radius, @Nullable Double mapX, @Nullable Double mapY, @Nullable String keyword,
                               @Nullable String eventStartDate, @Nullable String eventEndDate, @Nullable Long contentId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .pathSegment(operation)
                .queryParam("serviceKey", key)
                .queryParam("MobileOS", mobileOs)
                .queryParam("MobileApp", mobileApp)
                .queryParam("_type", JSON);

        if (numOfRows != null)
            builder.queryParam("numOfRows", numOfRows);

        if (pageNo != null)
            builder.queryParam("pageNo", pageNo);

        if (arrange != null)
            builder.queryParam("arrange", arrange.name());

        if (contentTypeId != null)
            builder.queryParam("contentTypeId", contentTypeId.getContentTypeId());

        if (areaCode != null)
            builder.queryParam("areaCode", areaCode.getCode());

        if (sigunguCode != null)
            builder.queryParam("sigunguCode", sigunguCode);
        if (radius != null)
            builder.queryParam("radius", radius);

        if (mapX != null)
            builder.queryParam("mapX", mapX);

        if (mapY != null)
            builder.queryParam("mapY", mapY);

        if (keyword != null)
            builder.queryParam("keyword", keyword);

        if (eventStartDate != null)
            builder.queryParam("eventStartDate", eventStartDate);

        if (eventEndDate != null)
            builder.queryParam("eventEndDate", eventEndDate);

        if (contentId != null)
            builder.queryParam("contentId", contentId);

        return builder.build(true).toUri();
    }

}
