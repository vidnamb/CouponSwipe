package couponswipe.cmu.edu.couponswipe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import couponswipe.cmu.edu.couponswipe.R;
import couponswipe.cmu.edu.couponswipe.ui.intents.Intents;

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

        adapter.add(new CardModel(getString(R.string.demo_coupon_title_1)+": "+getString(R.string.demo_coupon_price_1), getString(R.string.demo_coupon_store_1)+": "+getString(R.string.demo_coupon_distance_1), getResources().getDrawable(R.drawable.easter_coupon)));
        adapter.add(new CardModel(getString(R.string.demo_coupon_title_2)+": "+getString(R.string.demo_coupon_price_2), getString(R.string.demo_coupon_store_2)+": "+getString(R.string.demo_coupon_distance_2), getResources().getDrawable(R.drawable.omaha_steak)));
        adapter.add(new CardModel(getString(R.string.demo_coupon_title_3)+": "+getString(R.string.demo_coupon_price_3), getString(R.string.demo_coupon_store_3)+": "+getString(R.string.demo_coupon_distance_3), getResources().getDrawable(R.drawable.golden_gate)));

        mCardContainer.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void openUserProfile(View view)
    {
        Intents.openUserProfile(this);
    }

    public void openShortlist(View view)
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
