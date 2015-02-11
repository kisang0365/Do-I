package com.example.user.do_i;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by user on 15. 2. 7.
 */
public class TransferActivity extends Activity {
    Button btn2;
    Chronometer ct;
    TextView allowView;


    protected void onCreate(Bundle savedINstanceState){
        super.onCreate(savedINstanceState);
        setContentView(R.layout.transfor);

        final TextView text1 = (TextView)findViewById(R.id.TextView1);

        final Calendar currentTime = Calendar.getInstance();
        int Hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int Minute = currentTime.get(Calendar.MINUTE);
        int Second = currentTime.get(Calendar.SECOND);
        int AmPm = currentTime.get(Calendar.AM_PM);

        //long currentsec = System.currentTimeMillis()/1000;

        text1.setText(Hour +"시 " + Minute + "분 " + Second + "초");

        ct = (Chronometer) findViewById(R.id.chronmeter1);
        allowView = (TextView) findViewById(R.id.textView2);

        ct.setBase(SystemClock.elapsedRealtime());
        ct.start();
        findViewById(R.id.button1).setOnClickListener(      //Init(초기화) button event
                new Button.OnClickListener(){
                    public void onClick(View v){
                        ct.setBase(SystemClock.elapsedRealtime());
                    }
                }
        );
/*
        ct.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            public void onChronometerTick(Chronometer chronometer){
                long elapsedMillis = SystemClock.elapsedRealtime() - ct.getBase();
                DecimalFormat numFormatter = new DecimalFormat("###,###");
                String output = numFormatter.format(elapsedMillis);
                miliTextView.setText("Total MiliSec : " + output);
            }
        });
*/
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                finish();
            }
        });
    }
}
