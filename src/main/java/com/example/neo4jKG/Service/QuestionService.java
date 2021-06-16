package com.example.neo4jKG.Service;

import com.example.neo4jKG.VO.ResponseVO;

public interface QuestionService {
    ResponseVO getAnswer(String question);
}
