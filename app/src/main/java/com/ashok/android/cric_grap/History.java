package com.ashok.android.cric_grap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.sqlite.cric_grap.Player_Score_Information;
import com.utility.cric_grap.Custom_History_ListView;
import com.utility.cric_grap.HistoryGetSet;

import java.util.ArrayList;
import java.util.Calendar;

public class History extends AppCompatActivity {
    protected static EditText chooseDate;
    private Button searchDate,showAll;
    private Player_Score_Information information;
    LinearLayout failedMessage,listTitle;
    Custom_History_ListView history_listView;
    ListView listHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        init();
        OnClickListen();

    }

    private void init() {

        chooseDate= (EditText) findViewById(R.id.chooseDate);
        searchDate= (Button) findViewById(R.id.searchDate);
        showAll= (Button) findViewById(R.id.showAll);
        chooseDate.setInputType(InputType.TYPE_NULL);
        failedMessage= (LinearLayout) findViewById(R.id.failedMessage);
        listTitle= (LinearLayout) findViewById(R.id.listTitle);
        information=new Player_Score_Information(History.this);
        listHistory= (ListView) findViewById(R.id.listHistory);

    }

    private void OnClickListen(){
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"string");
            }
        });

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(chooseDate.getText().toString())){
                    chooseDate.setError("Choose Date");
                }else{
                    final String date=chooseDate.getText().toString();
                    new AsyncTask<Void,Void,ArrayList<HistoryGetSet>>(){

                        @Override
                        protected ArrayList<HistoryGetSet> doInBackground(Void... params) {
                            try{
                                information.open();
                                ArrayList<HistoryGetSet> historyGetSet=information.searchList(date);
                                return historyGetSet;
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                information.close();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(ArrayList<HistoryGetSet> historyGetSet) {
                            super.onPostExecute(historyGetSet);
                            if(historyGetSet!=null){
                                failedMessage.setVisibility(View.GONE);
                                listTitle.setVisibility(View.VISIBLE);
                                history_listView=new Custom_History_ListView(History.this,R.layout.customlistview,historyGetSet);
                                listHistory.setAdapter(history_listView);
                            }else{
                                failedMessage.setVisibility(View.VISIBLE);
                                listTitle.setVisibility(View.GONE);
                            }
                        }
                    }.execute();
                }

            }
        });

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Log.e("Date", year + "/" + (month + 1) + "/" + day);
            if(String.valueOf(day).length()>1){
                chooseDate.setText(year+"/"+(month+1)+"/"+day);
            }else{

                chooseDate.setText(year+"/"+(month+1)+"/0"+day);
            }

        }
    }

}
