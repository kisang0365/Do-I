package com.example.user.do_i;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by user on 15. 2. 27.
 */
public class SubwayActivity extends Activity {

    private SeoulXML seoulObj;
    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;

    private ArrayList<String> sGroupList = null;
    private ArrayList<ArrayList<String>> sChildList = null;

    private ArrayList<String> mChildListContent = null;
    private ExpandableListView mListView;
    private TextView text;
    private int line;
    private int station;


    protected void onCreate(Bundle savedINstanceState) {
        super.onCreate(savedINstanceState);
        setContentView(R.layout.activity_subway);

        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();
        seoulObj = new SeoulXML("111");

        mListView = (ExpandableListView) this.findViewById( R.id.elv_list);
        text = (TextView) this.findViewById(R.id.testtext);

        setArrayData();
        mListView.setAdapter(new AdapMain(this, mGroupList, mChildList));

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });


        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                station = childPosition;
                line = groupPosition;

                String queryURL = "http://openapi.seoul.go.kr:8088/726454656e6b69733130307472435641/xml/SearchInfoBySubwayNameService/1/5/" + mChildList.get(line).get(station)+"/";
                line = line+1;
                seoulObj.setSeoulUrl(queryURL);
                seoulObj.fetchXML(line);
                while (seoulObj.parsingComplete) {
                    //status.setText("파싱중...");
                }
                //text.setText(station +"   " + line + "       " + mChildList.get(line).get(station));
                text.setText(seoulObj.getItem());


                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Collapse = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), "g Expand = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //mListView.setAdapter( new AdapMain(this, arrayGroup, arrayChild ) );






        /*
        ExpandableListView status = (ExpandableListView) findViewById(R.id.status);
        setArrayData();


        Button search = (Button) findViewById(R.id.search);
        search.setText("Search");

        search.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {

            String queryURL = "http://openapi.seoul.go.kr:8088/726454656e6b69733130307472435641/xml/SearchLastTrainTimeByFRCodeService/1/5/418/1/2/";



            seoulObj = new SeoulXML(queryURL);
            seoulObj.fetchXML();
            while (seoulObj.parsingComplete) {
                //status.setText("파싱중...");
            }

            String item = seoulObj.getItem();
                   // status.setText(item);

            }

        });*/

    }


    private void setArrayData(){
        mGroupList.add("1호선");
        mGroupList.add("2호선");
        mGroupList.add("3호선");
        mGroupList.add("4호선");
        mGroupList.add("5호선");
        mGroupList.add("6호선");
        mGroupList.add("7호선");
        mGroupList.add("8호선");
        mGroupList.add("9호선");

        ArrayList<String> line1 = new ArrayList<String>();
        line1.add("인천");
        line1.add("동인천");
        line1.add("도원");
        line1.add("제물포");
        line1.add("도화");
        line1.add("주안");
        line1.add("간석");
        line1.add("동암");
        line1.add("백운");
        line1.add("부평");
        line1.add("부개");
        line1.add("송내");


        ArrayList<String> line2 = new ArrayList<String>();
        line2.add("합정");
        line2.add("홍대입구");
        line2.add("신촌");
        line2.add("이대");
        line2.add("아현");
        line2.add("충정로");
        line2.add("시청");
        line2.add("을지로입구");


        ArrayList<String> line3 = new ArrayList<String>();
        line3.add("대화");


        ArrayList<String> line4 = new ArrayList<String>();
        line4.add("당고개");


        mChildList.add(line1);
        mChildList.add(line2);
        mChildList.add(line3);
        mChildList.add(line4);

    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        ArrayList<String> searchline = new ArrayList<String>();
        ArrayList<String> searchContent = new ArrayList<String>();
        ArrayList<ArrayList<String>> searchChild = new ArrayList<ArrayList<String>>();

        sGroupList.clear();
        sChildList.clear();

        if( charText.length() == 0 ){
            sGroupList.addAll(mGroupList);
            sChildList.addAll(mChildList);
        }
        else{
            for(int i=0; i<9; i++){
                searchContent.clear();
                for(int j=0; j<mChildList.get(i).size(); j++){
                    if (mChildList.get(i).get(j).toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        searchline.add(mGroupList.get(i));
                        searchContent.add(mChildList.get(i).get(j));
                    }
                }
                searchChild.add(searchContent);
            }
        }
        this.sGroupList = searchline;
        this.sChildList = searchChild;
    }

}
