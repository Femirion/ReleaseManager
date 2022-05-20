package com.femirion.releasemagager.handlers;

import com.femirion.releasemagager.domain.Resource;
import com.femirion.releasemagager.service.ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AddResourceHandler {

    private final ResourceStorage resourceStorage;

    public Mono<ServerResponse> addResource(ServerRequest request) {
        var resource = request.bodyToMono(Resource.class)
                .doOnNext(resourceStorage::addResource);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(resource.map(Resource::getVersion), Integer.class));
    }
}
