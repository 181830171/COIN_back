package com.example.neo4jKG.Driver;

import com.example.neo4jKG.Entity.NeoEntity;
import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.neo4j.driver.Values.parameters;


@Component
public class NeoEntityDriver {
    Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "toor"));
    private final Session session = driver.session();


    /**
     * 获取该节点指向的所有节点
     * @param id
     * @return
     */
    public List<NeoEntity> findRelatedNodes(long id){
        HashSet<Map<String, Object>> nodes;
        List<NeoEntity> resNodes = new ArrayList<>();
        try {
            String cmdSql = "MATCH (n)-[]-()-[]-(t) WHERE id(n)=$id RETURN t AS entity";
            Result result = session.run(cmdSql,parameters("id",id));
            nodes= getNodes(result);
            for(Map<String, Object> node: nodes){
                NeoEntity neoEntity = new NeoEntity();
                neoEntity.setId((long)(node.get("nodeid")));
                neoEntity.setDes((String)(node.get("des")));
                neoEntity.setCenterX((double)(node.get("centerX")));
                neoEntity.setCenterY((double)(node.get("centerY")));
                resNodes.add(neoEntity);
            }
        }catch(Exception e) {
            System.err.println(e.getClass() + "," + e.getMessage());
        }
        return resNodes;
    }

    public HashSet<Map<String, Object>> getNodes(Result result) {
        HashSet<Map<String, Object>> nodedatas = new HashSet<Map<String, Object>>();// 存放所有的节点数据
        try  {
//            HashSet<Map<String, Object>> allrelationships = new HashSet<Map<String, Object>>();// 存放所有的节点数据

            while (result.hasNext()){
                Record record = result.next();
                Map<String, Object> resultData = new HashMap<>();
                Node node = (Node) record.get("entity").asNode();
                Map<String, Object> data = node.asMap();
                for(String key:data.keySet()){
                    resultData.put(key,data.get(key));
                }
                resultData.put("nodeid",node.id());
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
