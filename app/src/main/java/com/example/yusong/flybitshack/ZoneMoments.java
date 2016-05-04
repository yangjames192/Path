package com.example.yusong.flybitshack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flybits.core.api.models.GeoPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class ZoneMoments extends AppCompatActivity implements OnMapReadyCallback {
    public double la;
    public double lg;

    public double sx;
    public double sy;

    ArrayList<GeoPoint> shapes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_moments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent(); // gets the previously created intent
        String zoneName = intent.getStringExtra("zoneName");
        la = intent.getDoubleExtra("latitude", 0);
        lg = intent.getDoubleExtra("longitude", 0);
        shapes = intent.getParcelableArrayListExtra("shapes");
        GeoPoint g = shapes.get(0);
        sx =shapes.get(0).lat;
        sy = shapes.get(0).lng;

        TextView tx = (TextView)findViewById(R.id.zoneName);
        tx.setText(zoneName);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMSMessage();
                Snackbar.make(view, "Message Sent", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    protected void sendSMSMessage() {
        //Log.i("Send SMS", "");
        String phoneNo = "4166552252";//phoneNumber.getText().toString();
        String message = "Need Help!!";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(la, lg);

        //map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));

        LatLng l = new LatLng(sx, sy);

        //map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 16));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(l));

        PolygonOptions polygonOptions = new PolygonOptions();
        for(int i = 0;i<shapes.size();i++) {
            polygonOptions.add(new LatLng((shapes.get(i)).lat, shapes.get(i).lng));
        }
        Polygon polygon = map.addPolygon(polygonOptions
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

    }

}
