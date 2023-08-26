package com.dev.clock.countdown;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.dev.clock.clock.R;
import com.dev.clock.clock.SimpleDialog;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

//倒计时界面
public class CountdownActivity extends AppCompatActivity {
    TextView date_tv;

    private int countdown = 0;
    private Timer timer;
    private TimerTask timerTask;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        getSupportActionBar().setTitle("倒计时");

        date_tv = (TextView) findViewById(R.id.date_tv);
        //添加定时器，时间间隔为1秒
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(running&&countdown>0){
                    countdown--;
                    showTime();
                    if(countdown==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showTip();
                            }
                        });
                    }
                }
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    //倒计时开始
    public void start(View view){
        if(countdown==0){
            Toast.makeText(this,"请选择倒计时时间",Toast.LENGTH_SHORT).show();
        }else {
            running = true;
        }

    }

    //重置
    public void reset(View view){
        countdown = 0;
        running = false;
        date_tv.setText("选择时间");
        showTime();
    }

    //选择倒计时时间
    public void showTimePicker(View view) {
        if(countdown==0){
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.Theme_dialog,
                    timeSetListener,
                    0,
                    1, true);
            timePickerDialog.show();
        }
    }

    //倒计时结束提醒
    private void showTip(){
        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("倒计时提醒");
        dialog.setMessage("倒计时结束");
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    dialog.dismiss();
                    reset(null);
                }
            }
        });
    }

    //时间选择器监听
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            countdown = hourOfDay*60*60+minute*60;
            showTime();
        }
    };

    //格式化时间
    public void showTime() {
        if(countdown>=0){
            int h = countdown/60/60;
            int m = (countdown-h*3600)/60;
            int s = countdown-h*3600-m*60;
            String hStr = h < 10 ? ("0" + h) : h + "";
            String mStr = m < 10 ? ("0" + m) : m + "";
            String sStr = s < 10 ? ("0" + s) : s + "";
            date_tv.setText(hStr+":"+mStr+":"+sStr);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timerTask.cancel();
    }
}
