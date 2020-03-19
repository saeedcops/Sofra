package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviewsData;
import com.cops.sofra.databinding.ItemFoodListBinding;
import com.cops.sofra.databinding.ItemRateCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class RestaurantReviewAdapter extends RecyclerView.Adapter<RestaurantReviewAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<RestaurantReviewsData> restaurantReviewsData = new ArrayList<>();

    public RestaurantReviewAdapter(Activity activity, List<RestaurantReviewsData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.restaurantReviewsData = restaurantDataList;
    }



    @Override
    public RestaurantReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRateCommentBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_rate_comment, parent, false);

        return new RestaurantReviewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantReviewAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantReviewAdapter.ViewHolder holder, int position) {


        holder.binding.restaurantReviewsAdapterTvName.setText(restaurantReviewsData.get(position).getClient().getName());
        holder.binding.restaurantReviewsAdapterTvComment.setText(restaurantReviewsData.get(position).getComment());

        if (restaurantReviewsData.get(position).getRate().equals("1")) {
            holder.binding.restaurantReviewsAdapterIv.setImageResource(R.drawable.angry);
        }else if(restaurantReviewsData.get(position).getRate().equals("2")){
            holder.binding.restaurantReviewsAdapterIv.setImageResource(R.drawable.bad);
        }else if(restaurantReviewsData.get(position).getRate().equals("3")){
            holder.binding.restaurantReviewsAdapterIv.setImageResource(R.drawable.good);
        }else if(restaurantReviewsData.get(position).getRate().equals("4")){
            holder.binding.restaurantReviewsAdapterIv.setImageResource(R.drawable.great);
        }else if(restaurantReviewsData.get(position).getRate().equals("5")){
            holder.binding.restaurantReviewsAdapterIv.setImageResource(R.drawable.love);
        }


    }

//

    private void setAction(final RestaurantReviewAdapter.ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return restaurantReviewsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRateCommentBinding binding;


        public ViewHolder(@NonNull  ItemRateCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

