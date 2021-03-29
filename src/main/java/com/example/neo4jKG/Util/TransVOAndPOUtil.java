package com.example.neo4jKG.Util;

import com.example.neo4jKG.Entity.Category;
import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import com.example.neo4jKG.VO.*;
import org.springframework.stereotype.Component;

@Component
public class TransVOAndPOUtil {

    public NeoEntity transNeoEntityVO(NeoEntityVO neoEntityVO){
        NeoEntity neoEntity = new NeoEntity();
        neoEntity.setId(neoEntityVO.getNodeId());
        neoEntity.setName(neoEntityVO.getName());
        neoEntity.setDes(neoEntityVO.getDes());
        neoEntity.setSymbol(neoEntityVO.getSymbol());
        neoEntity.setX(neoEntityVO.getX());
        neoEntity.setY(neoEntityVO.getY());
        return neoEntity;
    }

    public NeoEntityVO transNeoEntity(NeoEntity neoEntity){
        NeoEntityVO neoEntityVO = new NeoEntityVO();
        neoEntityVO.setNodeId(neoEntity.getId());
        neoEntityVO.setName(neoEntity.getName());
        neoEntityVO.setDes(neoEntity.getDes());
        neoEntityVO.setSymbol(neoEntity.getSymbol());
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
        if(relation.getFrom()==null){
            relationVO.setSource(null);
        }else {
            relationVO.setSource(relation.getFrom().getName());
        }
        if(relation.getTo()!=null){
            relationVO.setTarget(relation.getTo().getName());
        }else {
            relationVO.setTarget(null);
        }

        LineStyleVO lineStyleVO = new LineStyleVO();
        if(relation.isSolid()){
            lineStyleVO.setType("solid");
        }else {
            lineStyleVO.setType("dotted");
        }
        relationVO.setLineStyle(lineStyleVO);


        relationVO.setSymbol(new String[]{relation.getSymbolFrom(),relation.getSymbolTo()});

        return relationVO;
    }

    public CategoryVO transCategory(Category category){
        CategoryVO categoryVO=new CategoryVO();
        categoryVO.setCategoryId(category.getId());
        categoryVO.setName(category.getName());
        categoryVO.setItemStyle(new ItemStyleVO(category.getColor()));
        return categoryVO;
    }

    public Category transCategoryVO(CategoryVO categoryVO){
        Category category=new Category();
        category.setId(categoryVO.getCategoryId());
        category.setColor(categoryVO.getItemStyle().getColor());
        category.setName(categoryVO.getName());
        return category;
    }
}
