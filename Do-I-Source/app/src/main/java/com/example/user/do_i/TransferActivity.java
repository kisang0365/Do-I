package com.example.user.do_i;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by user on 15. 2. 7.
 */

public class TransferActivity extends Activity {

    Chronometer ct;
    TextView allowTransfer;
    int number = 3;
    int allowTime;
    final int longTrasferTime = 10;
    final int shortTransferTime = 5;


    //If transfer allow text hander
    Handler timeHandler = new Handler(){
        public void handleMessage(Message msg){
            long time = (SystemClock.elapsedRealtime() - ct.getBase()) / 1000;
            if(time<allowTime) allowTransfer.setText("환승 가능!");
            else {
                allowTransfer.setText("환승 불가!");
                timeHandler.removeMessages(0);
            }
            timeHandler.sendEmptyMessageDelayed(0, 1000);
        };
    };


    protected void onCreate(Bundle savedINstanceState){
        super.onCreate(savedINstanceState);
        setContentView(R.layout.transfor);

        final TextView text1 = (TextView)findViewById(R.id.TextView1);
        allowTransfer = (TextView)findViewById(R.id.TextView2);
        ct = (Chronometer) findViewById(R.id.chronmeter1);
        ct.setBase(SystemClock.elapsedRealtime());
        text1.setText("환승 가능 횟수 : -");
        allowTransfer.setText("환승 가능 여부 : - ");

        //Click do transfer Button
        findViewById(R.id.button0).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Calendar currentTime = Calendar.getInstance();
                        int Hour = currentTime.get(Calendar.HOUR_OF_DAY); //receive current Hour
                        if(Hour>=7 && Hour<=21) allowTime = shortTransferTime;
                        else allowTime = longTrasferTime;

                        timeHandler.removeMessages(0);  //remove timer handler
                        ct.setBase(SystemClock.elapsedRealtime());
                        ct.start();
                        if (number == 0) {
                            ct.stop();
                            text1.setText("환승 가능 횟수 :  환승 불가");
                            allowTransfer.setText("환승 불가!");
                        } else {
                            text1.setText("환승 가능 횟수 : " + number);
                            number--;
                            timeHandler.sendEmptyMessage(0);

                        }
                    }
                });

        //Init(초기화) button event
        findViewById(R.id.button1).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        ct.stop();
                        allowTransfer.setText("환승 가능 여부 : - ");
                        timeHandler.removeMessages(0);
                        number = 3;
                        text1.setText("환승 가능 횟수 :  -");
                        ct.setBase(SystemClock.elapsedRealtime());
                    }
                }
        );
        //back activity
        findViewById(R.id.button2).setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    //cancle is backgrounding
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
        }
        return true;
    }
}
