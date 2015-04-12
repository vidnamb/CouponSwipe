package couponswipe.cmu.edu.couponswipe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import couponswipe.cmu.edu.couponswipe.R;

public class DealPreferencesActivity extends Activity {

    String[] dealCategories = {
            "Food & Drink",
            "Things To Do",
            "Beauty & Spas",
            "Health & Fitness",
            "Automotive",
            "Electronics",
            "Home & Garden",
            "Travel",
            "Gift Ideas"
    };

    ListView dealCategoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_preferences);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        dealCategoriesListView = (ListView) findViewById(R.id.dealCategoriesListView);
        dealCategoriesListView.setChoiceMode(dealCategoriesListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> adapter = (new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, dealCategories));
        dealCategoriesListView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deal_preferences, menu);
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
