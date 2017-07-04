package bbh.fzu.com.ybg.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import bbh.fzu.com.ybg.R;

/**
 * Created by MerickBao on 2017/7/4.
 * describe :
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }

    @Override
    public void onClick(View v) {

    }
}
