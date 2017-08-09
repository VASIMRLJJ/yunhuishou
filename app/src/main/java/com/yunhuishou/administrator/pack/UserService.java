package com.yunhuishou.administrator.pack;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个用于用户登录的工具类
 * Created by Administrator on 2017/8/9/009.
 */

public class UserService
{
    private static final String PATH = "http://39.108.158.194:8080/lss/UserServlet";
    //服务器访问地址

    /**
     * 验证用户登录是否合法
     * @param email 输入的邮箱
     * @param psw 输入的密码
     * @return 输入是否正确
     */
    public static boolean check(String email, String psw) throws SocketException
    {
        Map<String,String> params = new HashMap<>();
        params.put("userName",email);
        params.put("passWord",psw);
        try
        {
            return sendGETRequest(params, "UTF-8");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (SocketException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean sendGETRequest(Map<String,String> params,
                                          String encode)
            throws IOException
    {
        StringBuilder url = new StringBuilder(PATH);
        url.append("?");
        for(Map.Entry<String, String> entry:params.entrySet())
        {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(),encode));
            url.append("&");
        }
        url.deleteCharAt(url.length()-1);
        HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode()==200)
        {
            return true;
        }
        return false;
    }
}
