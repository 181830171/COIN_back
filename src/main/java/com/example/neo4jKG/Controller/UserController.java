package com.example.neo4jKG.Controller;

import com.example.neo4jKG.Service.UserService;
import com.example.neo4jKG.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins ="*",maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping(path="/login",method = RequestMethod.POST)
    public ResponseVO login(@RequestBody Map<String,Object> params){
        return userService.login((String)params.get("username"),(String) params.get("password"));
    }

    //注册
    @RequestMapping(path="/register",method = RequestMethod.POST)
    public ResponseVO register(@RequestBody Map<String,Object> params){
        return userService.register((String)params.get("username"),(String) params.get("password"));
    }
}
