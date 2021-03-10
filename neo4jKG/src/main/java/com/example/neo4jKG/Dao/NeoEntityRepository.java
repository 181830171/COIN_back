package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.NeoEntity;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NeoEntityRepository extends Neo4jRepository<NeoEntity, Long> {
    @Query("MATCH (n) WHERE id(n) = :#{#id} SET n.name = :#{#name} RETURN n")
    NeoEntity updateByEntity(@Param(value = "id") Long id, @Param(value = "name") String name);
}
