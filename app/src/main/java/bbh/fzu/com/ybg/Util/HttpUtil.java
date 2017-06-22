package bbh.fzu.com.ybg.Util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by pc on 2017/6/1.
 */

public class HttpUtil {

    //发送网络请求
    public static void sendHttpResquest(String address,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        //回调callback函数
        client.newCall(request).enqueue(callback);
    }
}
