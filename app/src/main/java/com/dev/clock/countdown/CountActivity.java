package com.dev.clock.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.dev.clock.clock.R;
import com.dev.clock.clock.SimpleDialog;
import java.util.Timer;
import java.util.TimerTask;

public class CountActivity extends AppCompatActivity {
    TextView date_tv;

    private int countdown = 0;
    private Timer timer;
    private TimerTask timerTask;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        getSupportActionBar().setTitle("计时");

        date_tv = (TextView) findViewById(R.id.date_tv);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(running){
                    countdown++;
                    showTime();
                }
            }
        };
        // 从现在起过delay0毫秒后，每隔1000毫秒执行一次
        timer.schedule(timerTask,0,1000);
    }

    //计时开始
    public void start(View view){
        running = true;
    }

    //计时结束
    public void end(View view){
        if(countdown>0){
            showTip();

            running = false;
            countdown = 0;
            date_tv.setText("00:00:00");
            showTime();
        }
    }

    //计时结束提醒
    private void showTip(){
        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("计时提醒");
        dialog.setMessage("计时结束，计时时间为 "+getTime());
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    dialog.dismiss();
                }
            }
        });
    }

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

    //格式化时间
    public String getTime() {
        int h = countdown/60/60;
        int m = (countdown-h*3600)/60;
        int s = countdown-h*3600-m*60;
        String hStr = h < 10 ? ("0" + h) : h + "";
        String mStr = m < 10 ? ("0" + m) : m + "";
        String sStr = s < 10 ? ("0" + s) : s + "";
        return hStr+"时"+mStr+"分"+sStr+"秒";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消定时器
        timer.cancel();
        timerTask.cancel();
    }
}
