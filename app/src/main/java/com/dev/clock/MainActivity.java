package com.dev.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.dev.clock.clock.R;
import com.dev.clock.clock.SetClockActivity;
import com.dev.clock.countdown.CountActivity;
import com.dev.clock.countdown.CountdownActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //打开设置闹钟界面
    public void goClock(View view) {startActivity(new Intent(this, SetClockActivity.class));}

    //倒计时界面
    public void goCountdown(View view) {
        startActivity(new Intent(this, CountdownActivity.class));
    }

    //计时界面
    public void goTimer(View view) {
        startActivity(new Intent(this, CountActivity.class));
    }

}
