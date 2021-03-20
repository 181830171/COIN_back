package com.example.neo4jKG.Entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;


@RelationshipEntity(type = "Relate")
public class Relation {
    @Id
    @GeneratedValue
    private Long relationshipId;

    @StartNode
    private NeoEntity from;

    @EndNode
    private NeoEntity to;

    @Property(name = "name")
    private String name;

    @Property(name = "des")
    private String des;

    @Property
    private Boolean isSolid;
    @Override
    public String toString() {
        return "Relate{" +
                "relationshipId=" + relationshipId +
                ", from=" + from +
                ", to=" + to +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", isSolid=" + isSolid +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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


    public Relation(){}

    public Relation(Long id, NeoEntity from, NeoEntity to, boolean isSolid, String des, String name){
        this.relationshipId = id;
        this.from = from;
        this.to = to;
        this.isSolid = isSolid;
        this.des = des;
        this.name = name;
    }
}
