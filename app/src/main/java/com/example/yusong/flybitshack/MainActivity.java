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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void goToZone(View view) {
        Intent intent = new Intent(this, ZonesActivity.class);

        startActivity(intent);
    }
}
