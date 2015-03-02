package com.example.user.do_i;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by user on 15. 2. 18.
 */
public class TaxiActivity extends Activity {

    private NaverHandleXML naverObj;
    private DaumHandleXML daumObj;
    final private String naverKey = "2d565f2b0bb07770f27d65f1291f28f5";
    final private String daumKey = "a9aeb6c38f87fbe4444deffc7b3b7b0f";


    protected void onCreate(Bundle savedINstanceState) {
        super.onCreate(savedINstanceState);
        setContentView(R.layout.activity_taxi);
        final EditText start = (EditText) findViewById(R.id.start);
        final EditText end = (EditText) findViewById(R.id.end);

        Button taxi = (Button) findViewById(R.id.taxi);
        taxi.setText("택시 요금 측정");

        Button fastroad = (Button) findViewById(R.id.fastroad);
        fastroad.setText("빠른 길 찾기");

        taxi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String startNX, startNY, startDX, startDY;  //naver x,y
                String endNX, endNY, endDX, endDY;          //daum x,y

                String query = null;        //search query
                try {
                    query = URLEncoder.encode(start.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                //naver StartPoint parssing
                String queryURL = "http://openapi.naver.com/search?"
                        + "key="+ naverKey
                        + "&query=" + query
                        + "&target=local&start=1&display=1";

                TextView status = (TextView) findViewById(R.id.view);
                naverObj = new NaverHandleXML(queryURL);
                naverObj.fetchXML();
                while (naverObj.parsingComplete) {
                   //status.setText("파싱중...");
                }
                startNX = naverObj.getMapx();
                startNY = naverObj.getMapy();

                //daum StartPoint parssing
                queryURL = "https://apis.daum.net/local/geo/transcoord?"
                        +"apikey=" + daumKey
                        +"&x="+startNX + "&y=" + startNY
                        +"&fromCoord=KTM&toCoord=WGS84&output=xml";

                daumObj = new DaumHandleXML(queryURL);

                daumObj.fetchXML();

                while (daumObj.parsingComplete) {
                    //status.setText("파싱중...");
                }

                startDX = daumObj.getMapx();
                startDY = daumObj.getMapy();

                //end search query
                try {
                    query = URLEncoder.encode(end.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                //naver EndPoint parssing
                queryURL = "http://openapi.naver.com/search?"
                        + "key=2d565f2b0bb07770f27d65f1291f28f5"
                        + "&query=" + query //여기는 쿼리를 넣으세요(검색어)
                        + "&target=local&start=1&display=1";


                naverObj.setNaverUrl(queryURL);
                naverObj.fetchXML();
                while (naverObj.parsingComplete) {
                    //status.setText("파싱중...");
                }
                endNX = naverObj.getMapx();
                endNY = naverObj.getMapy();

                //daum EndPoint parssing
                queryURL = "https://apis.daum.net/local/geo/transcoord?"
                        +"apikey=" + daumKey
                        +"&x="+endNX + "&y=" + endNY
                        +"&fromCoord=KTM&toCoord=WGS84&output=xml";

                daumObj.setDaumUrl(queryURL);

                daumObj.fetchXML();

                while (daumObj.parsingComplete) {
                    //status.setText("파싱중...");
                }

                endDX = daumObj.getMapx();
                endDY = daumObj.getMapy();
                status.setText("");


                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("daummaps://route?sp="
                        + startDY + "," + startDX + "&ep=" + endDY + "," + endDX + "&by=CAR"));

                        startActivity(intent);
            }

        });


        fastroad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String startNX, startNY, startDX, startDY;  //naver x,y
                String endNX, endNY, endDX, endDY;          //daum x,y

                String query = null;        //search query
                try {
                    query = URLEncoder.encode(start.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                //naver StartPoint parssing
                String queryURL = "http://openapi.naver.com/search?"
                        + "key="+ naverKey
                        + "&query=" + query
                        + "&target=local&start=1&display=1";

                TextView status = (TextView) findViewById(R.id.view);
                naverObj = new NaverHandleXML(queryURL);
                naverObj.fetchXML();
                while (naverObj.parsingComplete) {
                    //status.setText("파싱중...");
                }
                startNX = naverObj.getMapx();
                startNY = naverObj.getMapy();

                //daum StartPoint parssing
                queryURL = "https://apis.daum.net/local/geo/transcoord?"
                        +"apikey=" + daumKey
                        +"&x="+startNX + "&y=" + startNY
                        +"&fromCoord=KTM&toCoord=WGS84&output=xml";

                daumObj = new DaumHandleXML(queryURL);

                daumObj.fetchXML();

                while (daumObj.parsingComplete) {
                    //status.setText("파싱중...");
                }

                startDX = daumObj.getMapx();
                startDY = daumObj.getMapy();

                //end search query
                try {
                    query = URLEncoder.encode(end.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                //naver EndPoint parssing
                queryURL = "http://openapi.naver.com/search?"
                        + "key=2d565f2b0bb07770f27d65f1291f28f5"
                        + "&query=" + query //여기는 쿼리를 넣으세요(검색어)
                        + "&target=local&start=1&display=1";


                naverObj.setNaverUrl(queryURL);
                naverObj.fetchXML();
                while (naverObj.parsingComplete) {
                    //status.setText("파싱중...");
                }
                endNX = naverObj.getMapx();
                endNY = naverObj.getMapy();

                //daum EndPoint parssing
                queryURL = "https://apis.daum.net/local/geo/transcoord?"
                        +"apikey=" + daumKey
                        +"&x="+endNX + "&y=" + endNY
                        +"&fromCoord=KTM&toCoord=WGS84&output=xml";

                daumObj.setDaumUrl(queryURL);

                daumObj.fetchXML();

                while (daumObj.parsingComplete) {
                    //status.setText("파싱중...");
                }

                endDX = daumObj.getMapx();
                endDY = daumObj.getMapy();
                status.setText("");


                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("daummaps://route?sp="
                        + startDY + "," + startDX + "&ep=" + endDY + "," + endDX + "&by=PUBLICTRANSIT"));

                startActivity(intent);
            }

        });
    }
}