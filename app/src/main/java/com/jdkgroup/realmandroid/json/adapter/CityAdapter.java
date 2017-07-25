package com.jdkgroup.realmandroid.json.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdkgroup.realmandroid.R;
import com.jdkgroup.realmandroid.json.dao.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private AppCompatActivity mAppCompatActivity;
    private List<City> alCity;

    public CityAdapter(AppCompatActivity mAppCompatActivity, List<City> alCity) {
        this.mAppCompatActivity = mAppCompatActivity;
        this.alCity = alCity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_realm_json, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        City  city = alCity.get(position);
        viewHolder.tvAppName.setText(city.name);
        viewHolder.tvAppUUID.setText(city.UUID);
    }

    @Override
    public int getItemCount() {
        return (null != alCity ? alCity.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final AppCompatTextView tvAppName, tvAppUUID;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAppName = (AppCompatTextView) itemView.findViewById(R.id.tvAppName);
            tvAppUUID  = (AppCompatTextView) itemView.findViewById(R.id.tvAppUUID);
        }
    }

}

