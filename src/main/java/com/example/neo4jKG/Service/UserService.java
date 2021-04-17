package com.example.neo4jKG.Service;

import com.example.neo4jKG.VO.ResponseVO;

public interface UserService {
    ResponseVO login(String username,String password);

    ResponseVO register(String username,String password);
}
