package com.femirion.releasemagager.service;

import com.femirion.releasemagager.domain.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;

@Slf4j
@Service
public class SimpleResourceStorage implements ResourceStorage {
    private final Map<Integer, List<Resource>> serviceMap = new HashMap<>();

    @Override
    public Resource addResource(Resource resource) {
        serviceMap.merge(
                resource.version(),
                List.of(resource),
                (oldList, newList) -> {
                    var result = new ArrayList<>(oldList);
                    result.addAll(newList);
                    return result;
                });
        log.debug("resource was added. resource={}", resource);
        return resource;
    }

    @Override
    public Flux<Resource> getAllByVersion(int version) {
        return Flux.fromIterable(serviceMap.getOrDefault(version, new ArrayList<>()));
    }

    @Override
    public Flux<Resource> getAllResource() {
        return Flux.fromIterable(serviceMap.values()
                .stream()
                .flatMap(Collection::stream)
                .toList()
        );
    }
}
