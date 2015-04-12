package edu.cmu.couponswipe.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.cmu.couponswipe.database.DatabaseHandler;
import edu.cmu.couponswipe.model.User;

/**
 * Created by sparshith on 11/4/15.
 */
public class UserDAO {
    private SQLiteDatabase database;
    private DatabaseHandler dbHandler;

    public UserDAO(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public void addUser(User user) {

    }

    public User getUser(String email, String password) {
        return null;
    }

    public void deleteUser(User user) {

    }

    public void updateUser(User user) {

    }

}
