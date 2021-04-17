package com.example.neo4jKG.VO;

import com.example.neo4jKG.Entity.NeoEntity;

import java.util.List;

public class NodeListVO {

    List<NeoEntity> nodes;

    public List<NeoEntity> getNodes() {
        return nodes;
    }

    public void setNodes(List<NeoEntity> nodes) {
        this.nodes = nodes;
    }


    @Override
    public String toString() {
        return "NodeListVO{" +
                "nodes=" + nodes +
                '}';
    }
}
