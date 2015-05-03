package edu.cmu.couponswipe.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.sessions.Current;
import edu.cmu.couponswipe.ui.intents.Intents;

public class LoginActivity extends Activity {

    EditText emailET;
    EditText pwdET;
    boolean loginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public void login(View view) {
        emailET = (EditText) findViewById(R.id.userEmailEditText);
        pwdET = (EditText) findViewById(R.id.userPasswordEditText);
        String email = emailET.getText().toString();
        String password = pwdET.getText().toString();
        RequestParams params = new RequestParams();
        invokeWS(params, email, password);
        if (loginSuccess) Intents.openDealStack(this);
    }

    private void invokeWS(RequestParams params, String email, final String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.3.2:8080/user/get/" + email, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("exists") == false) {
                        Toast.makeText(getApplicationContext(), "Wrong username", Toast.LENGTH_LONG).show();
                    } else if (obj.getString("password").equals(password)) {
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        Current.firstName = obj.getString("firstName");
                        Current.lastName = obj.getString("lastName");
                        Current.email = obj.getString("email");
                        Current.phone = obj.getString("phoneNumber");
                        Current.prefDist = 50;
                        navigatetoDealStackActivity();
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void navigatetoDealStackActivity() {
        Intent homeIntent = new Intent(getApplicationContext(), DealStackActivity.class);
        startActivity(homeIntent);
    }

    public void signup(View view) {
        Intents.openSignUpPage(this);
    }
}
