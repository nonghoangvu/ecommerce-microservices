package vn.microservice.config;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;
import vn.microservice.ErrorResponse;

import org.springframework.core.io.buffer.DataBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j(topic = "API-REQUEST-FILTER")
public class CustomizeFilter extends AbstractGatewayFilterFactory<CustomizeFilter.Config> {

    public CustomizeFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(CustomizeFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String url = request.getPath().toString();
            log.info("====================[ {} ]====================", url);

            if(isWhileListURL(url)) {
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                }));
            }

            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders requestHeaders = request.getHeaders();

            if (requestHeaders.containsKey(AUTHORIZATION)) {
                // Get access token from header
                final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);

                // Verify access token
                /*
                 * invalid return forbidden
                 */
                // check permission
                /*
                *   if(200 != checkPermissionResponse.getStatus) {
                *   return printErrorResponse(exchange.getResponse(), FORBIDDEN, url, checkPermissionResponse.getMessage())
                * }
                 */
                log.info("Request valid");

                return chain.filter(exchange).then(Mono.fromRunnable(() -> {}));
            } else {
                log.info("Request not valid, URL={}", url);
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {}));
            }
        };
    }

    public static class Config {
        // Add configuration properties if needed
    }

    private boolean isWhileListURL(String url) {
        List<String> permitUrls = new LinkedList<>();
        permitUrls.add("/access-token");
        // over more
        return permitUrls.contains(url);
    }

    private @NotNull Mono<Void> printErrorMessage(@NotNull ServerHttpResponse response, @NotNull HttpStatus status, String url, String message) {
        log.info("Request valid, URL={}", url);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(url);
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setMessage(message);

        byte[] bytes = new Gson().toJson(errorResponse).getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.just(buffer));
    }
}
