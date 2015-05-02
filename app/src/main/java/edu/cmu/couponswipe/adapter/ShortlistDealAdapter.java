package edu.cmu.couponswipe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import edu.cmu.couponswipe.R;
import edu.cmu.couponswipe.model.Deal;

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

        if(convertView == null) {
            // brand new view
            convertView = LayoutInflater.from(context).inflate(R.layout.deal_shortlist_item, null);
            holder = new ViewHolder();
            holder.dealIconImageView = (ImageView) convertView.findViewById(R.id.dealIconImageView);
            holder.dealTitleTextView = (TextView) convertView.findViewById(R.id.dealTitleTextView);
            holder.deleteDealButton = (Button) convertView.findViewById(R.id.deleteDealButton);
            holder.viewDealButton = (Button) convertView.findViewById(R.id.viewDealButton);
            holder.buyDealButton = (Button) convertView.findViewById(R.id.buyDealButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.viewDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


            }
        });

        holder.deleteDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


            }
        });

        holder.buyDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


            }
        });

        Deal deal = deals[position];
        holder.dealIconImageView.setImageResource(R.drawable.pizza);
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

//    private Bitmap getImageBitmap(String url) {
//        Bitmap bm = null;
//        try {
//            URL aURL = new URL(url);
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//            Log.e(TAG, "Error getting bitmap", e);
//        }
//        return bm;
//    }
}
