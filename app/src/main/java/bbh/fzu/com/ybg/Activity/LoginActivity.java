package bbh.fzu.com.ybg.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bbh.fzu.com.ybg.Entity;
import bbh.fzu.com.ybg.R;
import bbh.fzu.com.ybg.Util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by MerickBao on 2017/6/21.
 * describe : 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button login;

    private TextView register;

    private EditText getUsername;

    private EditText getPassword;

    private ProgressDialog progressDialog;

    private String username;
    private String password;

    //保存密码所用到的控件
    private CheckBox savePassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int token;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what){
                case 1:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                progressDialog.cancel();
                                JSONObject jsonObject = new JSONObject(msg.obj.toString());
                                String code = jsonObject.getString("code");
                                if (code.equals("202")){

                                    if (savePassword.isChecked()){
                                        //保存账号密码
                                        //editor = preferences.edit();
                                        String username = getUsername.getText().toString();
                                        String password = getPassword.getText().toString();
                                        editor.putBoolean("isSaved",true);
                                        editor.putString("username",username);
                                        editor.putString("password",password);
                                    }else {
                                        editor.clear();
                                    }
                                    editor.apply();

                                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                    Intent main = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(main);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                }
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
        setContentView(R.layout.activity_login);

        Intent preIntent = getIntent();
        token = preIntent.getIntExtra("token",0);
        username = preIntent.getStringExtra("username");
        password = preIntent.getStringExtra("password");
        inits();
    }

    private void inits() {//初始化各控件

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);

        register = (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(this);

        getUsername = (EditText) findViewById(R.id.login_get_account);
        getPassword = (EditText) findViewById(R.id.login_get_password);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();//必须要在主线程初始化
        savePassword = (CheckBox) findViewById(R.id.login_remember_password);

        //若果有保存的账号密码
        boolean isSaved = preferences.getBoolean("isSaved",false);
        if (token == Entity.ACTIVITY_REGISTER){
            getUsername.setText(username);
            getPassword.setText(password);
        }else{
            if (isSaved){
                username = preferences.getString("username","");
                password = preferences.getString("password","");
                getUsername.setText(username);
                getPassword.setText(password);

                if (token == Entity.ACTIVITY_WELCOME){
                    checkLogin();
                }
            }

        }
    }

    @Override
    public void onClick(View v) {//点击事件
        switch (v.getId()){
            case R.id.login_button:
                checkLogin();
                break;

            case R.id.login_register:
                Intent intent2 = new Intent(this,RegisterActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void checkLogin() {//进行登录验证

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登陆...");
        progressDialog.show();

        String username = getUsername.getText().toString();
        String password = getPassword.getText().toString();
        //Toast.makeText(LoginActivity.this,username+password,Toast.LENGTH_SHORT).show();

        String url = "http://123.206.22.45:8080/Demo/login?"+"username="+username+"&password="+password;

        HttpUtil.sendHttpResquest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = result;
                handler.handleMessage(message);
            }
        });
        Log.d("login",username+password);
    }
}
