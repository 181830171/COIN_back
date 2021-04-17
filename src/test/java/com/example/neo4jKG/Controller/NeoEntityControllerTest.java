package com.example.neo4jKG.Controller;

import com.alibaba.fastjson.JSON;
import com.example.neo4jKG.Entity.Category;
import com.example.neo4jKG.Service.NeoEntityService;
import com.example.neo4jKG.VO.CategoryVO;
import com.example.neo4jKG.VO.ItemStyleVO;
import com.example.neo4jKG.VO.NeoEntityVO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.MultiValueMap;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NeoEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NeoEntityService neoEntityService;

    @Before
    public void clear(){
        neoEntityService.clearRepository();
    }

    // 增加
    @Test
    public void test1() {
        NeoEntityVO test1 = new NeoEntityVO();
        test1.setNodeId((long) -1);
        test1.setName("test1");
        String test1JsonString = JSON.toJSONString(test1);

        try{
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addNeoEntity")
                    .contentType(MediaType.APPLICATION_JSON).content(test1JsonString)).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 删除
    @Test
    public void test2(){
        try{
             MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete")
            .param("id", "22").contentType(MediaType.TEXT_HTML)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 查询
    @Test
    public void  test3(){
        try{
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get")
            .param("id","23").contentType(MediaType.TEXT_HTML)).andExpect(status().isOk()).andDo(print()).andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 修改
    @Test
    public void test4(){
        NeoEntityVO test4 = new NeoEntityVO();
        test4.setName("test_true");
        test4.setNodeId((long) 23);
        test4.setDes("test_des");
        String test4JsonString = JSON.toJSONString(test4);
        try{
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/update").contentType(MediaType.APPLICATION_JSON)
                    .content(test4JsonString)).andDo(print()).andExpect(status().isOk()).andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 添加关系
    @Test
    public void test5(){
        try{
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addRelates").contentType(MediaType.TEXT_HTML)
            .param("from","5").param("to","9").param("isSolid","true").param("des","destest").param("name","test").param("symbol","circle")).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 获取所有关系和实体列表
    @Test
    public void test6(){
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getListAll").contentType(MediaType.TEXT_HTML))
                    .andDo(print()).andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 增加类型
    @Test
    public void test7() {
        try{
            Map<String,String> params= new HashMap<String, String>();
            params.put("name","category1");
            params.put("color","#ee6666");
            String test7 = JSON.toJSONString(params);
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addCategory")
                    .contentType(MediaType.APPLICATION_JSON).content(test7)).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //修改类型
    @Test
    public void test8(){
        try{
            Map<String,Object> params= new HashMap<>();
            params.put("id",98);
            params.put("name","category1");
            params.put("color","#ee6666");
            String test8 = JSON.toJSONString(params);
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/updateCategory")
                    .contentType(MediaType.APPLICATION_JSON).content(test8)).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //修改关系虚实线
    @Test
    public void test9(){
        try{
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/updateRelType")
                    .contentType(MediaType.TEXT_HTML).param("id","2")
                    .param("type","solid")).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //修改关系两端形状
    @Test
    public void test10(){
        try{
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/updateRelSymbol")
                    .contentType(MediaType.TEXT_HTML).param("id","2")
                    .param("symbol", new String[]{"arrow", "pin"})).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //搜索，然后获取搜索记录
    @Test
    public void test11(){
        try{
            MvcResult mvcResult0 = mockMvc.perform(MockMvcRequestBuilders.post("/searchNodes")
                    .contentType(MediaType.TEXT_HTML).param("message","贾")).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult0.getResponse().getContentAsString(StandardCharsets.UTF_8));
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getSearchHistories")
                    .contentType(MediaType.TEXT_HTML)).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //登录
    @Test
    public void test12(){
        try {

            Map<String,Object> params= new HashMap<>();
            params.put("username","admin");
            params.put("password","123456");
            String test12 = JSON.toJSONString(params);
            MvcResult mvcResult0 = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                    .contentType(MediaType.APPLICATION_JSON).content(test12)).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_JSON).content(test12)).andDo(print()).andExpect(status().isOk())
                    .andReturn();
            System.out.println("Response:" + mvcResult0.getResponse().getContentAsString(StandardCharsets.UTF_8));
            System.out.println("Response:" + mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
