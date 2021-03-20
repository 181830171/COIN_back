package com.example.neo4jKG.Controller;

import com.alibaba.fastjson.JSON;
import com.example.neo4jKG.Service.NeoEntityService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
            .param("from","5").param("to","9").param("isSolid","true").param("des","destest").param("name","test")).andDo(print()).andExpect(status().isOk())
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

}
