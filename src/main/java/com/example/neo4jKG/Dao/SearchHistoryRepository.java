package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.Entity.SearchHistory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends Neo4jRepository<SearchHistory, Long> {
}
