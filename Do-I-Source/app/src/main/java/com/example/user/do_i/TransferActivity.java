package com.example.user.do_i;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * Created by user on 15. 2. 7.
 */

public class TransferActivity extends Activity {

    Button btn2;
    Chronometer ct;
    int number = 3;
    TextView allowTransfer;

    //If transfer allow text hander
    Handler timeHandler = new Handler(){
        public void handleMessage(Message msg){
            long time = (SystemClock.elapsedRealtime() - ct.getBase()) / 1000;
            if(time<10) allowTransfer.setText("환승 가능!");
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

/*
        final Calendar currentTime = Calendar.getInstance();
        int Hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int Minute = currentTime.get(Calendar.MINUTE);
        int Second = currentTime.get(Calendar.SECOND);
        int AmPm = currentTime.get(Calendar.AM_PM);

        text2.setText(Hour +"시 " + Minute + "분 " + Second + "초");
*/
        final TextView text1 = (TextView)findViewById(R.id.TextView1);
        allowTransfer = (TextView)findViewById(R.id.TextView2);
        ct = (Chronometer) findViewById(R.id.chronmeter1);
        ct.setBase(SystemClock.elapsedRealtime());
        text1.setText("환승 가능 횟수 : -");
        allowTransfer.setText("환승 가능 여부 : - ");

        //time = (SystemClock.elapsedRealtime() - ct.getBase()) / 1000;
        //text2.setText("" + time);

        //Allow transfer confirm Handler




        findViewById(R.id.button0).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        ct.setBase(SystemClock.elapsedRealtime());
                        ct.start();
                        if (number == 0) {
                            ct.stop();
                            text1.setText("환승 가능 횟수 :  환승 불가");
                        } else {
                            text1.setText("환승 가능 횟수 : " + number);
                            number--;
                            timeHandler.sendEmptyMessage(0);

                        }
                    }
                });


        findViewById(R.id.button1).setOnClickListener(      //Init(초기화) button event
                new Button.OnClickListener(){
                    public void onClick(View v){
                        timeHandler.removeMessages(0);
                        number = 3;
                        text1.setText("환승 가능 횟수 :  -");
                        ct.setBase(SystemClock.elapsedRealtime());
                    }
                }
        );

        btn2 = (Button) findViewById(R.id.button2);     //back activity
        btn2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                finish();
            }
        });
    }
}
