package com.example.neo4jKG.Driver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NeoEntityDriverTest {
    @Autowired
    private NeoEntityDriver neoEntityDriver;

    @Test
    public void test1(){
        neoEntityDriver.findRelatedNodes(132);
    }
}
