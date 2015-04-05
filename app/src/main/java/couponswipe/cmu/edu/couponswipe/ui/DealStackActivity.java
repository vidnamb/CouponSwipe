package couponswipe.cmu.edu.couponswipe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import couponswipe.cmu.edu.couponswipe.R;

/**
 * MainActivity - renamed to DealStackActivity
 * activity_main.xml renamed to activity_deal_stack
 *
 */

public class DealStackActivity extends Activity {

    private int currentDemoDeal = 0;    //0,1,2 represent three sample deals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_stack);


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

    public void likeDeal(View view){

        showNextDealInStack();
    }

    public void discardDeal(View view){

        showNextDealInStack();
    }

    public void showNextDealInStack(){

        currentDemoDeal++;
        if(currentDemoDeal>2)
            currentDemoDeal=0;

        ImageView dealImageView = (ImageView) findViewById(R.id.coupon_imageView);
        TextView dealTitleTextView = (TextView) findViewById(R.id.deal_title_textView);
        TextView dealStoreTextView = (TextView) findViewById(R.id.deal_store_textView);
        TextView dealDistanceTextView = (TextView) findViewById(R.id.deal_distance_textView);
        TextView dealPriceTextView = (TextView) findViewById(R.id.deal_price_textView);

        switch (currentDemoDeal)
        {
            case 0:
            {
                dealImageView.setImageResource(R.drawable.easter_coupon);
                dealTitleTextView.setText(getString(R.string.demo_coupon_title_1));
                dealStoreTextView.setText(getString(R.string.demo_coupon_store_1));
                dealDistanceTextView.setText(getString(R.string.demo_coupon_distance_1));
                dealPriceTextView.setText(getString(R.string.demo_coupon_price_1));
                break;
            }
            case 1:
            {
                dealImageView.setImageResource(R.drawable.omaha_steak);
                dealTitleTextView.setText(getString(R.string.demo_coupon_title_2));
                dealStoreTextView.setText(getString(R.string.demo_coupon_store_2));
                dealDistanceTextView.setText(getString(R.string.demo_coupon_distance_2));
                dealPriceTextView.setText(getString(R.string.demo_coupon_price_2));
                break;
            }
            case 2:
            {
                dealImageView.setImageResource(R.drawable.golden_gate);
                dealTitleTextView.setText(getString(R.string.demo_coupon_title_3));
                dealStoreTextView.setText(getString(R.string.demo_coupon_store_3));
                dealDistanceTextView.setText(getString(R.string.demo_coupon_distance_3));
                dealPriceTextView.setText(getString(R.string.demo_coupon_price_3));
                break;
            }
        }

    }

    public void openUserProfile(View view)
    {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void openShortlist(View view)
    {
        Intent intent = new Intent(this, DealShortlistActivity.class);
        startActivity(intent);
    }

    public void openUserPreferences(View view){
        Intent intent = new Intent(this, DealPreferencesActivity.class);
        startActivity(intent);
    }

}
