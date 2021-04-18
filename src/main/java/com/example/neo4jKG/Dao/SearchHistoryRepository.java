package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.Entity.SearchHistory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends Neo4jRepository<SearchHistory, Long> {
    @Query("MATCH (n) WHERE n.history = :#{#history} RETURN n")
    List<SearchHistory> findByHistory(@Param(value = "history") String history);
}
