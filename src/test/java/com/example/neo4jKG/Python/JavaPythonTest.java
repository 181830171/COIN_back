package com.example.neo4jKG.Python;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaPythonTest {
    public static void main(String[] args) {
        try {
            //路径对应本地question_processor.py
            String[] args1 = new String[] { "python","D:\\CollegeStudy\\Third-Two\\software3\\harrypotter\\openkg-harry-potter\\OpenKG-Harry-Potter-main\\qa\\question_processor.py", "哈利·波特的祖父是？" };
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
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
