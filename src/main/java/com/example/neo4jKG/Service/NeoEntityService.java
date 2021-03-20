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

    NeoEntity findByName(String name);

    List<NeoEntity> findAll();

    RelationVO addIRelates(Long from, Long to, boolean isSolid, String des, String name);

    NeoEntityVO updateByEntity(NeoEntityVO neoEntityVO);

    void deleteRelateById(long id);

    NeoAndRelationListVO getAllEntitiesAndRelations();

    void clearRepository();

    void updateRel(long id, String name);
}
