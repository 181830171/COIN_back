package com.example.neo4jKG.JsonFileEntity;

public class JsonFileData {
    private String name = "";
    private String des = "";

    @Override
    public String toString() {
        return "JsonFileData{" +
                "name='" + name + '\'' +
                ", des='" + des + '\'' +
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
}
