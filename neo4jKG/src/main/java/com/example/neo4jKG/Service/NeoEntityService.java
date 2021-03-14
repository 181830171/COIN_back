package com.example.neo4jKG.Service;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.VO.NeoAndRelationListVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import com.example.neo4jKG.VO.RelationVO;

import java.util.List;

public interface NeoEntityService {
    NeoEntityVO addNeoEntity(NeoEntityVO neoEntity);

    void deleteByIdCus(Long id);

    NeoEntityVO findById(Long id);

    List<NeoEntity> findAll();

    RelationVO addIRelates(Long from, Long to, boolean isSolid, String des);

    NeoEntityVO updateByEntity(NeoEntityVO neoEntityVO);

    void deleteRelateById(long fromId, long toId);

    NeoAndRelationListVO getAllEntitiesAndRelations();

    void clearRepository();
}
