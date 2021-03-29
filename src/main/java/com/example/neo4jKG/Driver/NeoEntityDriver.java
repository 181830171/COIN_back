package com.example.neo4jKG.Driver;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Entity.Relation;
import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.neo4j.driver.Values.parameters;


@Component
public class NeoEntityDriver {
    Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "toor"));
    private final Session session = driver.session();
    public void updateRelation(long id, String name){
        HashSet<Map<String, Object>> nodes;
        Relation relation = new Relation();
        try {
            String updateRel = "MATCH (r) WHERE id(r)=$id SET r.name=$name RETURN r as node";
            session.run(updateRel,parameters("id",id,"name",name));
//            nodes = getNodes(result);
//            for(Map<String, Object> node:nodes){
//                relation.setRelationshipId((long)node.get("nodeid"));
//                relation.setName((String)node.get("name"));
//                relation.setDes((String)node.get("des"));
//
//                break;
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void deleteNode(long id){
        try {
            String deleteRel = "MATCH (n)-[]-(r) WHERE id(n)=$id DETACH DELETE r"; //删除有关的关系
            String deleteNode = "MATCH (n) WHERE id(n)=$id DETACH DELETE n"; // 删除该节点
            session.run(deleteRel,parameters("id",id));
            session.run(deleteNode,parameters("id",id));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void clearRepository(){
        String clear = "MATCH (n) DETACH DELETE n";
        try{
            session.run(clear);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteRel(long id){
        try {
            String deleteRel = "MATCH (n) WHERE id(n)=$id DETACH DELETE n"; //删除有关的关系
            session.run(deleteRel,parameters("id", id));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取该节点指向的所有节点
     * @param id
     * @return
     */
    public List<NeoEntity> findRelatedNodes(long id){
        HashSet<Map<String, Object>> nodes;
        List<NeoEntity> resNodes = new ArrayList<>();
        try {
            String cmdSql = "MATCH (n)-[]-()-[]-(t) WHERE id(n)=$id RETURN t AS node";
            Result result = session.run(cmdSql,parameters("id",id));
            nodes= getNodes(result);
            resNodes = getNeoEntities(nodes);
        }catch(Exception e) {
            System.err.println(e.getClass() + "," + e.getMessage());
        }
        return resNodes;
    }

    public List<NeoEntity> getNeoEntities(HashSet<Map<String, Object>> nodes){
        List<NeoEntity> resNodes = new ArrayList<>();
        for(Map<String, Object> node: nodes){
            NeoEntity neoEntity = new NeoEntity();
            neoEntity.setId((Long) (node.get("nodeId")));
            neoEntity.setDes((String)(node.get("des")));
            neoEntity.setCenterX((Double) (node.get("centerX")));
            neoEntity.setCenterY((Double) (node.get("centerY")));
            neoEntity.setCategory((Integer)(node.get("category")));
            neoEntity.setSymbolSize((Integer)(node.get("symbolSize")));
            neoEntity.setX((Double) (node.get("x")));
            neoEntity.setY((Double) (node.get("y")));
            resNodes.add(neoEntity);
        }
        return resNodes;

    }

    // TODO: 通过nodes返回关系列表,主要是获取from和to的节点
//    public List<Relation> getRelations(HashSet<Map<String, Object>> nodes){
//        List<Relation> resNodes = new ArrayList<>();
//        String getFromCmd = "MATCH (r)-[t:FROM]-(n) WHERE id(r)=$id RETURN n AS node";
//        String getToCmd = "MATCH (r)-[t:TO]-(n) WHERE id(r)=$id RETURN n AS node";
//        for(Map<String, Object> node:nodes){
//            Relation relation = new Relation();
//            Long id = (Long)(node.get("nodeid"));
//
//        }
//    }

    public HashSet<Map<String, Object>> getNodes(Result result) {
        HashSet<Map<String, Object>> nodedatas = new HashSet<Map<String, Object>>();// 存放所有的节点数据
        try  {
//            HashSet<Map<String, Object>> allrelationships = new HashSet<Map<String, Object>>();// 存放所有的节点数据

            while (result.hasNext()){
                Record record = result.next();
                Map<String, Object> resultData = new HashMap<>();
                Node node = (Node) record.get("node").asNode();
                Map<String, Object> data = node.asMap();
                for(String key:data.keySet()){
                    resultData.put(key,data.get(key));
                }
                resultData.put("nodeId",node.id());
                nodedatas.add(resultData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodedatas;
    }

    public void close() {
        // Closing a driver immediately shuts down all open connections.
        session.close();
        driver.close();
    }
}
