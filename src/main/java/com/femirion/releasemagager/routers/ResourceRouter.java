package com.femirion.releasemagager.routers;

import com.femirion.releasemagager.handlers.AddResourceHandler;
import com.femirion.releasemagager.handlers.GetResourcesByVersionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AddResourceHandler addResourceHandler,
                                                GetResourcesByVersionHandler getResourcesByVersionHandler) {
        return RouterFunctions
                .route(POST("/deploy").and(accept(MediaType.APPLICATION_JSON)), addResourceHandler::addResource)
                .and(RouterFunctions.route(GET("/services"), getResourcesByVersionHandler::getResourcesByVersion));
    }

}
