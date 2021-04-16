package com.example.neo4jKG.Util;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.VO.CategoryVO;
import com.example.neo4jKG.VO.ItemStyleVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import javax.annotation.PostConstruct;
import java.io.*;

@Component
public class ReadFileUtil {
    @Autowired
    NeoEntityService neoEntityService;

    @PostConstruct
    public void readFileAndSave(){
        neoEntityService.clearRepository();
        System.out.println("Start Reading File...");
        File file = new File("test5.json");
        String allStrings = "";
        try{
            FileInputStream in = new FileInputStream(file);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            allStrings = new String(buffer,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("End Reading File");
        if(allStrings.equals(""))
            return;

        JSONArray neoEntities;
        JSONArray links;
        JSONArray categories;

        neoEntities = (JSONArray) JSONObject.fromObject(allStrings).get("data");
        links = (JSONArray)JSONObject.fromObject(allStrings).get("links");
        categories= (JSONArray)JSONObject.fromObject(allStrings).get("categories");
        for(int i=0; i < neoEntities.size();i++){
            JSONObject neoJson = JSONObject.fromObject(neoEntities.getString(i));
            String name = neoJson.getString("name");
            String des = neoJson.getString("des");
            NeoEntityVO neoEntityVO = new NeoEntityVO();
            neoEntityVO.setNodeId((long) -1);
            neoEntityVO.setName(name);
            neoEntityVO.setDes(des);
            neoEntityVO.setSymbol("circle");
            neoEntityService.addNeoEntity(neoEntityVO);
        }

        for(int i=0;i<links.size();i++){
            JSONObject relationJson = JSONObject.fromObject(links.getString(i));
            String source = relationJson.getString("source");
            String target = relationJson.getString("target");
            String name = relationJson.getString("name");
            String des = relationJson.getString("des");
            String type = JSONObject.fromObject(relationJson.getString("lineStyle")).getString("type");
            String[] symbol={"pin","arrow"};
            NeoEntity fromEntity = neoEntityService.findByName(source);
            NeoEntity toEntity = neoEntityService.findByName(target);
            if(fromEntity == null || toEntity == null){
                continue;
            }
            boolean isSolid = false;
            if(type.equals("solid"))
                isSolid = true;
            neoEntityService.addIRelates(fromEntity.getId(),toEntity.getId(),isSolid,des,name,symbol);
        }

        for(int i=0; i < categories.size();i++){
            JSONObject categoryJson = JSONObject.fromObject(categories.getString(i));
            String name = categoryJson.getString("name");
            String color=JSONObject.fromObject(categoryJson.getString("itemStyle")).getString("color");
            neoEntityService.addCategory(name,color);
        }


        String test_result = JSON.toJSONString(neoEntityService.initAllEntities());
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("test5_result.json")),"UTF-8");
            JSONObject obj= JSONObject.fromObject(test_result);//创建JSONObject对象
            osw.write(obj.toString());
            osw.flush();
            osw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
