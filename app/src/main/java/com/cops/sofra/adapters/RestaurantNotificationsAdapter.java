package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.sofra.R;
import com.cops.sofra.data.model.notifications.NotificationsData;
import com.cops.sofra.databinding.ItemNotificationBinding;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantNotificationsAdapter extends RecyclerView.Adapter<RestaurantNotificationsAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<NotificationsData> notificationsData = new ArrayList<>();
    private Date createdAt;
    private Date now = new Date();

    public RestaurantNotificationsAdapter(Activity activity, List<NotificationsData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.notificationsData = restaurantDataList;
    }



    @Override
    public RestaurantNotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemNotificationBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_notification, parent, false);

        return new RestaurantNotificationsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantNotificationsAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantNotificationsAdapter.ViewHolder holder, int position) {

        holder.binding.restaurantNotificationAdapterTvNotify.setText(notificationsData.get(position).getContent());



        try {

            createdAt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(notificationsData.get(position).getCreatedAt());
            DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

            long diff = now.getTime() - createdAt.getTime();

            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

            int diffHours = (int) (diff / (60 * 60 * 1000));

            int diffMin = (int) (diff / (60 * 1000));

            if(diffDays>0){
                holder.binding.restaurantNotificationAdapterTvTime.setText(activity.getString(R.string.since)+" "+diffDays+" "+activity.getString(R.string.day));
            }else if(diffHours>0){

                holder.binding.restaurantNotificationAdapterTvTime.setText(activity.getString(R.string.since)+" "+diffHours+" "+activity.getString(R.string.hours));
            }else {
                holder.binding.restaurantNotificationAdapterTvTime.setText(activity.getString(R.string.since)+" "+diffMin+" "+activity.getString(R.string.minute));

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private void setAction(final RestaurantNotificationsAdapter.ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return notificationsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNotificationBinding binding;


        public ViewHolder(@NonNull  ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

