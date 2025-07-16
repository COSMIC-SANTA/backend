package SANTA.backend.global.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final KoreaTourOrganization koreaTourOrganization = new KoreaTourOrganization();
    private final JWT jwt = new JWT();

    public KoreaTourOrganization getKoreaTourOrganization() {
        return this.koreaTourOrganization;
    }

    public JWT getJwt(){
        return this.jwt;
    }

    public static class KoreaTourOrganization {
        private String koreaTourInfoServiceEncodingKey;
        private String koreaTourInfoServiceDecodingKey;
        private String koreaTourInfoServiceURL;
        private String mobileAppName;

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

        public void setKoreaTourInfoServiceURL(String koreaTourInfoServiceURL){
            this.koreaTourInfoServiceURL = koreaTourInfoServiceURL;
        }

        public String getKoreaTourInfoServiceURL(){
            return koreaTourInfoServiceURL;
        }

        public void setMobileAppName(String mobileAppName){
            this.mobileAppName = mobileAppName;
        }

        public String getMobileAppName(){
            return mobileAppName;
        }

        public String getKoreaTourInfoServiceRoutingKey(){return koreaTourInfoServiceEncodingKey;}
    }

    public static class JWT{
        private String secret;

        public String getSecret(){return secret;}

        public void setSecret(String secret){this.secret = secret;}
    }
}
