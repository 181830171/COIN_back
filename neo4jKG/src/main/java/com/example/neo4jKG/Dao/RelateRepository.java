package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.Relation;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelateRepository extends Neo4jRepository<Relation, Long> {
//    @Query("MATCH (fromNode) WHERE id(fromNode) = :#{#fromId} MATCH (toNode) WHERE id(toNode) = :#{#toId} MATCH (fromNode)-[r]->(toNode) DELETE r")
//    void deleteByNodeId(@Param(value = "fromId") long fromId, @Param(value = "toId") long toId);

//    @Query("MATCH (p)-[]->() WHERE id(p) = :#{#id} " +
//            "RETURN count(*)")
//    int countFromRelationById(@Param(value="id") long id);
//
//    @Query("MATCH ()-[]->(p) WHERE id(p) = :#{#id} RETURN count(*)")
//    int countToRelationById(@Param(value = "id") long id);

}