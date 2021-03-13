package com.example.neo4jKG.VO;

public class RelationVO {

    private long id;

    @Override
    public String toString() {
        return "RelationVO{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", lineStyle=" + lineStyle +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // 源
    private String source;

    // 目标
    private String target;

    private String name;
    // description
    private String des;
    // 线的样式
    private LineStyleVO lineStyle;

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
