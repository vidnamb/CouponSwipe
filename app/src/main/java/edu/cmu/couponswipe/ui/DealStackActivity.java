package edu.cmu.couponswipe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
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
import java.util.HashMap;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.sessions.SessionManager;
import edu.cmu.couponswipe.ui.intents.Intents;

/**
 * MainActivity - renamed to DealStackActivity
 * activity_main.xml renamed to activity_deal_stack
 *
 */

public class DealStackActivity extends Activity {

    public static final String TAG = DealStackActivity.class.getSimpleName();

    private int currentDemoDeal = 0;    //0,1,2 represent three sample deals

    // https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_203792_212556_0&lat=37.398873&lng=-122.071806&radius=10&offset=0&limit=20
    private static final String apiToken = "US_AFF_0_203792_212556_0";
    private static final int numDeals = 20;

    // Session Manager Class
    SessionManager session;

    // Location
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_stack);

        // Session class instance
        session = new SessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        Location location = getLocation();

        if(location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();


        } else {

        }


        buildSwipeCards();
    }


    //Set up Cards for Swiping
    public void buildSwipeCards()
    {
        CardContainer mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        mCardContainer.setOrientation(Orientations.Orientation.Disordered);

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        CardModel card1 = new CardModel(getString(R.string.demo_coupon_title_1)+": "+getString(R.string.demo_coupon_price_1), getString(R.string.demo_coupon_store_1)+": "+getString(R.string.demo_coupon_distance_1), getResources().getDrawable(R.drawable.easter_coupon));
        CardModel card2 = new CardModel(getString(R.string.demo_coupon_title_2)+": "+getString(R.string.demo_coupon_price_2), getString(R.string.demo_coupon_store_2)+": "+getString(R.string.demo_coupon_distance_2), getResources().getDrawable(R.drawable.omaha_steak));
        CardModel card3 = new CardModel(getString(R.string.demo_coupon_title_3)+": "+getString(R.string.demo_coupon_price_3), getString(R.string.demo_coupon_store_3)+": "+getString(R.string.demo_coupon_distance_3), getResources().getDrawable(R.drawable.golden_gate));

        adapter.add(card1);
        adapter.add(card2);
        adapter.add(card3);

        mCardContainer.setAdapter(adapter);

    }

    public void openDeal()
    {
        Intents.openDeal(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deal_stack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_user_profile:
                openUserProfile();
                return true;
            case R.id.action_shortlist:
                openShortlist();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openUserProfile()
    {
        Intents.openUserProfile(this);
    }

    public void openShortlist()
    {
        Intents.openShortList(this);
    }

    public void openPreferences(View view){

        Intents.openDealPreferences(this);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public ArrayList<Deal> getDeals() {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken +  "offset=0&limit=" + numDeals;

        return new ArrayList<Deal>();
    }

    public ArrayList<Deal> getDeals(ArrayList<String> dealCategories) {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken +  "offset=0&limit=" + numDeals;

        return new ArrayList<Deal>();
    }

    public ArrayList<Deal> getDeals(double latitude, double longitude, int dealRadius, ArrayList<String> dealCategories) {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken + "&lat=" + latitude + "&lng=" + longitude
                + "&radius=" + dealRadius + "&offset=0&limit=" + numDeals;



        return new ArrayList<Deal>();

    }

    private void callDealAPI(String url) {

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
//                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
//                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
//                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
//        if (mProgressBar.getVisibility() == View.INVISIBLE) {
//            mProgressBar.setVisibility(View.VISIBLE);
//            mRefreshImageView.setVisibility(View.INVISIBLE);
//        }
//        else {
//            mProgressBar.setVisibility(View.INVISIBLE);
//            mRefreshImageView.setVisibility(View.VISIBLE);
//        }
    }

    private void updateDisplay() {
//        Current current = mForecast.getCurrent();
//
//        mTemperatureLabel.setText(current.getTemperature() + "");
//        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
//        mHumidityValue.setText(current.getHumidity() + "");
//        mPrecipValue.setText(current.getPrecipChance() + "%");
//        mSummaryLabel.setText(current.getSummary());
//
//        Drawable drawable = getResources().getDrawable(current.getIconId());
//        mIconImageView.setImageDrawable(drawable);
    }


    private ArrayList<Deal> parseDeals(String jsonData) throws JSONException {
        JSONObject response = new JSONObject(jsonData);
        JSONArray dealData = response.getJSONArray("deals");

        ArrayList<Deal> deals = new ArrayList<Deal>();

        for (int i = 0; i < dealData.length(); i++) {
            JSONObject jsonDeal = dealData.getJSONObject(i);
            Deal deal = new Deal();

            deal.setDealTitle(jsonDeal.getString(""));

            deals.add(deal);
        }

        return new ArrayList<Deal>();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    protected Location getLocation() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        return location;
    }

}
