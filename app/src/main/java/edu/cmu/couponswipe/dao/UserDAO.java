package edu.cmu.couponswipe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.couponswipe.database.DatabaseHandler;
import edu.cmu.couponswipe.model.DealHistory;
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

        ContentValues newUser = new ContentValues();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String created_at = dateFormat.format(date);

        newUser.put("first_name", user.getFirstName());
        newUser.put("last_name", user.getLastName());
        newUser.put("email", user.getEmail());
        newUser.put("phone", user.getPhoneNumber());
        newUser.put("last_latitude", user.getLastLatitude());
        newUser.put("last_longitude", user.getLastLongitude());
        newUser.put("deal_radius", user.getDealRadius());
        newUser.put("deal_categories", user.getDealCategoriesString());
        newUser.put("created_at", created_at);

        open();

        database.insert("users", null, newUser);

        close();
    }

    public User getUser(String email, String password) {

        Cursor cursor = database.query("users", new String[] {"first_name", "last_name",
                        "email","phone","created_at", "updated_at"},
                "email="+email, null, null, null, null);

        if(cursor!=null)
        {
            cursor.moveToFirst();

            User user = new User(
                    cursor.getString(cursor.getColumnIndex("first_name")),
                    cursor.getString(cursor.getColumnIndex("last_name")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getString(cursor.getColumnIndex("last_latitude")),
                    cursor.getString(cursor.getColumnIndex("last_longitude")),
                    cursor.getInt(cursor.getColumnIndex("deal_radius")),
                    cursor.getString(cursor.getColumnIndex("deal_categories")),
                    cursor.getString(cursor.getColumnIndex("created_at")),
                    cursor.getString(cursor.getColumnIndex("updated_at"))
            );

            return user;
        }

        return null;
    }

    public void deleteUser(User user) {

        String where = "email="+user.getEmail();

        database.delete("users",where,null);

    }

    public void updateUser(User user) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String updated_at = dateFormat.format(date);

        ContentValues newUser = new ContentValues();

        newUser.put("first_name", user.getFirstName());
        newUser.put("last_name", user.getLastName());
        newUser.put("email", user.getEmail());
        newUser.put("phone", user.getPhoneNumber());
        newUser.put("last_latitude", user.getLastLatitude());
        newUser.put("last_longitude", user.getLastLongitude());
        newUser.put("deal_radius", user.getDealRadius());
        newUser.put("deal_categories", user.getDealCategoriesString());
        newUser.put("updated_at", updated_at);

        open();

        database.insert("users", null, newUser);

        close();

    }

}
