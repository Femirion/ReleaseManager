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
    private final Map<String, Integer> correspondSystemVersions = new HashMap<>();
    private int systemVersionNumber = 0;

    @Override
    public int addResource(Resource resource) {
        var currentVersion = correspondSystemVersions.getOrDefault(resource.name(), -1);
        if (systemVersionNumber != currentVersion) {
            systemVersionNumber++;
            correspondSystemVersions.put(resource.name(), systemVersionNumber);
        }

        serviceMap.merge(
                resource.name(),
                Map.of(systemVersionNumber, List.of(resource)),
                (oldMap, newMap) -> {
                    var result = new HashMap<>(newMap);
                    for (var element : oldMap.entrySet()) {
                        result.merge(
                                systemVersionNumber,
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
        return systemVersionNumber;
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
