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

import java.util.ArrayList;

public class IndividualScoreView extends AppCompatActivity {
    ListView individualList;
    TextView failedText;
    LinearLayout title;
    private Player_Score_Information information;
    IndividualAdapt individualAdapt;
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
            protected ArrayList<IndividualGetSet> doInBackground(Void... params) {
                try{
                    information.open();
                    ArrayList<IndividualGetSet> getSets=information.showOnList(Custom_History_ListView.items.get(mpos).getINNINGS(), dates);

                    return getSets;
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
                Log.w("PostExecute",""+individualGetSets.size()+"++++++ "+individualGetSets+"");
                if(individualGetSets!=null){

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
