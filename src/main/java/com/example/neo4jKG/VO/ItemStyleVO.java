package com.example.neo4jKG.VO;

public class ItemStyleVO {
    private String color="";

    public ItemStyleVO(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ItemStyleVO{" +
                "color='" + color + '\'' +
                '}';
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
