package com.example.neo4jKG.Entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;


import java.util.HashSet;

import java.util.Set;




@NodeEntity(label = "NeoEntity")
public class NeoEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "name")
    private String name;

    public NeoEntity(){}
//    private NeoEntity() {
//        // Empty constructor required as of Neo4j API 2.0.5
//    };

    public NeoEntity(String name) {
        this.name = name;
    }

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "Relate", direction = Relationship.Direction.OUTGOING)
    public Set<NeoEntity> relates = new HashSet<>();

    @Override
    public String toString() {
        return "NeoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", relates=" + relates +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NeoEntity> getRelates() {
        return relates;
    }

    public void setRelates(Set<NeoEntity> relates) {
        this.relates = relates;
    }

    public boolean addRelates(NeoEntity to){
        return this.relates.add(to);
    }
}
