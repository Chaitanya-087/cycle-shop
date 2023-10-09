package com.example.apigateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

  public static final List<String> openApiEndpoints = List.of(
    "/api/auth/register",
    "/api/auth/token",
    "/api/auth/validate",
    "/v3/api-docs/**",
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/actuator/**",
    "/eureka"
  );

  public Predicate<ServerHttpRequest> isSecured =
  request -> openApiEndpoints
          .stream()
          .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
