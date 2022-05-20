package com.femirion.releasemagager.service;

import com.femirion.releasemagager.domain.Resource;
import reactor.core.publisher.Flux;

public interface ResourceStorage {
    int addResource(Resource resource);
    Flux<Resource> getAllByVersion(int version);
    Flux<Resource> getAllResource();
}
