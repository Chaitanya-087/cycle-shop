package com.example.apigateway;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {
    
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/swagger-resources")
    public ResponseEntity<?> swaggerResources() {
        return ResponseEntity.ok(discoveryClient.getServices());
    }
        
    @GetMapping("/v3/api-docs/swagger-config")
    public Map<String, Object> swaggerUiConfig(ServerHttpRequest serverHttpRequest) throws URISyntaxException {
         URI uri = serverHttpRequest.getURI();
        String url = new URI(uri.getScheme(), uri.getAuthority(), null, null, null).toString();
        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
        List<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new LinkedList<>();
        System.out.println("Services = " + discoveryClient.getServices());
        discoveryClient.getServices().stream().forEach(serviceName ->
                        swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                serviceName.toUpperCase(),
                                url + "/v3/api-docs",
                                serviceName.toUpperCase()
                        )));
        swaggerConfig.put("urls", swaggerUrls);
        return swaggerConfig;
    }
    
}
