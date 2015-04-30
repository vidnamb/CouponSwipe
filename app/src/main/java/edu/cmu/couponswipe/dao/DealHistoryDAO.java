package edu.cmu.couponswipe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

        ContentValues newDeal = new ContentValues();

        newDeal.put("history_id", dealHistory.getHistoryId());
        newDeal.put("user_id", dealHistory.getUserId());
        newDeal.put("deal_id", dealHistory.getDealUuid());
        newDeal.put("action", dealHistory.getAction());
        newDeal.put("created_at", dealHistory.getCreatedAt());
        newDeal.put("updated_at", dealHistory.getUpdatedAt());

        open();

        database.insert("history", null, newDeal);

        close();
    }

    public DealHistory getDealHistory(String dealId) {

        Cursor cursor = database.query("history", new String[] {"history_id", "user_id", "deal_id",
                        "action","created_at", "updated_at"},
                "deal_id="+dealId, null, null, null, "history_id");

        if(cursor!=null)
        {
            cursor.moveToFirst();

            DealHistory deal = new DealHistory(
                    cursor.getInt(cursor.getColumnIndex("history_id")),
                    cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getString(cursor.getColumnIndex("deal_id")),
                    cursor.getString(cursor.getColumnIndex("action")),
                    cursor.getString(cursor.getColumnIndex("created_at")),
                    cursor.getString(cursor.getColumnIndex("updated_at"))
            );

            return deal;
        }

        return null;
    }

    public void deleteDealHistory(DealHistory dealHistory) {

        String where = "deal_id="+dealHistory.getDealUuid();

        database.delete("history",where,null);

    }

    public void updateDealHistory(DealHistory dealHistory) {

        ContentValues newDeal = new ContentValues();

        String where = "deal_id="+dealHistory.getDealUuid();

        newDeal.put("history_id", dealHistory.getHistoryId());
        newDeal.put("user_id", dealHistory.getUserId());
        newDeal.put("deal_id", dealHistory.getDealUuid());
        newDeal.put("action", dealHistory.getAction());
        newDeal.put("created_at", dealHistory.getCreatedAt());
        newDeal.put("updated_at", dealHistory.getUpdatedAt());

        open();

        database.update("history", newDeal, where, null);

        close();
    }
}
