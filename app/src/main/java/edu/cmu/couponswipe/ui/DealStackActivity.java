package edu.cmu.couponswipe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.ui.intents.Intents;

/**
 * MainActivity - renamed to DealStackActivity
 * activity_main.xml renamed to activity_deal_stack
 *
 */

public class DealStackActivity extends Activity {

    public static final String TAG = DealStackActivity.class.getSimpleName();

    private int currentDemoDeal = 0;    //0,1,2 represent three sample deals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_stack);

//        ParseUser currentUser = ParseUser.getCurrentUser();
//        if (currentUser == null) {
//            navigateToLogin();
//        }
//        else {
//            Log.i(TAG, currentUser.getUsername());
//        }

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

}
