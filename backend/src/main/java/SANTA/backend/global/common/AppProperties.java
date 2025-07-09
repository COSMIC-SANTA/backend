package SANTA.backend.global.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final KoreaTourOrganization koreaTourOrganization = new KoreaTourOrganization();

    public KoreaTourOrganization getKoreaTourOrganization() {
        return this.koreaTourOrganization;
    }

    private static class KoreaTourOrganization {
        private String koreaTourInfoServiceEncodingKey;
        private String koreaTourInfoServiceDecodingKey;

        public String getKoreaTourInfoServiceEncodingKey() {
            return koreaTourInfoServiceEncodingKey;
        }

        public void setKoreaTourInfoServiceEncodingKey(String koreaTourInfoServiceEncodingKey) {
            this.koreaTourInfoServiceEncodingKey = koreaTourInfoServiceEncodingKey;
        }

        public String getKoreaTourInfoServiceDecodingKey() {
            return koreaTourInfoServiceDecodingKey;
        }

        public void setKoreaTourInfoServiceDecodingKey(String koreaTourInfoServiceDecodingKey) {
            this.koreaTourInfoServiceDecodingKey = koreaTourInfoServiceDecodingKey;
        }
    }
}
