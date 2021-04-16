package com.example.neo4jKG.Service;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.VO.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface NeoEntityService {
    // 增加新的实体
    NeoEntityVO addNeoEntity(NeoEntityVO neoEntity);

    // 根据id删除实体
    void deleteByIdCus(Long id);

    // 根据id查找实体
    NeoEntityVO findById(Long id);

    // 根据名称查找实体
    NeoEntity findByName(String name);

    // 查找所有实体
    List<NeoEntity> findAll();

    // 添加新的关系
    RelationVO addIRelates(Long from, Long to, boolean isSolid, String des, String name,String[] symbol);

    // 更新实体
    NeoEntityVO updateByEntity(NeoEntityVO neoEntityVO);

    // 根据id删除关系
    void deleteRelateById(long id);

    NodeListVO initAllEntities();

    // 获取所有的实体和关系
    NeoAndRelationListVO getAllEntitiesAndRelations();

    // 清空数据库
    void clearRepository();

    // 更新关系名称
    void updateRel(long id, String name);

    // 修改关系虚实线
    ResponseVO updateRelType(long id,String type);

    // 修改关系虚实线
    ResponseVO updateRelSymbol(long id,String[] symbol);

    //添加节点类型
    CategoryVO addCategory(String name,String color);

    //更新节点类型
    ResponseVO updateCategory(long id, String name, String color);

    //获取搜索记录
    ResponseVO getSearchHistories();

    //搜索节点
    ResponseVO searchNodes(String message);
}
