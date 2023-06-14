package com.企业微信消息推送.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingUtil {

    public static void main(String[] args) throws IOException {
//        String ip = "127.0.0.1";
        String ip = "123.03.230.1";
//        String ip = "10.103.110.101";
        boolean isConnect = PingUtil.isReachable(ip);
        System.out.println(isConnect);


    }

        public static boolean isReachable(String ip) {
            try {
                InetAddress address = InetAddress.getByName(ip);
                return address.isReachable(5000); // 5 秒超时
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }



}
