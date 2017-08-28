package bbh.fzu.com.ybg.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bbh.fzu.com.ybg.R;
import bbh.fzu.com.ybg.Util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by MerickBao on 2017/8/25.
 * describe :
 */

public class JsonTestActivity extends BaseActivity{

    TextView textView;
    Button button;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what){
                case 1:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s = msg.obj.toString();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String code = jsonObject.getString("code");
                                textView.setText(s+code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_test);

        textView = (TextView) findViewById(R.id.json);


        button = (Button) findViewById(R.id.json_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(JsonTestActivity.this,"666",Toast.LENGTH_SHORT).show();

                HttpUtil.sendHttpResquest("http://192.168.17.1:8080/Demo/login?username=15659896330&password=123456", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("JSON","fail");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Message message = new Message();
                        message.what = 1;
                        message.obj = data;
                        handler.handleMessage(message);
                        Log.d("JSON",data);
                    }
                });
            }
        });
    }
}
