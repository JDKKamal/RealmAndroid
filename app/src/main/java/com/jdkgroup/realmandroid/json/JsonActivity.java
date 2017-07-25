
package com.jdkgroup.realmandroid.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jdkgroup.realmandroid.R;
import com.jdkgroup.realmandroid.json.adapter.CityAdapter;
import com.jdkgroup.realmandroid.json.dao.City;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class JsonActivity extends AppCompatActivity {

    private AppCompatActivity mAppCompatActivity;

    private RecyclerView mRecyclerView;
    private CityAdapter mAdapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_json);

        mAppCompatActivity = this;

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        //Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mAppCompatActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load from file "cities.json" first time
        if (mAdapter == null) {
            List<City> cities = null;
            try {
                cities = loadCities();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mAdapter = new CityAdapter(mAppCompatActivity, cities);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public List<City> loadCities() throws IOException {

        //loadJsonFromStream();
        loadJsonFromJsonObject();
        //loadJsonFromString();
        loadDaoObject();

        return realm.where(City.class).findAll();
    }

    private void loadJsonFromStream() throws IOException {
        InputStream stream = getAssets().open("cities.json");
        realm.beginTransaction();
        try {
            realm.createAllFromJson(City.class, stream);
            realm.commitTransaction();
        } catch (IOException e) {
            realm.cancelTransaction();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private void loadJsonFromJsonObject() {
        Map<String, String> city = new HashMap<>();
        city.put("UUID", UUID.randomUUID().toString());
        city.put("name", "Ravani (Kuba)");
        city.put("votes", "79");
        final JSONObject json = new JSONObject(city);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(City.class, json);
            }
        });
    }

    //TODO INSERT TO THE REALM
    private void loadDaoObject() {
        City city = new City(UUID.randomUUID().toString(), "Junagadh", 85);
        realm.beginTransaction();
        realm.copyToRealm(city);
        realm.commitTransaction();

    }

    /*private void loadJsonFromString() {
        final String json = "{ name: \"Aarhus\", votes: 99 }";

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(City.class, json);
            }
        });
    }*/
}
