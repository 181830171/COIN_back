package com.example.neo4jKG.Entity;

import com.oracle.webservices.internal.api.message.PropertySet;
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
    @Property(name = "des")
    private String des;
    @Property(name = "x")
    private int x;
    @Property(name = "y")
    private int y;
    @Property(name = "symbolSize")
    private int symbolSize;
    @Property(name = "category")
    private int category;

    @Override
    public String toString() {
        return "NeoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", symbolSize=" + symbolSize +
                ", category=" + category +
                ", relates=" + relates +
                '}';
    }

    public int getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(int symbolSize) {
        this.symbolSize = symbolSize;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

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
