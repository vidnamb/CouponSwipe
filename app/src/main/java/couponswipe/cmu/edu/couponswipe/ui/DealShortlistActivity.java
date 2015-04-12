package couponswipe.cmu.edu.couponswipe.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import couponswipe.cmu.edu.couponswipe.R;
import couponswipe.cmu.edu.couponswipe.adapter.ShortlistDealAdapter;
import couponswipe.cmu.edu.couponswipe.model.Deal;
import couponswipe.cmu.edu.couponswipe.ui.intents.Intents;

public class DealShortlistActivity extends ListActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_shortlist);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Deal> shortlistedDealsList = new ArrayList<Deal>();
        shortlistedDealsList.add(new Deal("1"));
        shortlistedDealsList.add(new Deal("2"));
        shortlistedDealsList.add(new Deal("3"));

        Deal[] shortlistedDeals = shortlistedDealsList.toArray(new Deal[shortlistedDealsList.size()]);

        ShortlistDealAdapter adapter = new ShortlistDealAdapter(this, shortlistedDeals);
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
}
