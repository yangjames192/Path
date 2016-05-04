package com.example.yusong.flybitshack;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.flybits.core.api.Flybits;
import com.flybits.core.api.FlybitsOptions;
import com.flybits.core.api.events.EventMomentNotification;
import com.flybits.core.api.interfaces.IRequestCallback;
import com.flybits.core.api.interfaces.IRequestPaginationCallback;
import com.flybits.core.api.models.GeoPoint;
import com.flybits.core.api.models.Pagination;
import com.flybits.core.api.models.Zone;
import com.flybits.core.api.models.ZoneMoment;
import com.flybits.core.api.utils.filters.ZoneMomentOptions;
import com.flybits.core.api.utils.filters.ZoneOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ZonesActivity extends AppCompatActivity {
    public ListView lv;
    public Context context;
    public ArrayList<String> urls = null;
    public ArrayList<String> text = null;
    public ArrayList<String> zoneIds = null;
    public ArrayList<GeoPoint> geoPoints = null;
    public ArrayList<ArrayList<GeoPoint>> shape = null;
    ZoneListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        urls = new ArrayList<String>();
        text = new ArrayList<String>();
        zoneIds = new ArrayList<String>();
        geoPoints = new ArrayList<GeoPoint>();
        shape = new ArrayList<ArrayList<GeoPoint>>();

        adapter = new ZoneListViewAdapter(this, urls, text);

        context = this;
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Log.d("id", " "+position);
                //position /= 2;
                getZoneMoment(text.get(position), zoneIds.get(position), geoPoints.get(position), shape.get(position));
            }
        });

        //new AddStringTask().execute();

        ArrayList<String> listOfLanguages = new ArrayList<>();
        listOfLanguages.add("en");
        FlybitsOptions builder = new FlybitsOptions.Builder(this)
                .setDebug(true)
                .setLocalization(listOfLanguages)
                        //    .setLocalization()
                .build();

        //Initialize the FlybitsOptions
        Flybits.include(this).initialize(builder);

        getZones();
    }

    public void getZones() {
        ZoneOptions option = new ZoneOptions.Builder().build();

        Flybits.include(this).getZones(option, new IRequestPaginationCallback<ArrayList<Zone>>() {
            @Override
            public void onSuccess(ArrayList<Zone> zones, Pagination pagination) {
                for(int i = 0; i < zones.size(); ++i) {
                    Zone zone = zones.get(i);

                    Log.d("zones: ", zone.getName());
                    Log.d("zones: ", zone.id);
                    Log.d("zones icon: ", zone.getIcon());

                    zoneIds.add(zone.id);
                    text.add(zone.getName());
                    urls.add(zone.getIcon());
                    geoPoints.add(zone.addressCoordinates);

                    ArrayList gs = zone.shapes;

                    ArrayList<GeoPoint> temp = new ArrayList<GeoPoint>();

                    for(int j=0;j<gs.size();++j) {
                        ArrayList<GeoPoint> ps = (ArrayList<GeoPoint>) gs.get(j);
                        for (int k = 0; k < ps.size(); k++) {
                            temp.add(ps.get(k));
                        }
                    }
                    shape.add(temp);

                    adapter.add(zone.getName(), zone.getIcon());
                }
            }

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onFailed(String s) {

            }

            @Override
            public void onCompleted() {
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void getZoneMoment(final String zoneName, String zoneId, final GeoPoint geoPoint, ArrayList<GeoPoint> sp) {
        ZoneMomentOptions options = new ZoneMomentOptions.Builder().addZoneId(zoneId).build();
        final ArrayList<GeoPoint> shape_points = sp;

        Flybits.include(this).getZoneMoments(options, new IRequestPaginationCallback<ArrayList<ZoneMoment>>() {
            @Override
            public void onSuccess(ArrayList<ZoneMoment> zoneMoments, Pagination pagination) {
                Log.d("zonement ", "szie" + zoneMoments.size());
                for (int i = 0; i < zoneMoments.size(); ++i) {
                    ZoneMoment moment = zoneMoments.get(i);

                    Log.d("zonement name : "+i, moment.getName());
                    Log.d("zonement id : "+i, moment.id);
                }

                Intent intent = new Intent(ZonesActivity.this, ZoneMoments.class);

                intent.putExtra("zoneName",zoneName);
                intent.putExtra("latitude", geoPoint.lat);
                intent.putExtra("longitude", geoPoint.lng);

                intent.putExtra("shapes", shape_points);

                startActivity(intent);
            }

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onFailed(String s) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
