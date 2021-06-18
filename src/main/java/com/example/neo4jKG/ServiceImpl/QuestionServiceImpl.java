package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Service.QuestionService;
import com.example.neo4jKG.VO.ResponseVO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Override
    public ResponseVO getAnswer(String question) {
        String res = "";
        //TODO 根据问题获取结果
        try {
            //路径对应本地question_processor.py
            if(question==null){question="哈利·波特的祖父是谁？";}
            String path="D:\\CollegeStudy\\Third-Two\\software3\\harrypotter\\openkg-harry-potter\\OpenKG-Harry-Potter-main\\qa\\question_processor.py";
            String[] args1 = new String[] { "python",path,question };
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = "";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                res += line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseVO.buildSuccess(res);
    }
}
