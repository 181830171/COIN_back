package com.example.neo4jKG.VO;

public class CategoryVO {
    private Long categoryId;
    private String name="";
    private String symbol="";
    private ItemStyleVO itemStyle;

    @Override
    public String toString(){
        return "CategoryVO{" +
                "id="+categoryId+
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", itemStyle='" + itemStyle +
                '}';
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public ItemStyleVO getItemStyle() {
        return itemStyle;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setItemStyle(ItemStyleVO itemStyle) {
        this.itemStyle = itemStyle;
    }

}
