package edu.cmu.couponswipe.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.adapter.ShortlistDealAdapter;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.ui.intents.Intents;

public class DealShortlistActivity extends ListActivity {

    public static final String TAG = DealStackActivity.class.getSimpleName();
    ArrayList<Deal> shortlistedDealsList = new ArrayList<Deal>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_shortlist);

        ApplicationContextProvider.setDealShortlistActivityContext(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        getDeals();

        System.out.println(shortlistedDealsList.size());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deal_shortlist, menu);
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

    public void viewDeal(View view)
    {
        Intents.openDeal(this);
    }

    public void getDeals() {
        String dealUrl = "http://10.0.3.2:8080/history/get/xyz@a.com";

        callDealAPI(dealUrl);
    }

    private void callDealAPI(String url) {


            System.out.println("**************  network");

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    //handle failure
                    e.printStackTrace();
                    System.out.println("************** fail");

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        System.out.println("************** succ");

                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject obj = new JSONObject(jsonData);
                                JSONArray deals = obj.getJSONArray("deals");

                                for(int i=0; i<deals.length(); i++){
                                    JSONObject deal = (JSONObject) deals.get(i);
                                    Deal d = new Deal();
                                    d.setDealUuid(deal.getString("dealId"));
                                    d.setDealTitle(deal.getString("dealTitle"));
                                    d.setDealAmount(deal.getString("dealAmount"));
                                    d.setDealBuyUrl(deal.getString("dealBuyUrl"));
                                    d.setLargeImageUrl(deal.getString("dealLargeUrl"));
                                    d.setMediumImageUrl(deal.getString("dealMediumUrl"));
                                    shortlistedDealsList.add(d);
                                }
                                Deal[] shortlistedDeals = shortlistedDealsList.toArray(new Deal[shortlistedDealsList.size()]);

                                ShortlistDealAdapter adapter = new ShortlistDealAdapter(getApplicationContext(), shortlistedDeals);
                                setListAdapter(adapter);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();

                            }

                        } else {
                            //handle non ok response
                            System.out.println("************** non ok");

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });

    }
}
