package com.example.neo4jKG.JsonFileEntity;

import com.example.neo4jKG.VO.LineStyleVO;

public class JsonFileLink {
    private String source;
    private String target;
    private String name;
    private String des;
    private LineStyleVO lineStyle;

    @Override
    public String toString() {
        return "JsonFileLink{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", lineStyle=" + lineStyle +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public LineStyleVO getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyleVO lineStyle) {
        this.lineStyle = lineStyle;
    }
}
