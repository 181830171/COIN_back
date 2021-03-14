package com.example.neo4jKG.VO;

public class NeoEntityVO {
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String name="";

    // description
    private String des="";

    // 半径
    private int symbolSize;

    // 种类(颜色)
    private int category;

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
