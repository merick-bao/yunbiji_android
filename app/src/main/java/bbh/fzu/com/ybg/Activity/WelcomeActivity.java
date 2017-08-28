package bbh.fzu.com.ybg.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import bbh.fzu.com.ybg.Entity;
import bbh.fzu.com.ybg.R;

/**
 * Created by MerickBao on 2017/8/26.
 * describe :欢迎页
 */

public class WelcomeActivity extends BaseActivity{

    private ImageView welcome;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            goLogin();
        }
    };

    private void goLogin() {
        Intent loginActivity = new Intent(WelcomeActivity.this,LoginActivity.class);
        loginActivity.putExtra("token", Entity.ACTIVITY_WELCOME);
        startActivity(loginActivity);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome = (ImageView) findViewById(R.id.welcome);
        Glide.with(this)
                .load("http://api.dujin.org/bing/1366.php")
                .into(welcome);
        handler.sendEmptyMessageDelayed(0,2000);
    }
}
