package com.mybatisPlusUtil.代码生成;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class NpsUtils {
        public static void main(String[] args) throws Exception {
            String url = "http://101.43.82.231:8080/login/verify";
            String username = "admin";
            String password = "XuBin2022@";

            // 设置 CookieManager，用于存储和管理 Cookie
            CookieHandler.setDefault(new CookieManager());

            // 构建 URL 对象，并打开连接
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            // 设置请求方法、请求头信息、发送 POST 数据
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            conn.setDoOutput(true);
            String postData = "username=" + username + "&password=" + password;
            conn.getOutputStream().write(postData.getBytes("UTF-8"));

            // 得到输入流，即从服务器获取响应数据
            InputStream inputStream = conn.getInputStream();

            // 将输入流转换为字符串
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            // 获取所有响应头字段
            Map<String, List<String>> headers = conn.getHeaderFields();

// 获取Set-Cookie响应头字段
            List<String> cookieList = headers.get("Set-Cookie");
            if (cookieList != null) {
                // 遍历Set-Cookie响应头字段，获取cookie值
                for (String cookie : cookieList) {
                    String[] cookieValues = cookie.split(";");
                    // 第一个元素就是cookie值，如：JSESSIONID=xxxxxxxxxxxxxxxxxxxxxxxxx
                    String cookieValue = cookieValues[0].trim();
                    // 使用cookie值进行接下来的请求操作
                }
            }

            // 关闭流
            inputStream.close();
            reader.close();

            // 获取 Set-Cookie 值
            String setCookie = conn.getHeaderField("Set-Cookie");
            System.out.println("Set-Cookie: " + setCookie);
        }
    }