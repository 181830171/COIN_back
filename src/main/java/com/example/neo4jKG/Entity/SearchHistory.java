package com.example.neo4jKG.Entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

@NodeEntity(label = "searchHistory")
public class SearchHistory {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "history")
    private String history;

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", history='" + history + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
