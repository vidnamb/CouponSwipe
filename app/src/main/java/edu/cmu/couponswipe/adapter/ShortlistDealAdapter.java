package edu.cmu.couponswipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.model.Deal;
import edu.cmu.couponswipe.ui.ApplicationContextProvider;
import edu.cmu.couponswipe.ui.DealShortlistActivity;

/**
 * Created by lloyddsilva on 4/4/15.
 */
public class ShortlistDealAdapter extends BaseAdapter{

    private String TAG = "ShortlistDealAdapter";

    private Context context;
    private Deal[] deals;

    public ShortlistDealAdapter(Context context, Deal[] deals) {
        this.context = context;
        this.deals = deals;
    }

    @Override
    public int getCount() {
        return deals.length;
    }

    @Override
    public Object getItem(int position) {
        return deals[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
      final  Deal deal = deals[position];

        if(convertView == null) {
            // brand new view
            convertView = LayoutInflater.from(context).inflate(R.layout.deal_shortlist_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.dealIconImageView = (ImageView) convertView.findViewById(R.id.dealIconImageView);
        holder.dealTitleTextView = (TextView) convertView.findViewById(R.id.dealTitleTextView);
        holder.deleteDealButton = (Button) convertView.findViewById(R.id.deleteDealButton);
        holder.viewDealButton = (Button) convertView.findViewById(R.id.viewDealButton);
        holder.buyDealButton = (Button) convertView.findViewById(R.id.buyDealButton);

        holder.viewDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

//                Intent intentToViewDeal = new Intent(ApplicationContextProvider.getDealShortlistActivityContext(), ViewDealActivity.class);
//                ApplicationContextProvider.getDealShortlistActivityContext().startActivity(intentToViewDeal);
            }
        });

        holder.deleteDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.3.2:8080/history/delete/"+ deal.getDealUuid() +"/xyz@a.com")
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        //handle failure
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        Intent intentToViewShortlistDeal = new Intent(ApplicationContextProvider.getDealShortlistActivityContext(), DealShortlistActivity.class);
                        ApplicationContextProvider.getDealShortlistActivityContext().startActivity(intentToViewShortlistDeal);
                    }
                });


            }
        });

        holder.buyDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                    }
        });

        try {
            holder.dealIconImageView.setImageBitmap(BitmapFactory.decodeStream(new URL("https://s-media-cache-ak0.pinimg.com/236x/22/c9/1e/22c91e92d24adeba4c329c0034ddbbbf.jpg").openConnection().getInputStream()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        holder.dealTitleTextView.setText(deal.getDealTitle());

        return convertView;
    }

    private static class ViewHolder {
        ImageView dealIconImageView;
        TextView dealTitleTextView;
        Button deleteDealButton;
        Button viewDealButton;
        Button buyDealButton;

    }


}
