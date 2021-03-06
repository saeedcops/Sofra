package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.cops.sofra.R;
import com.cops.sofra.data.model.OrderItem;
import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.cops.sofra.databinding.ItemOrderBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.viewModel.room.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<Item> orderItems = new ArrayList<>();



    public CartAdapter(Activity activity, List<Item> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.orderItems = restaurantDataList;


    }



    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemOrderBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_order, parent, false);

        return new CartAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(CartAdapter.ViewHolder holder, int position) {


        holder.binding.cartAdapterTvName.setText(orderItems.get(position).getName());
        holder.binding.cartAdapterTvPrice.setText(orderItems.get(position).getPrice()+" EGP");
        holder.binding.cartAdapterTvCount.setText(activity.getString(R.string.qty)+" "+orderItems.get(position).getPivot().getQuantity());

    }



    private void setAction(final CartAdapter.ViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemOrderBinding binding;


        public ViewHolder(@NonNull  ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }



    }


}

