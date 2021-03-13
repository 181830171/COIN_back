package com.example.neo4jKG.VO;

import java.util.ArrayList;

public class NeoAndRelationListVO {
    private int categories;
    private ArrayList<NeoEntityVO> nodes;
    private ArrayList<RelationVO> links;

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

    public int getCategories() {
        return categories;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public ArrayList<NeoEntityVO> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<NeoEntityVO> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<RelationVO> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<RelationVO> links) {
        this.links = links;
    }
}
