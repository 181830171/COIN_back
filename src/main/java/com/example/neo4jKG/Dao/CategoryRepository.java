package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    @Query("MATCH (n) WHERE id(n) = :#{#id} SET n.name = :#{#name}, n.color = :#{#color}RETURN n")
    Category updateCategory(@Param(value = "id") Long id, @Param(value = "name") String name,
                            @Param(value = "color") String color);
}
