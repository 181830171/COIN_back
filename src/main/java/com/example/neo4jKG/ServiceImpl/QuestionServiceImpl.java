package com.example.neo4jKG.ServiceImpl;

import com.example.neo4jKG.Service.QuestionService;
import com.example.neo4jKG.VO.ResponseVO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class QuestionServiceImpl implements QuestionService {

    /*
        智能问答，调用python程序，获取输出
     */
    @Override
    public ResponseVO getAnswer(String question) {
        try {
            //路径对应chatbot.py
            String path=System.getProperty("user.dir")+"\\chatbot\\chatbot.py";
            String[] args1 = new String[] { "python",path, question};
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件
            String result="";
            proc.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));

            int line_num=0;//记录答案行数
            String line;
            while ((line = in.readLine()) != null) {
                line_num++;
                System.out.println(line);
                result+=line+"\n";
            }
            if(line_num==1&&line.startsWith("*")){
                //如果答案仅有一行，且为提示行
                result="抱歉数据库中没有找到相关信息";
                System.out.println(result);
            }
            in.close();
            proc.waitFor();
            return ResponseVO.buildSuccess(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseVO.buildFailure("发生未知错误");
    }
}
