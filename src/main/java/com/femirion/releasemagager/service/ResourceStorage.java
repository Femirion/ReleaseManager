package com.femirion.releasemagager.service;

import com.femirion.releasemagager.domain.Resource;

import java.util.Collection;

public interface ResourceStorage {
    Resource addResource(Resource resource);
    Collection<Resource> getAllByName();
    Collection<Resource> getAllResource();
}
