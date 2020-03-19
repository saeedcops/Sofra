package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.category.CategoryData;
import com.cops.sofra.databinding.ItemFoodCategoryBinding;
import com.cops.sofra.databinding.ItemSelectedOrderBinding;
import com.cops.sofra.utils.SelectedOrder;

import java.util.ArrayList;
import java.util.List;

public class SelectedOrderAdapter extends RecyclerView.Adapter<SelectedOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<SelectedOrder> selectedOrders = new ArrayList<>();

    public SelectedOrderAdapter(Activity activity, List<SelectedOrder> selectedOrders) {
        this.context = activity;
        this.activity=activity;
        this.selectedOrders = selectedOrders;
    }



    @Override
    public SelectedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemSelectedOrderBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_selected_order, parent, false);

        return new SelectedOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(SelectedOrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(selectedOrders.get(position).getImageUrl()).into(holder.binding.selectedOrderAdapterIv);
        holder.binding.selectedOrderAdapterTvName.setText(selectedOrders.get(position).getName());
        holder.binding.selectedOrderAdapterPrice.setText(selectedOrders.get(position).getPrice());
        holder.binding.selectedOrderAdapterTvCount.setText(selectedOrders.get(position).getCount()+"");

    }

//

    private void setAction(final SelectedOrderAdapter.ViewHolder holder, final int position) {
//        holder.binding.restaurantAdapterRlParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
//                        .addToBackStack(null)
//                        .replace(R.id.home_activity_fl_frame, new RestaurantItemsFragment())
//                        .commit();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return selectedOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemSelectedOrderBinding binding;


        public ViewHolder(@NonNull  ItemSelectedOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

