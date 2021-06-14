package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Dao.UserRepository;
import com.example.neo4jKG.Entity.User;
import com.example.neo4jKG.Service.UserService;
import com.example.neo4jKG.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private String USER_NOT_EXIST="请检查用户名是否输入正确！";
    private String PASSWORD_WRONG="请检查密码是否输入正确！";
    private String LOGIN_SUCCESS="成功登录！";
    private String USER_EXIST="用户名已存在！请更换";
    private String REGISTER_SUCCESS="注册成功！";

    @Override
    public ResponseVO login(String username, String password) {
        List<User> users=userRepository.findByUsername(username);
        if(users.size()==0){
            return ResponseVO.buildFailure(USER_NOT_EXIST);
        }else{
            User user=users.get(0);
            if(user.getPassword().equals(password)){
                return ResponseVO.buildSuccess(LOGIN_SUCCESS);
            }else{
                return ResponseVO.buildFailure(PASSWORD_WRONG);
            }
        }
    }

    @Override
    public ResponseVO register(String username,String password){
        List<User> users=userRepository.findByUsername(username);
        if(users.size()>0){
            return ResponseVO.buildFailure(USER_EXIST);
        }else{
            User user=new User();
            user.setId((long)-1);
            user.setUsername(username);
            user.setPassword(password);
            userRepository.save(user);
            System.out.println("成功注册账号"+username);
            return ResponseVO.buildSuccess(REGISTER_SUCCESS);
        }
    }
}
