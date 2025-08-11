package SANTA.backend.core.config;

import SANTA.backend.global.common.AppProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    static int byteCount = 10*1024*1024;
    private final AppProperties appProperties;

    @Bean("forestApiClient")
    public WebClient webClient(@Value("${forest.api.url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                .responseTimeout(Duration.ofSeconds(10))
                                .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                                .addHandlerLast(new WriteTimeoutHandler(10)))
                ))
                .build();
    }

    @Bean("clientServiceBean")
    public WebClient clientServiceBean(){
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(byteCount))
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                .responseTimeout(Duration.ofSeconds(10))
                                .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                                .addHandlerLast(new WriteTimeoutHandler(10)))
                ))
                .build();
    }

    @Bean("kakaoSearchByKeywordClient")
    public WebClient kakaoSearchByKeywordClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + appProperties.getKakaoSearchKeyword().getKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                .responseTimeout(Duration.ofSeconds(10))
                                .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                                .addHandlerLast(new WriteTimeoutHandler(10)))
                ))
                .build();
    }

    @Bean("kakaoRouteClient")
    public WebClient kakaoRouteClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + appProperties.getKakao().getKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                .responseTimeout(Duration.ofSeconds(10))
                                .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                                .addHandlerLast(new WriteTimeoutHandler(10)))
                ))
                .build();
    }
}
