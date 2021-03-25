package com.example.neo4jKG.Entity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@NodeEntity(label = "category")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name="name")
    private String name;

    @Property(name="symbol")
    private String symbol;

    @Property(name="color")
    private String color;

    @Override
    public String toString(){
        return "Category{" +
                "id="+id+
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", color='" + color +'\''+
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
