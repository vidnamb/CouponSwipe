package edu.cmu.couponswipe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import edu.cmu.couponswipe.database.DatabaseHandler;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.model.User;

/**
 * Created by sparshith on 11/4/15.
 */
public class DealDAO {
    private SQLiteDatabase database;
    private DatabaseHandler dbHandler;

    public DealDAO(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public void addDeal(Deal deal) {

        ContentValues newDeal = new ContentValues();

        newDeal.put("dealuuid", deal.getDealUuid());
        newDeal.put("title", deal.getDealTitle());
        newDeal.put("descr", deal.getDealDescription());
        newDeal.put("location", deal.getDealLocation());
        newDeal.put("latitude", deal.getDealLatitude());
        newDeal.put("longitude", deal.getDealLongitude());
        newDeal.put("amount", deal.getDealAmount());
        newDeal.put("currency", deal.getDealCurrency());
        newDeal.put("start_date", deal.getDealStartDate());
        newDeal.put("expiry_date", deal.getDealExpiryDate());
        newDeal.put("small_url", deal.getSmallImageUrl());
        newDeal.put("medium_url", deal.getMediumImageUrl());
        newDeal.put("large_url", deal.getLargeImageUrl());
        newDeal.put("merchant_id", deal.getMerchantUuid());
        newDeal.put("merchant_name", deal.getMerchantName());
        newDeal.put("merchant_url", deal.getMerchantUrl());
        newDeal.put("dealbuy_url", deal.getDealBuyUrl());

        open();

        database.insert("deals", null, newDeal);

        close();
    }

    public Deal getDeal(String dealId) {

        Cursor cursor = database.query("deals", new String[] {"dealuuid", "title", "descr",
                        "location","latitude", "longitude", "amount", "currency", "start_date",
                        "expiry_date", "small_url", "medium_url", "large_url", "merchant_id",
                        "merchant_name", "merchant_url", "dealbuy_url"},
                "dealuuid="+dealId, null, null, null, "dealuuid");

        if(cursor!=null)
        {
            cursor.moveToFirst();

            Deal deal = new Deal(
                    cursor.getString(cursor.getColumnIndex("dealuuid")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("descr")),
                    cursor.getString(cursor.getColumnIndex("location")),
                    cursor.getString(cursor.getColumnIndex("latitude")),
                    cursor.getString(cursor.getColumnIndex("longitude")),
                    cursor.getString(cursor.getColumnIndex("amount")),
                    cursor.getString(cursor.getColumnIndex("currency")),
                    cursor.getString(cursor.getColumnIndex("start_date")),
                    cursor.getString(cursor.getColumnIndex("expiry_date")),
                    cursor.getString(cursor.getColumnIndex("small_url")),
                    cursor.getString(cursor.getColumnIndex("medium_url")),
                    cursor.getString(cursor.getColumnIndex("large_url")),
                    cursor.getString(cursor.getColumnIndex("merchant_id")),
                    cursor.getString(cursor.getColumnIndex("merchant_name")),
                    cursor.getString(cursor.getColumnIndex("merchant_url")),
                    cursor.getString(cursor.getColumnIndex("dealbuy_url"))
            );

            return deal;
        }

        return null;
    }

    public void deleteDeal(Deal deal) {

        String where = "dealuuid="+deal.getDealUuid();

        database.delete("deals",where,null);
    }

    public void updateDeal(Deal deal) {

        ContentValues newDeal = new ContentValues();

        String where = "dealuuid="+deal.getDealUuid();

        newDeal.put("dealuuid", deal.getDealUuid());
        newDeal.put("title", deal.getDealTitle());
        newDeal.put("descr", deal.getDealDescription());
        newDeal.put("location", deal.getDealLocation());
        newDeal.put("latitude", deal.getDealLatitude());
        newDeal.put("longitude", deal.getDealLongitude());
        newDeal.put("amount", deal.getDealAmount());
        newDeal.put("currency", deal.getDealCurrency());
        newDeal.put("start_date", deal.getDealStartDate());
        newDeal.put("expiry_date", deal.getDealExpiryDate());
        newDeal.put("small_url", deal.getSmallImageUrl());
        newDeal.put("medium_url", deal.getMediumImageUrl());
        newDeal.put("large_url", deal.getLargeImageUrl());
        newDeal.put("merchant_id", deal.getMerchantUuid());
        newDeal.put("merchant_name", deal.getMerchantName());
        newDeal.put("merchant_url", deal.getMerchantUrl());
        newDeal.put("dealbuy_url", deal.getDealBuyUrl());

        open();

        database.update("deals", newDeal, where, null);

        close();

    }
}
