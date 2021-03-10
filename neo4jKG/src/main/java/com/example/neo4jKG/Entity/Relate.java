package com.example.neo4jKG.Entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;


@RelationshipEntity(type = "Relate")
public class Relate {
    @Id
    @GeneratedValue
    private Long relationshipId;

    @StartNode
    private NeoEntity from;

    @EndNode
    private NeoEntity to;

    @Property
    private boolean isSolid;

    @Override
    public String toString() {
        return "Relate{" +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public NeoEntity getFrom() {
        return from;
    }

    public void setFrom(NeoEntity from) {
        this.from = from;
    }

    public NeoEntity getTo() {
        return to;
    }

    public void setTo(NeoEntity to) {
        this.to = to;
    }


    public Relate(){}

    public Relate(Long id, NeoEntity from, NeoEntity to, boolean isSolid){
        this.relationshipId = id;
        this.from = from;
        this.to = to;
        this.isSolid = isSolid;
    }
}
