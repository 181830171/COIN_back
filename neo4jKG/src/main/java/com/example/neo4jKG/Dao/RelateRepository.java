package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.Relation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelateRepository extends Neo4jRepository<Relation, Long> {
    @Query("MATCH (fromNode) WHERE id(fromNode) = {fromId} MATCH (toNode) WHERE id(toNode) = {toId} MATCH (fromNode)-[r]->(toNode) DELETE r")
    void deleteByNodeId(@Param(value = "fromId") long fromId, @Param(value = "toId") long toId);



}
