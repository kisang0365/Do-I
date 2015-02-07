package com.example.user.do_i;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by user on 15. 2. 7.
 */
public class TransferActivity extends Activity {
    Button btn2;

    protected void onCreate(Bundle savedINstanceState){
        super.onCreate(savedINstanceState);
        setContentView(R.layout.transfor);

        final TextView text1 = (TextView)findViewById(R.id.TextView1);

        final Calendar c = Calendar.getInstance();
        int Hour = c.get(Calendar.HOUR_OF_DAY);
        int Minute = c.get(Calendar.MINUTE);
        int Second = c.get(Calendar.SECOND);
        int AmPm = c.get(Calendar.AM_PM);

        text1.setText(Hour +"시 " + Minute + "분 " + Second + "초");

        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                finish();
            }
        });
    }
}
