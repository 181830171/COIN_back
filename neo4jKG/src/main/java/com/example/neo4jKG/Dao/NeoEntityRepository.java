package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.NeoEntity;

import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeoEntityRepository extends Neo4jRepository<NeoEntity, Long> {
    @Query("MATCH (n) WHERE id(n) = :#{#id} SET n.name = :#{#name}, n.x = :#{#x}, n.y = :#{#y}, n.des = :#{#des}, " +
            "n.category= :#{#category}, n.symbolSize = :#{#symbolSize}, n.centerX = :#{#centerX}, n.centerY = :#{#centerY} RETURN n")
    NeoEntity updateByEntity(@Param(value = "id") Long id, @Param(value = "name") String name, @Param(value = "x") double x,
                             @Param(value = "y") double y, @Param(value = "des") String des, @Param(value = "category") int category,
                             @Param(value = "symbolSize") int symbolSize, @Param(value = "centerX") double centerX,
                             @Param(value = "centerY") double centerY);

    @Query("MATCH (n) WHERE id(n) = :#{#id} DETACH DELETE n")
    void deleteByIdCus(@Param(value = "id") long id);

//    @Query("MATCH (n)-->(t) WHERE id(n) = $id RETURN t")
////            "UNION" +
////            " MATCH (t)<-[]-(n) WHERE id(n) = :#{#id} RETURN t")
//    List<NeoEntity> findDirRelatedNodes(@Param(value = "id") long id);

    @Query("MATCH (n) WHERE n.name = :#{#name} RETURN n")
    List<NeoEntity> findByName(@Param(value = "name") String name);
}
