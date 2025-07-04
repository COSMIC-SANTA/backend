package SANTA.backend.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="app")
@RequiredArgsConstructor
public class AppProperties {
    private final Auth auth;
    private final OAuth oAuth;

    public class Auth{
       private String tokenSecret;
       private String tokenValidationTime;
    }

    public class OAuth{
        public String redirectUrl;
    }

}
