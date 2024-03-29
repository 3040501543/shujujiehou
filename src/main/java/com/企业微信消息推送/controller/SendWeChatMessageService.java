package com.企业微信消息推送.controller;

import com.alibaba.fastjson.JSONObject;
import com.企业微信消息推送.util.PingUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**发送企业微信消息
 * @author shihy
 *
 */
@Component
public class SendWeChatMessageService {
    //发送消息的类型
    private final static String MSGTYPE = "text";
    //将消息发送给所有成员
    private final static String TOPARTY = "@all";
    //获取企业微信的企业号，根据不同企业更改
/*
    private  static String CORPID = "wwb7515777a7d110c0";
*/
    private  static String CORPID = "wwb48ede664bc81f31";

//    private  static String CORPID;
    //获取企业应用的密钥，根据不同应用更改
/*
    private  static String CORPSECRET = "HPnnp24NnGN78HWMGJYwHxgk5aUoXX73we2NR0RdCz4";

*/
    private  static String CORPSECRET = "CVsnCM0wwt6WgiR7_gNJm-0NIkBBn9rVXK2nhZZ4aLQ";
//    private  static String CORPSECRET;
    //获取访问权限码URL
    private final static String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    //创建会话请求URL
    private final static String CREATE_SESSION_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
    //应用Id
/*
    private  static String AGENT_ID="1000002";
*/
    private  static String AGENT_ID;
    //获取access_token
    public static AccessToken getAccessToken() {

        AccessToken token = new AccessToken();

        //访问微信服务器
        String url = ACCESS_TOKEN_URL + "?corpid=" + CORPID + "&corpsecret=" + CORPSECRET;
        try {
            URL getUrl = new URL(url);
            //开启连接，并返回一个URLConnection对象
            HttpURLConnection http = (HttpURLConnection)getUrl.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            //将URL连接用于输入和输出，一般输入默认为true，输出默认为false
            http.setDoOutput(true);
            http.setDoInput(true);
            //进行连接，不返回对象
            http.connect();

            //获得输入内容,并将其存储在缓存区
            InputStream inputStream = http.getInputStream();
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            //将内容转化为JSON代码
            String message = new String(buffer,"UTF-8");
            JSONObject json = JSONObject.parseObject(message);
            //提取内容，放入对象
            token.setAccess_token(json.getString("access_token"));
            token.setExpires_in(new Integer(json.getString("expires_in")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回access_token码
        return token;
    }

    /**
     * 企业接口向下属关注用户发送给微信消息
     *
     * @param toUser 成员ID列表
     * @param toParty 部门ID列表
     * @param toTag 标签ID列表
     * @param content 消息内容
     * @param safe 是否保密
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public String sendWeChatMessage(String toUser, String toParty, String toTag, String content,String safe,
                                    String corpId,String corpSecret,String agentId) {
        //给企业编号、应用编号、应用秘钥赋值
        CORPID=corpId;
        CORPSECRET=corpSecret;
        AGENT_ID=agentId;

        //从对象中提取凭证
        AccessToken accessToken = getAccessToken();
        String ACCESS_TOKEN = accessToken.getAccess_token();
        //请求串
        String url = CREATE_SESSION_URL + ACCESS_TOKEN;

        //封装发送消息请求JSON
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append("\"touser\":" + "\"" + toUser + "\",");
        stringBuffer.append("\"toparty\":" + "\"" + toParty + "\",");
        stringBuffer.append("\"totag\":" + "\"" + toTag + "\",");
        stringBuffer.append("\"msgtype\":" + "\"" + MSGTYPE + "\",");
        stringBuffer.append("\"text\":" + "{");
        stringBuffer.append("\"content\":" + "\"" + content + "\"");
        stringBuffer.append("}");
        stringBuffer.append(",\"safe\":" + "\"" + safe + "\",");
        stringBuffer.append("\"agentid\":" + "\"" + AGENT_ID + "\",");
        stringBuffer.append("\"debug\":" + "\"" + "1" + "\"");
        stringBuffer.append("}");
        String json = stringBuffer.toString();
        System.out.println(json);

        try {
            URL postUrl = new URL(url);
            HttpURLConnection http = (HttpURLConnection) postUrl.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            // 连接超时30秒
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            // 读取超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();

            //写入内容
            OutputStream outputStream = http.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));
            InputStream inputStream = http.getInputStream();
            int size = inputStream.available();
            byte[] jsonBytes = new byte[size];
            inputStream.read(jsonBytes);
            String result = new String(jsonBytes, "UTF-8");
            System.out.println("请求返回结果:" + result);
            //清空输出流
            outputStream.flush();
            //关闭输出通道
            outputStream.close();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "发送异常";
    }
/*    public static void main(String[] args) {
        SendWeChatMessage weChat = new SendWeChatMessage();
        weChat.sendWeChatMessage("ShiHuyi", "1", "", "天干物燥。","0");
    }*/


    public static void main(String[] args)throws Exception {

//        private  static String CORPID = "wwb48ede664bc81f31";
//        private  static String CORPSECRET = "CVsnCM0wwt6WgiR7_gNJm-0NIkBBn9rVXK2nhZZ4aLQ";
        String ip = "123.03.230.1";
//        String ip = "10.103.110.101";
        boolean isConnect = PingUtil.isReachable(ip);
        System.out.println(isConnect);
        String toUser = "ZhangSan";

        if (!isConnect){
            String  json="{\"agentId\":\"1000002\",\"content\":\"这个ip地址不通了,赶紧处理："+ip+"\",\"corpId\":\"wwb48ede664bc81f31\",\"corpSecret\":\"CVsnCM0wwt6WgiR7_gNJm-0NIkBBn9rVXK2nhZZ4aLQ\",\"safe\":\"0\",\"toParty\":\"15\",\"toTag\":\"\",\"toUser\":\""+toUser+"\"}\n";
            WechatVo vo=JSONObject.parseObject(json,WechatVo.class);
            SendWeChatMessageService service = new SendWeChatMessageService();
            service.sendWeChatMessage(vo.getToUser(),vo.getToParty(),null,vo.getContent(),vo.getSafe(),vo.getCorpId(),vo.getCorpSecret(),vo.getAgentId());
        }



    }

}
