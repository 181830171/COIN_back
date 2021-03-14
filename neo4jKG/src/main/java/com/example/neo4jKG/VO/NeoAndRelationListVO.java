package com.example.neo4jKG.VO;

import java.util.ArrayList;
import java.util.List;

public class NeoAndRelationListVO {
    private Integer categories;
    private List<NeoEntityVO> nodes;
    private List<RelationVO> links;

    public NeoAndRelationListVO(){
        categories = 0;
        nodes = new ArrayList<>();
        links = new ArrayList<>();
    }

    public void addNeoEntity(NeoEntityVO node){
        nodes.add(node);
    }

    public void addLink(RelationVO link){
        links.add(link);
    }


    @Override
    public String toString() {
        return "NeoAndRelationListVO{" +
                "categories=" + categories +
                ", nodes=" + nodes +
                ", links=" + links +
                '}';
    }

    public Integer getCategories() {
        return categories;
    }

    public void setCategories(Integer categories) {
        this.categories = categories;
    }

    public List<NeoEntityVO> getNodes() {
        return nodes;
    }

    public void setNodes(List<NeoEntityVO> nodes) {
        this.nodes = nodes;
    }

    public List<RelationVO> getLinks() {
        return links;
    }

    public void setLinks(List<RelationVO> links) {
        this.links = links;
    }
}
