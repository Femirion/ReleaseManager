package com.femirion.releasemagager.handlers;

import com.femirion.releasemagager.domain.Resource;
import com.femirion.releasemagager.service.ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class GetAllResourcesHandler {

    private final ResourceStorage resourceStorage;

    public Mono<ServerResponse> getAllResource(ServerRequest request) {
        var allResources = resourceStorage.getAllResource();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allResources, Resource.class);
    }
}
