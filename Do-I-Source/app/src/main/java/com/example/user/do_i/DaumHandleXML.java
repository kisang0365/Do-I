package com.example.user.do_i;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 15. 2. 23.
 */
public class DaumHandleXML {

    private String urlString = null;
    String mapx, mapy = null;
    private XmlPullParserFactory xmlFactoryObject;
    public boolean parsingComplete = true;

    public DaumHandleXML(String url){
        this.urlString = url;
    }
    public void setDaumUrl(String url){
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
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.compareTo("result") == 0) {
                            mapx = myParser.getAttributeValue(null, "x");
                            mapy = myParser.getAttributeValue(null, "y");
                        }
                    break;
                }
                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parsingComplete = false;
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
