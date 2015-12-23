package com.ashok.android.cric_grap;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.sqlite.cric_grap.Player_Score_Information;
import com.utility.cric_grap.Custom_History_ListView;
import com.utility.cric_grap.HistoryGetSet;
import com.utility.cric_grap.IndividualAdapt;
import com.utility.cric_grap.IndividualGetSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndividualScoreView extends AppCompatActivity {
    ListView individualList;
    TextView failedText;
    LinearLayout title;
    private Player_Score_Information information;
    IndividualAdapt individualAdapt;
    private ArrayList<IndividualGetSet> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_score_view);
        init();

        Intent intent=getIntent();
        final int mpos=intent.getIntExtra("position",0);


        Log.e("Position", mpos + "+++++" + Custom_History_ListView.items.get(mpos).getINNINGS());
        Log.e("Date",""+History.chooseDate.getText().toString());
        new AsyncTask<Void,Void,ArrayList<IndividualGetSet>>(){
            String dates=History.chooseDate.getText().toString();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                arrayList=new ArrayList<IndividualGetSet>();
            }

            @Override
            protected ArrayList<IndividualGetSet> doInBackground(Void... params) {
                try{
                    information.open();
                    //information.combineTableSet();
                    JSONArray array=information.showOnList(Custom_History_ListView.items.get(mpos).getINNINGS(), dates);

                    if(array!=null) {
                        Log.w("arrayyyy", array.toString());
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            IndividualGetSet indi = new IndividualGetSet();
                            Log.w("arrayyyy", object.getString("PlayerName"));
                            Log.w("arrayyyy", object.getString("Ball_number"));
                            Log.w("arrayyyy", object.getString("Score"));
                            indi.setPLAYERNAME(object.getString("PlayerName"));
                            indi.setBALL_NUMBER(object.getString("Ball_number"));
                            indi.setSCORE(object.getString("Score"));
                            indi.setType("Regular");
                            arrayList.add(indi);
                        }
                    }
                    JSONArray array1=information.showOnExtraList(Custom_History_ListView.items.get(mpos).getINNINGS(), dates);

                    if(array1!=null) {
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject object = array1.getJSONObject(i);
                            IndividualGetSet indi = new IndividualGetSet();
                            Log.w("arrayyyy", object.getString("PlayerName"));
                            Log.w("arrayyyy", object.getString("Ball_number"));
                            Log.w("arrayyyy", object.getString("Score"));
                            indi.setPLAYERNAME(object.getString("PlayerName"));
                            indi.setBALL_NUMBER(object.getString("Ball_number"));
                            indi.setSCORE(object.getString("Score"));
                            indi.setType("Extra");
                            arrayList.add(indi);
                        }
                    }
                    return arrayList;
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    information.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<IndividualGetSet> individualGetSets) {
                super.onPostExecute(individualGetSets);

                if(individualGetSets.size()>0){
                    Log.w("PostExecute",""+individualGetSets.size()+"++++++ "+individualGetSets+"");
                    failedText.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    individualAdapt=new IndividualAdapt(IndividualScoreView.this,R.layout.individual_listview,individualGetSets);
                    individualList.setAdapter(individualAdapt);
                }else{
                    failedText.setVisibility(View.VISIBLE);
                    title.setVisibility(View.GONE);
                }
            }
        }.execute();


    }

    private void init() {
        individualList= (ListView) findViewById(R.id.individualList);
        failedText
                = (TextView) findViewById(R.id.failedText);
        title= (LinearLayout) findViewById(R.id.title);
        information=new Player_Score_Information(IndividualScoreView.this);


    }
}
