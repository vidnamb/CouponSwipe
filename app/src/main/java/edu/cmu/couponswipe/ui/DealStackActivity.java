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

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.dao.DealHistoryDAO;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.model.DealHistory;
import edu.cmu.couponswipe.sessions.Current;
import edu.cmu.couponswipe.sessions.SessionManager;
import edu.cmu.couponswipe.ui.intents.Intents;

public class DealStackActivity extends Activity {

    public static final String TAG = DealStackActivity.class.getSimpleName();

    // https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_203792_212556_0&lat=37.398873&lng=-122.071806&radius=10&offset=0&limit=20
    private static final String apiToken = "US_AFF_0_203792_212556_0";
    private static final int numDeals = 10;

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

        ApplicationContextProvider.setDealStackActivityContext(this);

        // Session class instance
        session = new SessionManager(getApplicationContext());
        // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        System.out.println("**************"+ Current.email);
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_LASTNAME);
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        for(final Deal deal : deals) {
            try {
                CardModel card = new CardModel(deal.getDealTitle(), deal.getDealAmount(), drawableFromUrl(deal.getLargeImageUrl()));
                adapter.add(card);

                card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                    @Override
                    public void onLike() {
                        Log.d("Swipeable Card", "I liked it");

                        //Add to db
                        DealHistoryDAO dealHistoryDAO = new DealHistoryDAO(ApplicationContextProvider.getDealStackActivityContext());
                        DealHistory dealHistory = new DealHistory(Current.email, deal.getDealUuid());
                        dealHistoryDAO.addDealHistory(dealHistory);

                        System.out.println("****Liked***");
                        try{
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            System.out.println(dateFormat.format(date));
                            JSONObject objHistory = new JSONObject();
                            objHistory.put("action", "shortlisted");
                            objHistory.put("createdAt", dateFormat.format(date));
                            objHistory.put("dealUuid", deal.getDealUuid());
                            objHistory.put("email", Current.email);
                            objHistory.put("updatedAt", dateFormat.format(date));
                            String history = objHistory.toString();

                            DefaultHttpClient httpclientHistory = new DefaultHttpClient();
                            HttpPost httpostHistory = new HttpPost("http://10.0.3.2:8080/history/add");
                            StringEntity seHist = new StringEntity(history);

                            httpostHistory.setEntity(seHist);
                            httpostHistory.setHeader("Accept", "application/json");
                            httpostHistory.setHeader("Content-type", "application/json");

                            ResponseHandler responseHandlerHist = new BasicResponseHandler();
                            String responseHistory = (String) httpclientHistory.execute(httpostHistory, responseHandlerHist);


                            JSONObject obj = new JSONObject();
                        obj.put("dealAmount",deal.getDealAmount());
                        obj.put("dealBuyUrl", deal.getDealBuyUrl());
                        obj.put("dealCategory", deal.getDealCategory());
                        obj.put("dealCurrency", deal.getDealCurrency());
                        obj.put("dealDescription", deal.getDealDescription());
                        obj.put("dealExpiryDate", deal.getDealExpiryDate());
                        obj.put("dealLatitude", deal.getDealLatitude());
                        obj.put("dealLocation",deal.getDealLocation());
                        obj.put("dealLongitude", deal.getDealLongitude());
                        obj.put("dealStartDate", deal.getDealStartDate());
                        obj.put("dealTitle", deal.getDealTitle());
                        obj.put("dealUuid", deal.getDealUuid());
                        obj.put("largeImageUrl", deal.getLargeImageUrl());
                        obj.put("mediumImageUrl", deal.getMediumImageUrl());
                        obj.put("merchantName", deal.getLargeImageUrl());
                        obj.put("merchantUrl", deal.getMerchantName());
                        obj.put("merchantUuid", deal.getMerchantUuid());
                        obj.put("smallImageUrl", deal.getSmallImageUrl());

                        String deal = obj.toString();
                        DefaultHttpClient httpclient = new DefaultHttpClient();
                        HttpPost httpost = new HttpPost("http://10.0.3.2:8080/deal/add");
                        StringEntity se = new StringEntity(deal);

                        httpost.setEntity(se);
                        httpost.setHeader("Accept", "application/json");
                        httpost.setHeader("Content-type", "application/json");

                        ResponseHandler responseHandler = new BasicResponseHandler();
                        String response = (String) httpclient.execute(httpost, responseHandler);
                        }catch(JSONException e){
                            Toast.makeText(getApplicationContext(), "JSON", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            Toast.makeText(getApplicationContext(), "UserName already taken", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("#######");

                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("%%%%%");
                            Toast.makeText(getApplicationContext(), "Problem with the connection the server", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDislike() {
                        System.out.println("****disLiked***");

                        //Add to db
                        DealHistoryDAO dealHistoryDAO = new DealHistoryDAO(ApplicationContextProvider.getDealStackActivityContext());
                        DealHistory dealHistory = new DealHistory(Current.email, deal.getDealUuid());
                        dealHistoryDAO.addDealHistory(dealHistory);

                        Log.d("Swipeable Card", "I did not liked it");
                    }
                });

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
        System.out.println("************** getdeals");

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
                            //handle non ok response
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
            System.out.println("************** No network");

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

                deal.setDealStartDate(jsonDeal.getString("startAt"));
                deal.setDealExpiryDate(jsonDeal.getString("endAt"));
//              deal.setDealCategory(jsonDeal.getString(""));
                deal.setSmallImageUrl(jsonDeal.getString("smallImageUrl"));
                deal.setMediumImageUrl(jsonDeal.getString("mediumImageUrl"));
                deal.setLargeImageUrl(jsonDeal.getString("largeImageUrl"));

                //Merchant Details
                JSONObject merchantData = jsonDeal.getJSONObject("merchant");
                deal.setMerchantUuid(merchantData.getString("uuid"));
                deal.setMerchantName(merchantData.getString("name"));
                deal.setMerchantUrl(merchantData.getString("websiteUrl"));

                //Deal Details URL
                //deal.setDealBuyUrl(jsonDeal.getString("dealUrl"));

                //Location, Price, Deal Buy URL
                JSONArray optionsData = jsonDeal.getJSONArray("options");
                deal.setDealBuyUrl(optionsData.getJSONObject(0).getString("buyUrl"));

                JSONArray locationData = optionsData.getJSONObject(0).getJSONArray("redemptionLocations");
                deal.setDealLocation(locationData.getJSONObject(0).getString("name") + " " + locationData.getJSONObject(0).getString("city"));
                deal.setDealLatitude(locationData.getJSONObject(0).getString("lat"));
                deal.setDealLongitude(locationData.getJSONObject(0).getString("lng"));

                JSONObject priceData = optionsData.getJSONObject(0).getJSONObject("price");
                deal.setDealAmount(priceData.getString("formattedAmount"));
                deal.setDealCurrency(priceData.getString("currencyCode"));

                deals.add(deal);


            } catch (Exception e) {
                Log.e(TAG, "Exception caught: ", e);
                e.printStackTrace();
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
        return scaleImage(new BitmapDrawable(x), 1);
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
