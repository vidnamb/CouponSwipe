package edu.cmu.couponswipe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.cmu.couponswipe.database.DatabaseHandler;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.model.DealHistory;

/**
 * Created by sparshith on 11/4/15.
 */
public class DealHistoryDAO {
    private SQLiteDatabase database;
    private DatabaseHandler dbHandler;

    public DealHistoryDAO(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public void addDealHistory(DealHistory dealHistory) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String created_at = dateFormat.format(date);

        ContentValues newDeal = new ContentValues();
        newDeal.put("email", dealHistory.getEmail());
        newDeal.put("deal_id", dealHistory.getDealUuid());
        newDeal.put("action", dealHistory.getAction());
        newDeal.put("created_at", created_at);
        open();
        database.insert("history", null, newDeal);
        close();

    }

    public DealHistory getDealHistory(String dealId, String email) {

        Cursor cursor = database.query("history", new String[] {"email", "deal_id",
                        "action","created_at", "updated_at"},
                "deal_id="+dealId+"and email="+email, null, null, null, null);

        if(cursor!=null)
        {
            cursor.moveToFirst();

            DealHistory deal = new DealHistory(
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("deal_id")),
                    cursor.getString(cursor.getColumnIndex("action")),
                    cursor.getString(cursor.getColumnIndex("created_at")),
                    cursor.getString(cursor.getColumnIndex("updated_at"))
            );

            return deal;
        }

        return null;
    }

    public ArrayList<String> getAllDealIdsForEmail(String email)
    {
        Cursor cursor = database.query("history", new String[] {"deal_id"},
                "email="+email, null, null, null, null);

        ArrayList<String> dealIDs = new ArrayList<String>();

        while(cursor.moveToNext())
        {
            dealIDs.add(cursor.getString(cursor.getColumnIndex("deal_id")));
        }

        return dealIDs;
    }

    public void deleteDealHistory(DealHistory dealHistory) {

        String where = "deal_id="+dealHistory.getDealUuid()+"and email="+dealHistory.getEmail();

        database.delete("history",where,null);

    }

    public void updateDealHistory(DealHistory dealHistory) {

        ContentValues newDeal = new ContentValues();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String updated_at = dateFormat.format(date);

        String where = "deal_id="+dealHistory.getDealUuid()+"and email="+dealHistory.getEmail();

        newDeal.put("email", dealHistory.getEmail());
        newDeal.put("deal_id", dealHistory.getDealUuid());
        newDeal.put("action", dealHistory.getAction());
        newDeal.put("updated_at", dealHistory.getUpdatedAt());

        open();

        database.update("history", newDeal, where, null);

        close();
    }
}
