package edu.cmu.couponswipe.ws.remote;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.cmu.couponswipe.model.Deal;

/**
 * Created by sparshith on 5/2/15.
 */
public class DS extends AsyncHttpResponseHandler {
    public static ArrayList<Deal> shortlistedDealsList = new ArrayList<Deal>();
    @Override
    public void onSuccess(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            JSONArray deals = obj.getJSONArray("deals");

            for(int i=0; i<deals.length(); i++){
                JSONObject deal = (JSONObject) deals.get(i);
                Deal d = new Deal();
                d.setDealUuid(deal.getString("dealId"));

                d.setDealTitle(deal.getString("dealTitle"));
                shortlistedDealsList.add(d);
                System.out.println(deal.getString("dealId"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
          //  Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }
    @Override
    public void onFailure(int statusCode, Throwable error,
                          String content) {

        // When Http response code is '404'
        if(statusCode == 404){
            //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
        }
        // When Http response code is '500'
        else if(statusCode == 500){
            //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
        }
        // When Http response code other than 404, 500
        else{
            //Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
        }
    }
}

