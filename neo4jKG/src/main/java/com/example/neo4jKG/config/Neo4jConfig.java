package com.example.neo4jKG.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.example.neo4jKG.Dao")
@EnableTransactionManagement
public class Neo4jConfig {
}
