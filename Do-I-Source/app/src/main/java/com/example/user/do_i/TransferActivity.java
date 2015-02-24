package com.example.user.do_i;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
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
    TextView transferAllow;
    int number = 3;
    int allowTime;
    final int longTrasferTime = 30;
    final int shortTransferTime = 20;

    //If transfer allow text hander, include notification
    Handler timeHandler = new Handler(){
        public void handleMessage(Message msg){
            long time = (SystemClock.elapsedRealtime() - ct.getBase()) / 1000;
            if(time<allowTime) {
                if(time == allowTime-5){
                    NotificationManager nm;
                    nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                    PendingIntent intent = PendingIntent.getActivity(
                            TransferActivity.this, 0,
                            new Intent(TransferActivity.this, TransferActivity.class), 0);


                    // Create Notification Object
                    Notification notification =
                            new Notification(R.drawable.ic_launcher,
                                    "Do-I", System.currentTimeMillis());
                    //vivrate
                    notification.defaults |= Notification.DEFAULT_VIBRATE;
                    //
                    notification.flags |=  Notification.FLAG_AUTO_CANCEL;
                    notification.setLatestEventInfo(TransferActivity.this, "DO-I", "환승까지 "+time+"초가 남았습니다.",intent);

                    nm.notify(1234, notification);
                    //Toast.makeText(TransferActivity.this, "Notification Registered.", Toast.LENGTH_SHORT).show();
                }
                transferAllow.setText("환승 가능!");
                transferAllow.setTextColor(Color.BLACK);
            }
            else {
                transferAllow.setText("환승 불가!");
                transferAllow.setTextColor(Color.RED);
                timeHandler.removeMessages(0);
            }
            timeHandler.sendEmptyMessageDelayed(0, 1000);
        };
    };


    protected void onCreate(Bundle savedINstanceState){
        super.onCreate(savedINstanceState);
        setContentView(R.layout.transfor);

        final TextView transferNumber = (TextView)findViewById(R.id.TextView1);
        transferAllow = (TextView)findViewById(R.id.TextView2);
        ct = (Chronometer) findViewById(R.id.chronmeter1);
        ct.setBase(SystemClock.elapsedRealtime());
        transferNumber.setText("환승 가능 횟수 : -");
        transferAllow.setText("환승 가능 여부 : - ");
        transferNumber.setTextColor(Color.BLACK);
        transferAllow.setTextColor(Color.BLACK);

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
                            transferNumber.setText("환승 가능 횟수 :  환승 불가");
                            transferNumber.setTextColor(Color.RED);
                            transferAllow.setText("환승 불가!");
                            transferAllow.setTextColor(Color.RED);
                        } else {
                            transferNumber.setText("환승 가능 횟수 : " + number);
                            transferNumber.setTextColor(Color.BLACK);
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
                        transferAllow.setText("환승 가능 여부 : - ");
                        transferAllow.setTextColor(Color.BLACK);
                        timeHandler.removeMessages(0);
                        number = 3;
                        transferNumber.setText("환승 가능 횟수 :  -");
                        transferNumber.setTextColor(Color.BLACK);
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
