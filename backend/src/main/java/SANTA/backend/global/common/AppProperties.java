package SANTA.backend.global.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final KoreaTourOrganization koreaTourOrganization = new KoreaTourOrganization();
    private final JWT jwt = new JWT();
    private final Forest forest = new Forest();
    private final Kakao kakao = new Kakao();

    public KoreaTourOrganization getKoreaTourOrganization() {
        return this.koreaTourOrganization;
    }

    public JWT getJwt() {
        return this.jwt;
    }

    public Forest getForest() {
        return this.forest;
    }

    public Kakao getKakao() {
        return this.kakao;
    }

    public static class KoreaTourOrganization {
        private String encodingKey;
        private String decodingKey;
        private String url;
        private String mobileAppName;

        public String getEncodingKey() {
            return encodingKey;
        }

        public void setEncodingKey(String encodingKey) {
            this.encodingKey = encodingKey;
        }

        public String getDecodingKey() {
            return decodingKey;
        }

        public void setDecodingKey(String decodingKey) {
            this.decodingKey = decodingKey;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setMobileAppName(String mobileAppName) {
            this.mobileAppName = mobileAppName;
        }

        public String getMobileAppName() {
            return mobileAppName;
        }

        public String getRoutingKey() {
            return encodingKey;
        }
    }

    public static class JWT {
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public static class Forest {
        private String url;
        private String key;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

        public String getRoutingKey() {
            return this.key;
        }
    }

    public static class Kakao {
        private String url;
        private String key;

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
