package edu.cmu.couponswipe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.sessions.SessionManager;
import edu.cmu.couponswipe.ui.intents.Intents;

public class DealStackActivity extends Activity {

    public static final String TAG = DealStackActivity.class.getSimpleName();

    // https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_203792_212556_0&lat=37.398873&lng=-122.071806&radius=10&offset=0&limit=20
    private static final String apiToken = "US_AFF_0_203792_212556_0";
    private static final int numDeals = 5;

    // Session Manager Class
    SessionManager session;

    // Location
    private LocationManager locationManager;
    private String provider;

    //Deals
    ArrayList<Deal> deals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_stack);

        // Session class instance
        session = new SessionManager(getApplicationContext());
        // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);
        int dealRadius = Integer.parseInt(user.get(SessionManager.KEY_RADIUS));
        String dealCategories = user.get(SessionManager.KEY_DEAL_CATEGORIES);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Location location = getLocation();

        if(location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            getDeals(latitude, longitude, dealRadius);
        } else {
            getDeals();
        }
    }

    //Set up Cards for Swiping
    public void buildSwipeCards(ArrayList<Deal> deals)
    {
        CardContainer mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Disordered);
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        for(Deal deal : deals) {
            try {
                CardModel card = new CardModel(deal.getDealTitle(), deal.getDealAmount(), drawableFromUrl(deal.getLargeImageUrl()));
                adapter.add(card);
            } catch (Exception e) {
                Log.e(TAG, "Exception caught: ", e);
            }
        }

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

    public void getDeals() {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken +  "&offset=0&limit=" + numDeals;

        callDealAPI(dealUrl);
    }

    public void getDeals(String dealCategories) {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken +  "&offset=0&limit=" + numDeals;

        callDealAPI(dealUrl);
    }

    public void getDeals(double latitude, double longitude, int dealRadius) {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken + "&lat=" + latitude + "&lng=" + longitude
                + "&radius=" + dealRadius + "&offset=0&limit=" + numDeals;

        callDealAPI(dealUrl);

    }

    public void getDeals(double latitude, double longitude, int dealRadius, String dealCategories) {
        String dealUrl = "https://partner-api.groupon.com/deals.json?tsToken=" + apiToken + "&lat=" + latitude + "&lng=" + longitude
                + "&radius=" + dealRadius + "&offset=0&limit=" + numDeals;

        callDealAPI(dealUrl);

    }

    private void callDealAPI(String url) {

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            deals = parseDeals(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    buildSwipeCards(deals);
                                }
                            });
                        } else {

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
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


    private ArrayList<Deal> parseDeals(String jsonData) throws JSONException {
        JSONObject response = new JSONObject(jsonData);
        JSONArray dealData = response.getJSONArray("deals");

        ArrayList<Deal> deals = new ArrayList<Deal>();

        for (int i = 0; i < dealData.length(); i++) {
            JSONObject jsonDeal = dealData.getJSONObject(i);
            Deal deal = new Deal();

            try {
                deal.setDealUuid(jsonDeal.getString("uuid"));
                deal.setDealTitle(jsonDeal.getString("announcementTitle"));
                deal.setDealDescription(jsonDeal.getString("title"));
//            deal.setDealLocation(jsonDeal.getString(""));
//            deal.setDealLatitude(jsonDeal.getString(""));
//            deal.setDealLongitude(jsonDeal.getString(""));
//            deal.setDealAmount(jsonDeal.getString(""));
//            deal.setDealCurrency(jsonDeal.getString(""));
                deal.setDealStartDate(jsonDeal.getString("startAt"));
                deal.setDealExpiryDate(jsonDeal.getString("endAt"));
//            deal.setDealCategory(jsonDeal.getString(""));
                deal.setSmallImageUrl(jsonDeal.getString("smallImageUrl"));
                deal.setMediumImageUrl(jsonDeal.getString("mediumImageUrl"));
                deal.setLargeImageUrl(jsonDeal.getString("largeImageUrl"));
//            deal.setMerchantUuid(jsonDeal.getString(""));
//            deal.setMerchantName(jsonDeal.getString(""));
//            deal.setMerchantUrl(jsonDeal.getString(""));
                deal.setDealBuyUrl(jsonDeal.getString("dealUrl")); //Options->buyUrl

                deals.add(deal);
            } catch (Exception e) {
                Log.e(TAG, "Exception caught: ", e);
            }

        }

        return deals;
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
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        return location;
    }

    public Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return scaleImage(new BitmapDrawable(x), 20);
    }

    public Drawable scaleImage (Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable)image).getBitmap();

        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        image = new BitmapDrawable(getResources(), bitmapResized);

        return image;

    }

}
