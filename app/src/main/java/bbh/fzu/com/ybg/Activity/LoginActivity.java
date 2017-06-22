package bbh.fzu.com.ybg.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bbh.fzu.com.ybg.R;

/**
 * Created by MerickBao on 2017/6/21.
 * describe : 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button login;

    private TextView register;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inits();
    }

    private void inits() {

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);

        register = (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                ActivityCollector.finishAll();
                break;

            case R.id.login_register:
                Intent intent2 = new Intent(this,RegisterActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

}
