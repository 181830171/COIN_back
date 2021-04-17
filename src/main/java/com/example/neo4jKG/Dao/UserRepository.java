package com.example.neo4jKG.Dao;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (n) WHERE n.username = :#{#username} RETURN n")
    List<User> findByUsername(@Param(value = "username") String username);
}
