package com.example.neo4jKG.VO;

public class LineStyleVO {
    private String type;

    @Override
    public String toString() {
        return "LineStyleVO{" +
                "type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
