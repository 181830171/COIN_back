package com.example.neo4jKG.VO;

public class NeoEntityVO {
    private Long nodeId;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    private String name="";

    // description
    private String des="";

    // 半径
    private Integer symbolSize=0;

    // 种类(颜色)
    private Long category;

    // 坐标 x, y
    private Double x=0.0;
    private Double y=0.0;

    // 形状
    private String symbol="circle";

    @Override
    public String toString() {
        return "NeoEntityVO{" +
                "nodeId=" + nodeId +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", symbolSize=" + symbolSize +
                ", category=" + category +
                ", x=" + x +
                ", y=" + y +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
