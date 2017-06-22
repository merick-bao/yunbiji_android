package bbh.fzu.com.ybg.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bbh.fzu.com.ybg.R;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by MerickBao on 2017/6/21.
 * describe : 注册界面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText getNickName;
    private String NickName;

    private EditText getAccount;
    private String Account;

    private EditText getVerifyCode;
    private String VerifyCode;

    private EditText getPassWord;
    private String PassWord;

    private EditText getGetPassWordVerify;
    private String PassWordVerify;

    private Button sendVerifyCode;

    private Button finishRegister;

    //异步处理事件
    private Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE){
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    //right
                    Toast.makeText(RegisterActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                    Log.d("888",data.toString());
                }
            }else {
                Toast.makeText(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
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
                handle.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eh);
    }

    private void inits() {

        getNickName = (EditText) findViewById(R.id.register_get_nickname);
        getAccount = (EditText) findViewById(R.id.register_get_account);
        getVerifyCode = (EditText) findViewById(R.id.register_get_verify_code);
        sendVerifyCode = (Button) findViewById(R.id.register_send_verify_code);
        getPassWord = (EditText) findViewById(R.id.register_get_password);
        getGetPassWordVerify = (EditText) findViewById(R.id.register_get_password_verify);
        finishRegister = (Button) findViewById(R.id.register_finish_button);
        finishRegister.setOnClickListener(this);
        sendVerifyCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_send_verify_code:
                //发送验证码
                Account = getAccount.getText().toString().trim();
                SMSSDK.getVerificationCode("86",Account);
                Toast.makeText(this,"验证码已发送，请注意查收",Toast.LENGTH_SHORT).show();
                break;

            case R.id.register_finish_button:
                //进行验证
                VerifyCode = getVerifyCode.getText().toString().trim();
                Account = getAccount.getText().toString().trim();
                SMSSDK.submitVerificationCode("86",Account,VerifyCode);
                Toast.makeText(this,"请等待验证",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

    }

}
