package edu.cmu.couponswipe.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.cmu.couponswipe.R;

public class ViewDealActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deal);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String deal_title = intent.getExtras().getString("DealTitle");
        String deal_amount = intent.getExtras().getString("DealAmount");
        String deal_image_url = intent.getExtras().getString("DealImage");

        TextView titleTextView = (TextView) findViewById(R.id.TitleTextView);
        TextView amountTextView = (TextView) findViewById(R.id.AmountTextView);
        ImageView imageView = (ImageView) findViewById(R.id.ImageView);

        titleTextView.setText(deal_title);
        amountTextView.setText(deal_amount);

        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(new URL(deal_image_url).openConnection().getInputStream()));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_deal, menu);
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
}
