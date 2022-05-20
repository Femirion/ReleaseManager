package com.femirion.releasemagager.service;

import com.femirion.releasemagager.domain.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SimpleResourceStorage implements ResourceStorage {
    private final Map<String, List<Integer>> serviceMap = new HashMap<>();

    @Override
    public Resource addResource(Resource resource) {
        serviceMap.merge(
                resource.getName(),
                List.of(resource.getVersion()),
                (oldList, newList) -> {
                    var result = new ArrayList<>(oldList);
                    result.addAll(newList);
                    return result;
                });
        log.debug("resource was added. resource={}", resource);
        return resource;
    }

    @Override
    public Collection<Resource> getAllByName() {
        return null;
    }

    @Override
    public Collection<Resource> getAllResource() {
        return null;
    }
}
