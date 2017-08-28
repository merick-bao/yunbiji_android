package bbh.fzu.com.ybg.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bbh.fzu.com.ybg.Entity;
import bbh.fzu.com.ybg.R;
import bbh.fzu.com.ybg.Util.HttpUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;
import tech.michaelx.authcode.AuthCode;
import tech.michaelx.authcode.CodeConfig;

/**
 * Created by MerickBao on 2017/6/21.
 * describe : 注册界面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText getNickName;
    private String nickname;

    private EditText getAccount;
    private String username;

    private EditText getVerifyCode;
    private String verifycode;

    private EditText getPassWord;
    private String password;

    private EditText getPassWordVerify;
    private String passwordVerify;

    private Button sendVerifyCode;

    private Button finishRegister;

    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    //异步处理事件
    private Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    final int event = msg.arg1;
                    final int result = msg.arg2;
                    //final Object data = msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result == SMSSDK.RESULT_COMPLETE){
                                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                                    //right
                                    username = getAccount.getText().toString();
                                    password = getPassWord.getText().toString();
                                    nickname = getNickName.getText().toString();
                                    String url = "http://123.206.22.45:8080/Demo/register?username="+username+"&password="+password+"&nickname="+nickname;
                                    HttpUtil.sendHttpResquest(url, new okhttp3.Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {

                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            String data = response.body().string();
                                            Message message = new Message();
                                            message.what = 1;
                                            message.obj = data;
                                            handle.handleMessage(message);
                                        }
                                    });
                                    Toast.makeText(RegisterActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                                    //Log.d("888",data.toString());
                                }
                            }else {
                                Toast.makeText(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 1:
                    //Toast.makeText(RegisterActivity.this,"进入注册",Toast.LENGTH_SHORT).show();
                    final String responseData = msg.obj.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                String code = jsonObject.getString("code");
                                String describe = jsonObject.getString("describe");
                                progressDialog.cancel();
                                if (code.equals("202")){
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Intent loginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                                    loginActivity.putExtra("token", Entity.ACTIVITY_REGISTER);
                                    loginActivity.putExtra("username",getAccount.getText().toString());
                                    loginActivity.putExtra("password",getPassWord.getText().toString());
                                    startActivity(loginActivity);
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"注册失败,该手机号已被注册"+describe,Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_register);

        inits();
        initSMSSDK();
    }

    private void initSMSSDK() {
        //初始化短信验证模块
        SMSSDK.initSDK(this,"1df128cbcbd61","40018dab2b1b42d0e68c63a8429d05e5");
        EventHandler eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                //回调handle处理结果
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = 0;
                handle.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eh);
    }


    private void inits() {
        //初始化控件
        getNickName = (EditText) findViewById(R.id.register_get_nickname);
        getAccount = (EditText) findViewById(R.id.register_get_account);
        getVerifyCode = (EditText) findViewById(R.id.register_get_verify_code);
        sendVerifyCode = (Button) findViewById(R.id.register_send_verify_code);
        getPassWord = (EditText) findViewById(R.id.register_get_password);
        getPassWordVerify = (EditText) findViewById(R.id.register_get_password_verify);
        finishRegister = (Button) findViewById(R.id.register_finish_button);

        finishRegister.setOnClickListener(this);
        sendVerifyCode.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {//点击事件处理
        switch (v.getId()){
            case R.id.register_send_verify_code:
                //发送验证码
                //sentVerifyCode();
                break;

            case R.id.register_finish_button:
                //进行注册验证
               // checkRegister();
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("正在注册...");

                username = getAccount.getText().toString();
                password = getPassWord.getText().toString();
                nickname = getNickName.getText().toString();
                String passwordVerify = getPassWordVerify.getText().toString();
                if (!password.equals("") && password.equals(passwordVerify)){
                    progressDialog.show();
                    String url = "http://123.206.22.45:8080/Demo/register?username="+username+"&password="+password+"&nickname="+nickname;
                    HttpUtil.sendHttpResquest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data = response.body().string();
                            Message message = new Message();
                            message.what = 1;
                            message.obj = data;
                            handle.handleMessage(message);
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }

    }

    private void sentVerifyCode() {//发送验证码
        username = getAccount.getText().toString().trim();
        if (username.length()==11){
            SMSSDK.getVerificationCode("86",username);
            Toast.makeText(this,"验证码已发送，请注意查收",Toast.LENGTH_SHORT).show();
            CodeConfig codeConfig = new CodeConfig.Builder()
                    .codeLength(4)
                    .smsFromStart(106)
                    .smsBodyStartWith("基佬")
                    .smsBodyContains("请将这个验证码发给很帅的斌虎")
                    .build();
            AuthCode.getInstance().with(this).config(codeConfig).into(getVerifyCode);
            Toast.makeText(this,"验证码已发送，请注意查收",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"请正确输入手机号",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkRegister() {//进行注册验证

        verifycode = getVerifyCode.getText().toString().trim();
        username = getAccount.getText().toString().trim();
        nickname = getNickName.getText().toString();
        password = getPassWord.getText().toString();
        passwordVerify = getPassWordVerify.getText().toString();

        if (!verifycode.equals("") && !password.equals("") && password.equals(passwordVerify) && !nickname.equals("")){
            //进行验证码验证
            SMSSDK.submitVerificationCode("86",username,verifycode);
            Toast.makeText(this,"请等待验证",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"请完整输入",Toast.LENGTH_SHORT).show();
        }
    }

}
