package com.example.neo4jKG.Util;

import com.example.neo4jKG.Entity.NeoEntity;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.Service.UserService;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class ReadFileUtil {
    @Autowired
    NeoEntityService neoEntityService;

    @Autowired
    UserService userService;

    public static void bufferedWriterMethod(String filepath, String content){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath,true))) {
            bufferedWriter.write(content);
        }catch (IOException exception){
            System.out.println("IOEXCEPTION");
        }
    }

    @PostConstruct
    public void readFileAndSave(){
        neoEntityService.clearRepository();
        userService.register("admin","123456");
        neoEntityService.addCategory("种族","#4992ff");
        neoEntityService.addCategory("职业","#7cffb2");
        neoEntityService.addCategory("性别","#fddd60");
        neoEntityService.addCategory("地点/组织","#ff6e76");
        neoEntityService.addCategory("其它","#ff8a45");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("harrypotterproperty.json")));
            int line = 1;
            String context = null;
            String json = "";
            //json内容转化为Map集合通过遍历集合来进行封装
            while ((context = bufferedReader.readLine()) != null) {
                //Context就是读到的json数据
                json += context;
                line++;
            }
            Map<String, String> result = JSONObject.fromObject(json);
            System.out.println(result);
            Set<String> keySet = result.keySet();
            Iterator<String> it = keySet.iterator();
            while (it.hasNext()){
                // 人名, 添加人名实体
                String name = it.next();
                // 判断实体是否存在
                if(neoEntityService.findByName(name)==null){
                    String des = "person";
                    NeoEntityVO neoEntityVO = new NeoEntityVO();
                    neoEntityVO.setNodeId((long) -1);
                    neoEntityVO.setName(name);
                    neoEntityVO.setCategory(0L);
                    neoEntityVO.setDes(des);
                    neoEntityVO.setSymbol("circle");
                    neoEntityService.addNeoEntity(neoEntityVO);
                    int tmp = name.indexOf("·");
//                    if(tmp >0){
//                        bufferedWriterMethod("dict.txt",name.substring(0,tmp)+" 10 nr\n");
//                    }
                    bufferedWriterMethod("fullname.txt"," "+name+" \n");
                }
                NeoEntity source = neoEntityService.findByName(name);
                Map<String, Object> res2 = JSONObject.fromObject(result.get(name));
                Iterator<String> it2 = res2.keySet().iterator();
                while (it2.hasNext()){
                    String property = it2.next();
                    // 如果属性是家庭信息,则下面还是一个jsonObject
                    if(property.equals("家庭信息")){
                        Map<String, Object> res3 = JSONObject.fromObject(res2.get(property));
                        Iterator<String> it3 = res3.keySet().iterator();
                        while (it3.hasNext()){
                            String family_info = it3.next();
                            if(res3.get(family_info) instanceof JSONArray){
                                JSONArray family_names = (JSONArray) res3.get(family_info);
                                for(int i=0;i<family_names.size();i++){
                                    String family_name = (String) family_names.get(i);
                                    if(neoEntityService.findByName(family_name)==null){
                                        String des = "person";
                                        NeoEntityVO neoEntityVO = new NeoEntityVO();
                                        neoEntityVO.setCategory(0L);
                                        neoEntityVO.setNodeId((long) -1);
                                        neoEntityVO.setName(family_name);
                                        neoEntityVO.setDes(des);
                                        neoEntityVO.setSymbol("circle");
                                        neoEntityService.addNeoEntity(neoEntityVO);
                                        int tmp = family_name.indexOf("·");
//                                        if(tmp>0){
//                                            bufferedWriterMethod("dict.txt",family_name.substring(0,tmp)+" 10 nr\n");
//                                        }
                                        bufferedWriterMethod("fullname.txt"," " + family_name+" \n");
                                    }
                                    NeoEntity target = neoEntityService.findByName(family_name);
                                    String[] symbol={"pin","arrow"};
                                    neoEntityService.addIRelates(source.getId(),target.getId(),true,"家庭信息",family_info,symbol);
                                    System.out.println(name + " " + family_info + " " + family_name);
                                }
                            }else {
                                if(neoEntityService.findByName((String)res3.get(family_info))==null){
                                    String des = "person";
                                    NeoEntityVO neoEntityVO = new NeoEntityVO();
                                    neoEntityVO.setCategory(0L);
                                    neoEntityVO.setNodeId((long) -1);
                                    neoEntityVO.setName((String)res3.get(family_info));
                                    neoEntityVO.setDes(des);
                                    neoEntityVO.setSymbol("circle");
                                    neoEntityService.addNeoEntity(neoEntityVO);
                                    String family_name = (String)res3.get(family_info);
                                    int tmp = family_name.indexOf("·");
//                                    if(tmp>0){
//                                        bufferedWriterMethod("dict.txt",family_name.substring(0,tmp) + " 10 nr\n");
//                                    }
                                    bufferedWriterMethod("fullname.txt"," "+family_name+" \n");
                                }
                                NeoEntity target = neoEntityService.findByName((String)res3.get(family_info));
                                String[] symbol={"pin","arrow"};
                                neoEntityService.addIRelates(source.getId(),target.getId(),true,"家庭信息",family_info,symbol);
                                System.out.println(name + " "+family_info + " " + (String) res3.get(family_info));
                            }
                        }
                    }else {
                        if(res2.get(property) instanceof JSONArray){
                            JSONArray jsonArray = (JSONArray)res2.get(property);
                            for(int i=0;i<jsonArray.size();i++){
                                String property_name = (String)jsonArray.get(i);
                                if(neoEntityService.findByName(property_name)==null){
                                    NeoEntityVO neoEntityVO = new NeoEntityVO();
                                    String des = "";
                                    if(property.equals("从属") || property.equals("学院")){
                                        des = "地点/组织";
                                        neoEntityVO.setCategory(3L);
//                                        bufferedWriterMethod("dict.txt",property_name+" 3 nl\n");
                                    }else if(property.equals("职业")){
                                        des = "职业";
                                        neoEntityVO.setCategory(1L);
                                    }else if(property.equals("性别")){
                                        des = "性别";
                                        neoEntityVO.setCategory(2L);
                                    }else {
                                        des = "其它";
                                        neoEntityVO.setCategory(4L);
                                    }
                                    neoEntityVO.setNodeId((long) -1);
                                    neoEntityVO.setName(property_name);
                                    neoEntityVO.setDes(des);
                                    neoEntityVO.setSymbol("circle");
                                    neoEntityService.addNeoEntity(neoEntityVO);
                                }
                                NeoEntity target = neoEntityService.findByName(property_name);
                                String[] symbol={"pin","arrow"};
                                neoEntityService.addIRelates(source.getId(),target.getId(),true,"",property,symbol);
                                System.out.println(name + " "+ property + " " + property_name);
                            }
                        }else {
                            String property_name = (String)res2.get(property);
                            if(neoEntityService.findByName(property_name)==null){
                                NeoEntityVO neoEntityVO = new NeoEntityVO();
                                String des = "";
                                if(property.equals("从属") || property.equals("学院")){
                                    des = "地点/组织";
                                    neoEntityVO.setCategory(3L);
//                                    bufferedWriterMethod("dict.txt",(String)res2.get(property)+" 3 nl\n");
                                }else if(property.equals("职业")){
                                    des = "职业";
                                    neoEntityVO.setCategory(1L);
                                }else if(property.equals("性别")){
                                    des = "性别";
                                    neoEntityVO.setCategory(2L);
                                }else {
                                    des = "其它";
                                    neoEntityVO.setCategory(4L);
                                }
                                neoEntityVO.setNodeId((long) -1);
                                neoEntityVO.setName((String)res2.get(property));
                                neoEntityVO.setDes(des);
                                neoEntityVO.setSymbol("circle");
                                neoEntityService.addNeoEntity(neoEntityVO);
                            }
                            NeoEntity target = neoEntityService.findByName((String)res2.get(property));
                            String[] symbol={"pin","arrow"};
                            neoEntityService.addIRelates(source.getId(),target.getId(),true,"",property,symbol);
                            System.out.println(name + " " + property + (String)res2.get(property));
                        }
                    }
                }
            }
        }catch (IOException exception){
            System.out.println("exception");
        }
        neoEntityService.initAllEntities();
//        System.out.println("Start Reading File...");
//        File file = new File("test5.json");
//        String allStrings = "";
//        try{
//            FileInputStream in = new FileInputStream(file);
//            int size = in.available();
//            byte[] buffer = new byte[size];
//            in.read(buffer);
//            in.close();
//            allStrings = new String(buffer,"UTF-8");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println("End Reading File");
//        if(allStrings.equals(""))
//            return;
//
//        JSONArray neoEntities;
//        JSONArray links;
//        JSONArray categories;
//
//        neoEntities = (JSONArray) JSONObject.fromObject(allStrings).get("data");
//        links = (JSONArray)JSONObject.fromObject(allStrings).get("links");
//        categories= (JSONArray)JSONObject.fromObject(allStrings).get("categories");
//        for(int i=0; i < neoEntities.size();i++){
//            JSONObject neoJson = JSONObject.fromObject(neoEntities.getString(i));
//            String name = neoJson.getString("name");
//            String des = neoJson.getString("des");
//            NeoEntityVO neoEntityVO = new NeoEntityVO();
//            neoEntityVO.setNodeId((long) -1);
//            neoEntityVO.setName(name);
//            neoEntityVO.setDes(des);
//            neoEntityVO.setSymbol("circle");
//            neoEntityService.addNeoEntity(neoEntityVO);
//        }
//
//        for(int i=0;i<links.size();i++){
//            JSONObject relationJson = JSONObject.fromObject(links.getString(i));
//            String source = relationJson.getString("source");
//            String target = relationJson.getString("target");
//            String name = relationJson.getString("name");
//            String des = relationJson.getString("des");
//            String type = JSONObject.fromObject(relationJson.getString("lineStyle")).getString("type");
//            String[] symbol={"pin","arrow"};
//            NeoEntity fromEntity = neoEntityService.findByName(source);
//            NeoEntity toEntity = neoEntityService.findByName(target);
//            if(fromEntity == null || toEntity == null){
//                continue;
//            }
//            boolean isSolid = false;
//            if(type.equals("solid"))
//                isSolid = true;
//            neoEntityService.addIRelates(fromEntity.getId(),toEntity.getId(),isSolid,des,name,symbol);
//        }
//
//        for(int i=0; i < categories.size();i++){
//            JSONObject categoryJson = JSONObject.fromObject(categories.getString(i));
//            String name = categoryJson.getString("name");
//            String color=JSONObject.fromObject(categoryJson.getString("itemStyle")).getString("color");
//            neoEntityService.addCategory(name,color);
//        }
//
//
//        String test_result = JSON.toJSONString(neoEntityService.initAllEntities());
//        try{
//            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("test5_result.json")),"UTF-8");
//            JSONObject obj= JSONObject.fromObject(test_result);//创建JSONObject对象
//            osw.write(obj.toString());
//            osw.flush();
//            osw.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
