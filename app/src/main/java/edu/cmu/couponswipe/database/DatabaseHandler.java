package edu.cmu.couponswipe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sparshith on 11/4/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "couponswipe";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_USERS = "create table users (user_id int, first_name varchar(255), last_name varchar(255), email varchar(255)," +
            " password varchar(255), phone varchar(255), created_at DATE, updated_at DATE";

    private static final String TABLE_DEALS = "create table deals (dealuuid varchar(255)," +
            "  title varchar(255)," +
            "  descr text," +
            "  location varchar(255)," +
            "  latitude varchar(255)," +
            "  longitude varchar(255)," +
            "  amount float," +
            "  currency varchar(255)," +
            "  start_date varchar(255)," +
            "  expiry_date varchar(255)," +
            "  category varchar(255)," +
            "  small_url varchar(255)," +
            "  medium_url varchar(255)," +
            "  large_url varchar(255)," +
            "  merchant_id varchar(255)," +
            "  merchant_name varchar(255)," +
            "  merchant_url varchar(255)," +
            "  dealbuy_url varchar(255))";

    private static final String TABLE_DEAL_HISTORY = "create table history(" +
            "  history_id int," +
            "  user_id int," +
            "  deal_id varchar(255)," +
            "  action varchar(255)," +
            "  created_at date," +
            "  updated_at date)";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_USERS);
        db.execSQL(TABLE_DEALS);
        db.execSQL(TABLE_DEAL_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
