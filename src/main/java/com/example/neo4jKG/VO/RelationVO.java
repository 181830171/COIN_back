package com.example.neo4jKG.VO;

public class RelationVO {

    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 源
    private String source="";

    // 目标
    private String target="";

    private String name="";
    // description
    private String des="";

    // 线的样式
    private LineStyleVO lineStyle;

    //线的两端是否有箭头
    private String[] symbol={"pin","arrow"};

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


    public String[] getSymbol() {
        return symbol;
    }

    public void setSymbol(String[] symbol) {
        this.symbol = symbol;
    }

}
