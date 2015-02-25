package com.example.user.do_i;

/**
 * Created by user on 15. 2. 22.
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NaverHandleXML {

    private String mapx = null;
    private String mapy = null;
    private String urlString = null;
    boolean inMapx = false, inMapy = false;
    private XmlPullParserFactory xmlFactoryObject;
    public boolean parsingComplete = true;
    public NaverHandleXML(String url){
        this.urlString = url;
    }
    public void setNaverUrl(String url){
       this.urlString = url;
        this.parsingComplete = true;
    }

    public String getMapx(){
        return mapx;
    }
    public String getMapy(){
        return mapy;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                //String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(myParser.getName().equals("mapx")){ //mapx 만나면 내용을 받을수 있게 하자
                            inMapx = true;
                        }
                        if(myParser.getName().equals("mapy")){ //mapy 만나면 내용을 받을수 있게 하자
                            inMapy = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(inMapx){ //isMapx이 true일 때 태그의 내용을 저장.
                            mapx = myParser.getText();
                            inMapx = false;
                        }
                        if(inMapy){ //isMapy이 true일 때 태그의 내용을 저장.
                            mapy = myParser.getText();
                            inMapy = false;
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
    public void fetchXML(){
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
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

}