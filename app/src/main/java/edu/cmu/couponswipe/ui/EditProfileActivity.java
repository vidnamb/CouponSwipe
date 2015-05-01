package edu.cmu.couponswipe.ui;

import android.app.Activity;
import android.content.Intent;
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
import edu.cmu.couponswipe.ui.intents.Intents;

public class EditProfileActivity extends Activity {


    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;
    EditText phoneNumberET;

    String fName;
    String lName;
    String email;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        firstNameET = (EditText)findViewById(R.id.first_name);
        lastNameET = (EditText)findViewById(R.id.last_name);
        emailET = (EditText)findViewById(R.id.user_signup_email);
        phoneNumberET = (EditText)findViewById(R.id.user_phone);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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

    public void update(View view){
        fName = firstNameET.getText().toString();
        lName = lastNameET.getText().toString();
        email = emailET.getText().toString();
        phone = phoneNumberET.getText().toString();
        JSONObject obj = new JSONObject();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try{
            obj.put("email",email);
            obj.put("firstName", fName);
            obj.put("lastName", lName);
            obj.put("phoneNumber", phone);

            String user = obj.toString();
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost("http://10.0.3.2:8080/user/update");
            StringEntity se = new StringEntity(user);

            httpost.setEntity(se);
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");

            ResponseHandler responseHandler = new BasicResponseHandler();
            String response = (String) httpclient.execute(httpost, responseHandler);
            Intents.openDealStack(this);
        }catch(JSONException e){
            Toast.makeText(getApplicationContext(), "JSON", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Toast.makeText(getApplicationContext(), "Invalid entries. Try again", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("#######");

            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("%%%%%");
            Toast.makeText(getApplicationContext(), "Problem with the connection the server", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        //Intents.openUserProfile(this);
    }


}
