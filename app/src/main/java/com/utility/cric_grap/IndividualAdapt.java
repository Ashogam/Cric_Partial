package com.utility.cric_grap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ashok.android.cric_grap.R;

import java.util.ArrayList;

/**
 * Created by ANDROID on 10-12-2015.
 */
public class IndividualAdapt extends ArrayAdapter<IndividualGetSet>{

    /**
     * Created by ANDROID on 09-12-2015.
     */


        public ArrayList<IndividualGetSet> items;
        Holder holder = null;
        private Context context;
        private int resource;

        public IndividualAdapt(Context context, int resource, ArrayList<IndividualGetSet> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.items = objects;

            for(int i=0;i<items.size();i++){
                Log.d("for",items.get(i).getPLAYERNAME());
                Log.d("for", items.get(i).getBallNumber());
                Log.d("for",items.get(i).getSCORE());
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            try {

                Log.d("posotion",position+"");
                View vi = convertView;

                if (convertView == null) {
                    LayoutInflater inflater = ((Activity) context)
                            .getLayoutInflater();
                    vi = inflater.inflate(resource, parent, false);
                    holder = new Holder(vi);
                    vi.setTag(holder);

                } else {
                    holder = (Holder) vi.getTag();

                }
                if (items.size() <= 0) {
                    holder.indBall.setText("No Data");
                } else {
                    Log.d("ball",items.get(position).getBallNumber());
                    Log.d("name",items.get(position).getPLAYERNAME());
                    Log.d("Score",items.get(position).getSCORE());
                    holder.indBall.setText(items.get(position).getBallNumber());
                    holder.indName.setText(items.get(position).getPLAYERNAME());
                    holder.indScore.setText(items.get(position).getSCORE());

                    vi.setOnClickListener(new OnItemClick(position));
                }
                return vi;
            } catch (Exception vi) {
                vi.printStackTrace();
            }

            return null;
        }

        public class Holder {
            TextView indScore, indName, indBall;

            public Holder(View vi) {
                indScore = (TextView) vi.findViewById(R.id.indScore);
                indName = (TextView) vi.findViewById(R.id.indName);
                indBall = (TextView) vi.findViewById(R.id.indBall);

            }
        }


        private class OnItemClick implements View.OnClickListener {
            private int mPosition;

            public OnItemClick(int position) {
                // TODO Auto-generated constructor stub
                mPosition = position;
            }

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }


        }


}
