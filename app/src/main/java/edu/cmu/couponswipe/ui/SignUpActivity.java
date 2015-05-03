package edu.cmu.couponswipe.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.sessions.Current;
import edu.cmu.couponswipe.ui.intents.Intents;

public class SignUpActivity extends Activity {


    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;
    EditText phoneNumberET;
    EditText passwordET;

    String fName;
    String lName;
    String email;
    String phone;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        firstNameET = (EditText) findViewById(R.id.firstNameEditText);
        lastNameET = (EditText) findViewById(R.id.lastNameEditText);
        emailET = (EditText) findViewById(R.id.userEmailEditText);
        phoneNumberET = (EditText) findViewById(R.id.userPhoneEditText);
        passwordET = (EditText) findViewById(R.id.userPasswordEditText);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    public void signup(View view) {

        fName = firstNameET.getText().toString();
        lName = lastNameET.getText().toString();
        email = emailET.getText().toString();
        phone = phoneNumberET.getText().toString();
        password = passwordET.getText().toString();
        JSONObject obj = new JSONObject();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        if (fName == null || fName.length() == 0 || lName == null || lName.length() == 0 || email == null || email.length() == 0 || phone == null || phone.length() == 0 || password == null || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid entries. Try again", Toast.LENGTH_LONG).show();
        } else {
            try {
                obj.put("email", email);
                obj.put("firstName", fName);
                obj.put("lastName", lName);
                obj.put("password", password);
                obj.put("phoneNumber", phone);
                obj.put("prefCategories", "");
                obj.put("prefDistance", 10);

                String user = obj.toString();
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httpost = new HttpPost("http://10.0.3.2:8080/user/signup");
                StringEntity se = new StringEntity(user);

                httpost.setEntity(se);
                httpost.setHeader("Accept", "application/json");
                httpost.setHeader("Content-type", "application/json");

                ResponseHandler responseHandler = new BasicResponseHandler();
                String response = (String) httpclient.execute(httpost, responseHandler);
                Current.firstName = obj.getString("firstName");
                Current.lastName = obj.getString("lastName");
                Current.email = obj.getString("email");
                Current.phone = obj.getString("phoneNumber");
                Current.prefDist = 50;
                Intents.openDealStack(this);
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON", Toast.LENGTH_LONG).show();

                e.printStackTrace();
            } catch (ClientProtocolException e) {
                Toast.makeText(getApplicationContext(), "UserName already taken", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Problem with the connection the server", Toast.LENGTH_LONG).show();

                e.printStackTrace();
            }
        }
    }

    public void cancel(View view) {
        Intents.openLoginPage(this);
    }


}
