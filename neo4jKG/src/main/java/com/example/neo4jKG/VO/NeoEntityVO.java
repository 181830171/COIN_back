package com.example.neo4jKG.VO;

public class NeoEntityVO {
    private Long id;

    @Override
    public String toString() {
        return "NeoEntityVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", symbolSize=" + symbolSize +
                ", category=" + category +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name="";

    // description
    private String des="";

    // 半径
    private Integer symbolSize;

    // 种类(颜色)
    private Integer category;

    // 坐标 x, y
    private Double x;
    private Double y;

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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
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
