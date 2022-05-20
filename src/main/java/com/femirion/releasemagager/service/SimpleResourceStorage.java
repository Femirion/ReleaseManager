package com.femirion.releasemagager.service;

import com.femirion.releasemagager.domain.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;

@Slf4j
@Service
public class SimpleResourceStorage implements ResourceStorage {
    private final Map<String, Map<Integer, List<Resource>>> serviceMap = new HashMap<>();

    @Override
    public Resource addResource(Resource resource) {
        serviceMap.merge(
                resource.name(),
                Map.of(resource.version(), List.of(resource)),
                (oldMap, newMap) -> {
                    var result = new HashMap<>(newMap);
                    for (var element : oldMap.entrySet()) {
                        result.merge(
                                element.getKey(),
                                element.getValue(),
                                (oldElement, newElement) -> {
                                    newElement.addAll(oldElement);
                                    return newElement;
                                });
                        return newMap;
                    }
                    return result;
                });
        log.debug("resource was added. resource={}", resource);
        return resource;
    }

    @Override
    public Flux<Resource> getAllByVersion(int version) {
        return Flux.fromIterable(serviceMap.values().stream()
                .map(e -> e.getOrDefault(version, new ArrayList<>()))
                .flatMap(Collection::stream)
                .toList());
    }

    @Override
    public Flux<Resource> getAllResource() {
        return Flux.fromIterable(serviceMap.values()
                .stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .toList()
        );
    }
}
