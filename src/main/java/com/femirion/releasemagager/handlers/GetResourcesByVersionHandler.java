package com.femirion.releasemagager.handlers;

import com.femirion.releasemagager.domain.Resource;
import com.femirion.releasemagager.service.ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class GetResourcesByVersionHandler {

    private final ResourceStorage resourceStorage;

    public Mono<ServerResponse> getResourcesByVersion(ServerRequest request) {
        var version = request.queryParam("systemVersion")
                .map(Integer::valueOf)
                .orElseThrow(() -> new IllegalArgumentException("url must contain systemVersion"));
        var offset = request.queryParam("page")
                .map(Integer::valueOf)
                .orElse(0);
        int pageSize = request.queryParam("size")
                .map(Integer::valueOf)
                .orElse(0);

        Flux<Resource>  resources;
        if (pageSize > 0) {
            resources = getResourcesByVersionWithPage(version, offset, pageSize);
        } else {
            resources = getResourcesByVersionWithoutPage(version, offset);
        }

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resources, Resource.class);
    }

    private Flux<Resource> getResourcesByVersionWithPage(int version, int offset, int pageSize) {
        return resourceStorage.getAllByVersion(version).skip(offset).take(pageSize);
    }

    private Flux<Resource> getResourcesByVersionWithoutPage(int version, int offset) {
        return resourceStorage.getAllByVersion(version).skip(offset);
    }
}
