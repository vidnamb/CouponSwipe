package couponswipe.cmu.edu.couponswipe.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import couponswipe.cmu.edu.couponswipe.database.DatabaseHandler;
import couponswipe.cmu.edu.couponswipe.model.DealHistory;

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

    }

    public void deleteDealHistory(DealHistory dealHistory) {

    }

    public void updateDealHistory(DealHistory dealHistory) {

    }
}
