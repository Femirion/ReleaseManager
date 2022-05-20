package com.femirion.releasemagager.routers;

import com.femirion.releasemagager.handlers.AddResourceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AddResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AddResourceHandler resourceHandler) {
        return RouterFunctions
                .route(POST("/deploy").and(accept(MediaType.APPLICATION_JSON)), resourceHandler::addResource);
    }

}
