package com.sqlite.cric_grap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ANDROID on 22-11-2015.
 */
public class Player_Score_Information {

    private Context context;
    DBHelper dbHelper = null;
    SQLiteDatabase sqLiteDatabase = null;

    public Player_Score_Information(Context context) {
        this.context = context;
        Date date = new Date();
    }

    public void open() {
        dbHelper = new DBHelper(context);
        try {
            sqLiteDatabase = dbHelper.getWritableDatabase();
            Log.d("DataBase opened", "Open");
        } catch (Exception e) {
            Log.d("DataBase opened", "Failed exception");
            e.printStackTrace();
        }

    }

    public void close() {

        try {
            if (dbHelper != null) {
                Log.d("DataBase closed", "close");
                dbHelper.close();
            }
        } catch (Exception e) {
            Log.d("closed Exception", "exception");
            e.printStackTrace();
        }

    }


    public void delete() {
        try {

            sqLiteDatabase.delete(DBHelper.TABLE_NAME, null, null);
            System.out.println("DeleteTable Gets Called");
        } catch (Exception exception) {
            System.out.println("DeleteTable one Gets Exception");
        }

    }

    public int sumOfScore(String created_date, String innings) {
        int result;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sum(" + DBHelper.SCORE + ") from " + DBHelper.TABLE_NAME + " where " + DBHelper.INNINGS + "='" + innings + "' and " + DBHelper.DATE + "='" + created_date + "'", null);
        if (cursor.moveToFirst()) {
            Log.e("sumOfScore", cursor.getCount() + "");
            Log.e("sumOfScore", cursor.getInt(0) + "");
            result = cursor.getInt(0);
            cursor.close();
            return result;
        } else {
            return -1;
        }
    }

    public String[] checkSet(String innings,String date,String ball_num){
        String[] ret=new String[2];
        Log.w("get", innings + " " + date + " " + ball_num);
        Cursor cursor=sqLiteDatabase.rawQuery("select "+DBHelper.PLAYER_NAME+","+DBHelper.SCORE+" from "+DBHelper.TABLE_NAME+" where "+DBHelper.INNINGS+"='"+innings+"' and "+DBHelper.DATE+"='"+date+"' and "+DBHelper.BALL_NUMBER+"='"+ball_num+"'",null);
        Log.e("checkSet",cursor.getCount()+"");

        if(cursor.moveToFirst()){
            Log.e("checkSet",cursor.getString(cursor.getColumnIndex(DBHelper.PLAYER_NAME)));
            Log.e("checkSet",cursor.getString(cursor.getColumnIndex(DBHelper.SCORE)));
            ret[0]=cursor.getString(cursor.getColumnIndex(DBHelper.PLAYER_NAME));
            ret[1]=cursor.getString(cursor.getColumnIndex(DBHelper.SCORE));
        }
        return ret;
    }

    public String ongoingOver(String created_date, String innings) {
        String result=null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "+DBHelper.BALL_NUMBER+" from " + DBHelper.TABLE_NAME + " where " + DBHelper.INNINGS + "='" + innings + "' and " + DBHelper.DATE + "='" + created_date + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("sumOfScore", cursor.getCount() + "");
                Log.e("sumOfScore", cursor.getString(0) + "");
                result = cursor.getString(0);
            }while (cursor.moveToNext());
            cursor.close();
            return result;
        } else {
            return result;
        }
    }

    public int sumOfScoreExtras(String created_date, String innings) {
        int result;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sum(" + DBHelper.EXTRA_RUN + ") from " + DBHelper.TABLE_EXTRA_NAME
                + " where " + DBHelper.INNINGS + "='" + innings + "' and " + DBHelper.DATE + "='" + created_date + "'", null);
        if (cursor.moveToFirst()) {
            Log.e("sumOfScore", cursor.getCount() + "");
            Log.e("sumOfScore", cursor.getInt(0) + "");
            result = cursor.getInt(0);
            cursor.close();
            return result;
        } else {
            return -1;
        }
    }

    public String totalOverDash(String created_date, String innings) {
        String result;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "+DBHelper.TOTAL_OVER+" from " + DBHelper.TABLE_DASH_NAME
                + " where " + DBHelper.INNINGS + "='" + innings + "' and " + DBHelper.DATE + "='" + created_date + "'", null);
        if (cursor.moveToFirst()) {
            Log.e("sumOfScore", cursor.getCount() + "");
            Log.e("sumOfScore", cursor.getString(0) + "");
            result = cursor.getString(0);
            cursor.close();
            return result;
        } else {
            return "error";
        }
    }


    public long insertScore(String playerName, String ball_Number, String Score, String innings, String created_Date) {
        Log.e("Scpre",Score+"");
        int score_num = Integer.parseInt(Score);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.PLAYER_NAME, playerName);
        contentValues.put(DBHelper.BALL_NUMBER, ball_Number);
        contentValues.put(DBHelper.SCORE, score_num);
        contentValues.put(DBHelper.INNINGS, innings);
        contentValues.put(DBHelper.DATE, created_Date);

        long id = sqLiteDatabase.insert(DBHelper.TABLE_NAME, DBHelper.BALL_NUMBER, contentValues);

        Log.e("ID",id+"");

        return id;

    }

    public long insertDashBoard(String teamA,String teamB,String OverDash,String innings, String created_Date) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TOTAL_OVER,OverDash);
        contentValues.put(DBHelper.INNINGS,innings);
        contentValues.put(DBHelper.DATE,created_Date);
        contentValues.put(DBHelper.TEAM_A,teamA);
        contentValues.put(DBHelper.TEAM_B,teamB);
        long id=sqLiteDatabase.insert(DBHelper.TABLE_DASH_NAME,DBHelper.INNINGS,contentValues);
        return id;
    }

    public long inserExtraScore(String playerName, String ball_Number, String extra_Score, String innings, String created_Date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.PLAYER_NAME, playerName);
        contentValues.put(DBHelper.BALL_NUMBER, ball_Number);
        contentValues.put(DBHelper.EXTRA_RUN, extra_Score);
        contentValues.put(DBHelper.INNINGS, innings);
        contentValues.put(DBHelper.DATE, created_Date);

        long id = sqLiteDatabase.insert(DBHelper.TABLE_EXTRA_NAME, DBHelper.BALL_NUMBER, contentValues);
        return id;

    }

    public String[] teamInfo(String innings,String date){
        String[] result=new String[2];
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT "+DBHelper.TEAM_A+","+DBHelper.TEAM_B+" FROM "+DBHelper.TABLE_DASH_NAME+" WHERE "+DBHelper.INNINGS+" = '"+innings+"' AND "+DBHelper.DATE+"='"+date+"'",null);
        Log.e("teamInfo",cursor.getCount()+"");
        if(cursor.moveToFirst()){
            Log.e("teamInfo",cursor.getCount()+"");
            Log.e("teamInfo",cursor.getString(cursor.getColumnIndex(DBHelper.TEAM_A)));
            Log.e("teamInfo",cursor.getString(cursor.getColumnIndex(DBHelper.TEAM_B)));
            result[0]=cursor.getString(cursor.getColumnIndex(DBHelper.TEAM_A));
            result[1]=cursor.getString(cursor.getColumnIndex(DBHelper.TEAM_B));
        }
        return result;
    }

    public List<String> spinDash(String currentDate){
        List<String> list=new ArrayList<>();
        list.add("Select Innings");
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT "+DBHelper.INNINGS+" from "+DBHelper.TABLE_DASH_NAME+" where "+DBHelper.DATE+"='"+currentDate+"'",null);
        Log.d("spinDash",""+cursor.getCount());
        Log.d("spinDash",""+currentDate);
        if(cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(DBHelper.INNINGS)));
                Log.d("spinDash", "" + cursor.getString(cursor.getColumnIndex(DBHelper.INNINGS)));

            } while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }


    private static class DBHelper extends SQLiteOpenHelper {
        private final String TAG = "Player_DB";
        private static File dir = new File(Environment.getExternalStorageDirectory() + "");
        private static String DATABASE_NAME = dir + "/Cric_Grab/Player.db";// dir+"/
        private static final int DATABASE_VERSION = 12;
        private static final String TABLE_NAME = "PLAYER_SCORE_INF";
        private static final String TABLE_EXTRA_NAME = "PLAYER_SCORE_EXTRAS";
        private static final String TABLE_DASH_NAME = "DASHBOARD";
        private static final String SCORE = "SCORE";
        /* private static final String FOUR = "FOUR";
         private static final String THREE = "THREE";
         private static final String TWO = "TWO";
         private static final String ONE = "ONE";*/
        private static final String INNINGS = "INNINGS";
        private static final String BALL_NUMBER = "BALL_NUMBER";
        private static final String TOTAL_OVER = "OVER_DASH";
        private static final String TEAM_A = "TEAM_A";
        private static final String TEAM_B = "TEAM_B";
        private static final String EXTRA_RUN = "EXTRA_SCORE";
        private static final String PLAYER_NAME = "PLAYER_NAME";
        private static final String DATE = "CREATED_DATE";
        private Context context;
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + PLAYER_NAME + " VARCHAR2(255)," + BALL_NUMBER + " VARCHAR2(255)," + SCORE + " INTEGER," + INNINGS + " VARCHAR2(255)," + DATE + " DATETIME,PRIMARY KEY(" + BALL_NUMBER + "," + DATE + "," + INNINGS + "))";

        private static final String CREATE_EXTRA_TABLE = "CREATE TABLE " + TABLE_EXTRA_NAME + " (" + PLAYER_NAME + " VARCHAR2(255)," + BALL_NUMBER + " VARCHAR2(255)," + EXTRA_RUN + " VARCHAR2(255)," + INNINGS + " VARCHAR2(255)," + DATE + " DATETIME)";

        private static final String CREATE_DASH_TABLE = "CREATE TABLE " + TABLE_DASH_NAME + " (" + TEAM_A + " VARCHAR2(255),"+ TEAM_B + " VARCHAR2(255),"+ TOTAL_OVER + " VARCHAR2(255)," + INNINGS + " VARCHAR2(255)," + DATE + " DATETIME,PRIMARY KEY(" + DATE + "," + INNINGS + "))";
        private static final String Drop_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String Drop_Dash_TABLE = "DROP TABLE IF EXISTS " + TABLE_DASH_NAME;
        private static final String Drop_EXTRA_TABLE = "DROP TABLE IF EXISTS " + TABLE_EXTRA_NAME;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_EXTRA_TABLE);
                db.execSQL(CREATE_DASH_TABLE);
                Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Table Created");

            } catch (SQLException e
                    ) {
                e.printStackTrace();
                Log.i(TAG, "Table Created Exception");

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(Drop_TABLE);
                db.execSQL(Drop_EXTRA_TABLE);
                db.execSQL(Drop_Dash_TABLE);

                Toast.makeText(context, "Table Upgraded", Toast.LENGTH_SHORT).show();
                onCreate(db);
                Log.i(TAG, "Table upgraded");

            } catch (SQLException e) {
                e.printStackTrace();
                Log.i(TAG, "Table Upgraded Exception");

            }

        }
    }
}
