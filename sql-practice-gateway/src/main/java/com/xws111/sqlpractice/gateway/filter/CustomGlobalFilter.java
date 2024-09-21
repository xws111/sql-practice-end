package com.xws111.sqlpractice.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取客户端 IP 地址
        String reqIp = getClientIpAddress(exchange);
        // 获取请求信息
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethodValue();
        log.info("Incoming request: method={}, path={}, remoteIp={}", method, path, reqIp);
        // 继续处理请求，并在响应返回前后记录响应信息
        return chain.filter(exchange).doOnSuccess(aVoid -> {
            ServerHttpResponse response = exchange.getResponse();
            log.info("Response status code: {}", response.getStatusCode());
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public String getClientIpAddress(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        // 优先检查 X-Forwarded-For 头，通常代理服务器会设置这个头部
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            // 尝试从 X-Real-IP 头获取
            ip = request.getHeaders().getFirst("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            // 回退到获取 RemoteAddress
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        // 如果 IP 是 IPv6 本地地址 "::1"，将其转换为 IPv4 "127.0.0.1"
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
