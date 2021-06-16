package com.example.neo4jKG.Controller;


import com.example.neo4jKG.Service.QuestionService;
import com.example.neo4jKG.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="*",maxAge = 3600)
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = "/getAnswer", method = RequestMethod.POST)
    public ResponseVO getAnswer(@RequestParam(name = "question") String question) {
        return questionService.getAnswer(question);
    }

}
