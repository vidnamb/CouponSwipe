package edu.cmu.couponswipe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.sessions.Current;
import edu.cmu.couponswipe.ui.intents.Intents;

public class UserProfileActivity extends Activity {

    TextView firstNameTV;
    TextView lastNameTV;
    TextView phoneTV;
    TextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getActionBar().setDisplayHomeAsUpEnabled(true);

         firstNameTV = (TextView) findViewById(R.id.first_name);
        lastNameTV = (TextView) findViewById(R.id.last_name);
        phoneTV = (TextView) findViewById(R.id.user_phone);
        emailTV = (TextView) findViewById(R.id.user_signup_email);

        firstNameTV.setText(Current.firstName);
        lastNameTV.setText(Current.lastName);
        phoneTV.setText(Current.phone);
        emailTV.setText(Current.email);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editProfile(View view){
        Intents.openEditProfile(this);
    }

    public void logout(View view){
        Current.logout();
        Intents.openLoginPage(this);
    }
}
