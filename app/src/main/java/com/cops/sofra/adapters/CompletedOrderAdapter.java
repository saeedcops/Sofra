package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.cops.sofra.databinding.ItemOrderCompletedBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class CompletedOrderAdapter extends RecyclerView.Adapter<CompletedOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MyOrderData> myOrderData = new ArrayList<>();

    public CompletedOrderAdapter(Activity activity, List<MyOrderData> restaurantDataList) {
        this.context = activity;
        this.activity = activity;
        this.myOrderData = restaurantDataList;
    }


    @Override
    public CompletedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemOrderCompletedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_order_completed, parent, false);

        return new CompletedOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(CompletedOrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOrderData.get(position).getClient().getPhotoUrl()).into(holder.binding.orderCompletedAdapterIv);

        holder.binding.orderCompletedAdapterName.setText(myOrderData.get(position).getClient().getName());
        try {

            holder.binding.orderCompletedAdapterNumber.setText(activity.getString(R.string.order_number) + myOrderData.get(position).getItems().get(0).getPivot().getOrderId());

        } catch (IndexOutOfBoundsException i) {
            Log.i("error", i.getMessage());
        }

        holder.binding.orderCompletedAdapterTotal.setText(activity.getString(R.string.total_cost) + " : " + myOrderData.get(position).getTotal() + " $");
        holder.binding.orderCompletedAdapterAddress.setText(activity.getString(R.string.address) + " : " + myOrderData.get(position).getAddress());

        if (myOrderData.get(position).getState().equals("delivered")) {

            holder.binding.orderCompletedAdapterBtnRefused.setVisibility(View.GONE);
            holder.binding.orderCompletedAdapterBtnDone.setVisibility(View.VISIBLE);
        } else if (myOrderData.get(position).getState().equals("rejected")) {

            holder.binding.orderCompletedAdapterBtnRefused.setVisibility(View.VISIBLE);
            holder.binding.orderCompletedAdapterBtnDone.setVisibility(View.GONE);
        }
    }

//

    private void setAction(final CompletedOrderAdapter.ViewHolder holder, final int position) {


        holder.binding.orderCompletedAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                OrderViewDialog viewDialog =new OrderViewDialog(myOrderData.get(position).getId());
//                viewDialog.show(((BaseActivity) activity).getSupportFragmentManager(), "dialog");

                int orderId= myOrderData.get(position).getId();


                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new ViewOrderFragment(orderId)).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemOrderCompletedBinding binding;


        public ViewHolder(@NonNull ItemOrderCompletedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

