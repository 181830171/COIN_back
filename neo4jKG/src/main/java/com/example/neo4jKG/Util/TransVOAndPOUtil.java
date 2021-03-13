package com.example.neo4jKG.Util;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.VO.LineStyleVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import com.example.neo4jKG.VO.RelationVO;
import org.springframework.stereotype.Component;

@Component
public class TransVOAndPOUtil {

    public NeoEntity transNeoEntityVO(NeoEntityVO neoEntityVO){
        NeoEntity neoEntity = new NeoEntity();
        neoEntity.setId(neoEntityVO.getId());
        neoEntity.setName(neoEntityVO.getName());
        neoEntity.setX(neoEntityVO.getX());
        neoEntity.setY(neoEntityVO.getY());
        return neoEntity;
    }

    public NeoEntityVO transNeoEntity(NeoEntity neoEntity){
        NeoEntityVO neoEntityVO = new NeoEntityVO();
        neoEntityVO.setId(neoEntity.getId());
        neoEntityVO.setName(neoEntity.getName());
        neoEntityVO.setDes(neoEntity.getDes());
        neoEntityVO.setX(neoEntity.getX());
        neoEntityVO.setY(neoEntity.getY());
        neoEntityVO.setCategory(neoEntity.getCategory());
        neoEntityVO.setSymbolSize(neoEntity.getSymbolSize());
        return neoEntityVO;
    }

    public RelationVO transRelation(Relation relation){
        RelationVO relationVO = new RelationVO();
        relationVO.setDes(relation.getDes());
        relationVO.setName(relation.getName());
        relationVO.setId(relation.getRelationshipId());
        relationVO.setSource(relation.getFrom().getName());
        relationVO.setTarget(relation.getTo().getName());
        LineStyleVO lineStyleVO = new LineStyleVO();
        if(relation.isSolid()){
            lineStyleVO.setType("solid");
        }else {
            lineStyleVO.setType("dotted");
        }
        relationVO.setLineStyle(lineStyleVO);
        return relationVO;
    }
}
