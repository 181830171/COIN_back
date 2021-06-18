package com.example.neo4jKG.Python;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaPythonTest {
    public static void main(String[] args) {
        try {
            //路径对应chatbot.py
            String path=".\\chatbot\\chatbot.py";
            String[] args1 = new String[] { "python",path, "哈利·波特的简介？" };
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

            proc.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            //记录答案行数
            int line_num=0;
            String line;
            while ((line = in.readLine()) != null) {
                line_num++;
                System.out.println(line);
            }
            if(line_num==1&&line.startsWith("*")){
                //如果答案有一行，且非提示行
                System.out.println("抱歉数据库中没有找到相关信息");
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
