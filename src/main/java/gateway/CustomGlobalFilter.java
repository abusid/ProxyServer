package gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class CustomGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().toSingleValueMap().get("Authorization");
        if(authHeader == null || authHeader.isEmpty() || (!authHeader.startsWith("Basic ") && !authHeader.startsWith("Bearer "))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return chain.filter(exchange);
    }

}
