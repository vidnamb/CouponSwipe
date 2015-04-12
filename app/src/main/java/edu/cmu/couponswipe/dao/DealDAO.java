package edu.cmu.couponswipe.dao;

import android.content.Context;
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

    }

    public Deal getDeal(String dealId) {
        return null;
    }

    public void deleteDeal(Deal deal) {

    }

    public void updateDeal(Deal deal) {

    }
}
