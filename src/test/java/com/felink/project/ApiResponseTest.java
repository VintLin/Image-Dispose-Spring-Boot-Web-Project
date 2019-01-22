package com.felink.project;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class ApiResponseTest {
    public int send(String url, String filePath) throws IOException {

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return -1;
        }

        /**
         * 第一部分
         */
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        /**
         * 设置关键值
         */
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);

        // 请求正文信息

        // 第一部分：

        StringBuilder sb = new StringBuilder();
        sb.append("--"); // ////////必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\nContent-Disposition: form-data;name=\"file\";filename=\"");
        sb.append(file.getName());
        sb.append("\"\r\nContent-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流

        OutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(head);

        // 文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();


        StringBuilder sb1 = new StringBuilder();
        sb1.append("--"); // ////////必须多两道线
        sb1.append(BOUNDARY);
        sb1.append("\r\nContent-Disposition: form-data;name=\"text\"\r\n\r\n");
//        sb1.append("Content-Type:application/json\r\n");
        sb1.append("x");
        byte[] head1 = sb1.toString().getBytes("utf-8");
        out.write(head1);

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);

        out.flush();
        out.close();

        /**
         * 读取服务器响应，必须读取,否则提交不成功
         */

//        return con.getResponseCode();

        /**
         * 下面的方式读取也是可以的
         */

        try {
         // 定义BufferedReader输入流来读取URL的响应
             BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
             String line = null;
             while ((line = reader.readLine()) != null) {
                System.out.println(line);
             }
        } catch (Exception e) {
             System.out.println("发送POST请求出现异常！" + e);
             e.printStackTrace();
        }
        return 200;
    }

    public static void testApiResponse(String[] args) throws IOException {
        ApiResponseTest up = new ApiResponseTest();
        System.out.println(up.send("http://localhost:8080/api/v0/dynamic",
                "E:\\Python-Package\\pyutility\\src\\tests\\codes\\0628.jpeg"));
    }
}



