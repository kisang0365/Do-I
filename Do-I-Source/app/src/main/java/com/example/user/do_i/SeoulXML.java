package com.example.user.do_i;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 15. 2. 27.
 */
public class SeoulXML {

    private String time = null;
    private String firstname = null;
    private String lastname = null;
    private String urlString = null;
    private String item = null;
    private String line = null;
    private String fr_code = null;
    boolean inFirstName = false, inLastName = false, inTime = false, inLine = false, infr_code= false;
    private XmlPullParserFactory xmlFactoryObject;
    public boolean parsingComplete = true;
    public SeoulXML(String url){
        this.urlString = url;
    }
    public void setSeoulUrl(String url){
        this.urlString = url;
        this.parsingComplete = true;
    }

    public String getFirstName(){
        return firstname;
    }
    public String getLastName(){
        return lastname;
    }
    public String getTime(){
        return time;
    }
    public String getItem(){
        return item;
    }
    public String getline(){
        return line;
    }
    public String getfr_code(){
        return fr_code;
    }


    public void parseXMLLastTime(XmlPullParser myParser) {
        int event;
        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                //String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(myParser.getName().equals("STATION_NM")){
                            inFirstName = true;
                        }
                        if(myParser.getName().equals("SUBWAYENAME")){
                            inLastName = true;
                        }
                        if(myParser.getName().equals("LEFTTIME")){
                            inTime = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(inFirstName){ //isMapx이 true일 때 태그의 내용을 저장.
                            firstname = myParser.getText();
                            inFirstName = false;
                        }
                        if(inLastName){ //isMapy이 true일 때 태그의 내용을 저장.
                            lastname = myParser.getText();
                            inLastName = false;
                        }
                        if(inTime){ //isMapy이 true일 때 태그의 내용을 저장.
                            time = myParser.getText();
                            item = item + "출발역 : " +firstname+ "\n종착역 : "+lastname+ "\n시간 : " + time +"\n";
                            inTime = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:

                        break;
                }
                event = myParser.next();

            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseXMLStationCode(XmlPullParser myParser) {
        int event;
        String check =""+2;
        line = null;
        fr_code = null;
        item = null;
        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                //String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(myParser.getName().equals("LINE_NUM")){ //역의 호선 받기.
                            inLine= true;
                        }
                        if(myParser.getName().equals("FR_CODE")){ //mapy 만나면 내용을 받을수 있게 하자
                            infr_code = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(inLine){ //isMapx이 true일 때 태그의 내용을 저장.
                            line = myParser.getText();
                            inLine = false;
                        }
                        if(infr_code){ //isMapy이 true일 때 태그의 내용을 저장.
                            fr_code = myParser.getText();
                            item = item + fr_code;
                            infr_code = false;
                            //if(line.equals(check)) check = "end";
                        }
                        break;

                    case XmlPullParser.END_TAG:

                        break;
                }
                event = myParser.next();
                //if(check.equals("end")) break;

            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void fetchXML(final int num){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)
                            url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES
                            , false);
                    myparser.setInput(stream, null);
                    if(num<=9 && num>=0) parseXMLStationCode(myparser);
                    else parseXMLLastTime(myparser);

                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}
