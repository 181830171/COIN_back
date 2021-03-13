package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.NeoEntity;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NeoEntityRepository extends Neo4jRepository<NeoEntity, Long> {
    @Query("MATCH (n) WHERE id(n) = :#{#id} SET n.name = :#{#name}, n.x = :#{#x}, n.y = :#{#x} RETURN n")
    NeoEntity updateByEntity(@Param(value = "id") Long id, @Param(value = "name") String name, @Param(value = "x") int x,
                             @Param(value = "y") int y, @Param(value = "des") String des, @Param(value = "category") int category,
                             @Param(value = "symbolSize") int symbolSize);

    @Query("MATCH (n) WHERE id(n) = {id} DETACH DELETE n")
    void deleteByIdCus(@Param(value = "id") long id);
}
