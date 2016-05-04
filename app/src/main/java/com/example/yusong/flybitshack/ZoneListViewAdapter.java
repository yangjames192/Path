package com.example.yusong.flybitshack;

/**
 * Created by Yusong on 2016-04-10.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputBinding;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class ZoneListViewAdapter extends BaseAdapter {
    ArrayList<String> photos_url;
    Context context;
    ArrayList<String> information;
    private static LayoutInflater inflater=null;
    public ZoneListViewAdapter(AppCompatActivity mainActivity, ArrayList<String> photos, ArrayList<String> info) {
        // TODO Auto-generated constructor stub
        this.photos_url=photos;
        context=mainActivity;
        this.information=info;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return photos_url.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public class Holder
    {
        ImageView img1;
        ImageView img2;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.zone_list, null);

        ImageView iv = (ImageView)rowView.findViewById(R.id.myImageView);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Picasso.with(context).load(photos_url.get(position))
                .resize(width, height/2)
                .centerCrop()
                .into(iv);

        Log.d("adapter: ", position + " "+information.get(position));
        TextView tv = (TextView) rowView.findViewById(R.id.textView);
        tv.setText(information.get(position));

        return rowView;
    }

    public void add(String url, String text) {
        photos_url.add(url);
        information.add(text);
    }
}