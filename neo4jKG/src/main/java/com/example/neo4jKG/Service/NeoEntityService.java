package com.example.neo4jKG.Service;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relate;

import java.util.List;
import java.util.Optional;

public interface NeoEntityService {
    NeoEntity create(NeoEntity neoEntity);

    void deleteById(Long id);

    Optional<NeoEntity> findById(Long id);

    List<NeoEntity> findAll();

    Relate addIRelates(NeoEntity from, NeoEntity to, boolean isSolid);

    NeoEntity updateByEntity(Long id, String name);

    void deleteRelateById(long fromId, long toId);
}
