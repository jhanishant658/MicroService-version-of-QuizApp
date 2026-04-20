package MicroService.QuizApp.GateWay.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 🔥 Skip public APIs
        if (path.contains("/api/users/login") || path.contains("/api/users/register")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Token");
        }

        String token = authHeader.substring(7);

        String username = jwtUtils.extractUsername(token);

        if (!jwtUtils.validateToken(token, username)) {
            throw new RuntimeException("Invalid Token");
        }

        // 🔥 ROLE CHECK
        String role = jwtUtils.getUserRole(token);

        if (path.contains("/admin") && !"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied");
        }

        return chain.filter(exchange);
    }
}