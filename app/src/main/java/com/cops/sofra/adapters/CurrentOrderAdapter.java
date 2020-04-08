package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.acceptOrder.AcceptOrder;
import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.cops.sofra.databinding.ItemOrderCurrentBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOrderFragment;
import com.cops.sofra.ui.dialog.OrderRefuseDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MyOrderData> myOrderData = new ArrayList<>();

    public CurrentOrderAdapter(Activity activity, List<MyOrderData> restaurantDataList) {
        this.context = activity;
        this.activity = activity;
        this.myOrderData = restaurantDataList;
    }


    @Override
    public CurrentOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemOrderCurrentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_order_current, parent, false);

        return new CurrentOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(CurrentOrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOrderData.get(position).getClient().getPhotoUrl()).into(holder.binding.orderCurrentAdapterIv);

        holder.binding.orderCurrentAdapterName.setText(myOrderData.get(position).getClient().getName());
        try {

            holder.binding.orderCurrentAdapterNumber.setText(activity.getString(R.string.order_number) + myOrderData.get(position).getItems().get(0).getPivot().getOrderId());

        } catch (IndexOutOfBoundsException i) {
            Log.i("error", i.getMessage());
        }


        holder.binding.orderCurrentAdapterTotal.setText(activity.getString(R.string.total_cost) + " : " + myOrderData.get(position).getTotal() + " $");
        holder.binding.orderCurrentAdapterAddress.setText(activity.getString(R.string.address) + " : " + myOrderData.get(position).getAddress());

    }

//

    private void setAction(final CurrentOrderAdapter.ViewHolder holder, final int position) {

        holder.binding.orderCurrentAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OrderViewDialog viewDialog = new OrderViewDialog(myOrderData.get(position).getId());
//                viewDialog.show(((BaseActivity) activity).getSupportFragmentManager(), "dialog");
                int  orderId= myOrderData.get(position).getId();

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new ViewOrderFragment(orderId)).commit();

            }
        });


        holder.binding.orderCurrentAdapterBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String apiToken = "";
                if (LoadData(activity, "apiToken") != null) {

                    apiToken = LoadData(activity, "apiToken");

                }
                getClient().confirmOrderDelivery(apiToken, myOrderData.get(position).getId()).enqueue(new Callback<AcceptOrder>() {
                    @Override
                    public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                        try {
                            Log.i("resp", response.message());
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                myOrderData.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.i("ex", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<AcceptOrder> call, Throwable t) {
                        Log.i("failed", t.getMessage());
                    }
                });

            }
        });

        holder.binding.orderCurrentAdapterBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderRefuseDialog refuseDialog = new OrderRefuseDialog(myOrderData.get(position).getId());
                refuseDialog.show(((BaseActivity) activity).getSupportFragmentManager(), "dialog");





            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemOrderCurrentBinding binding;


        public ViewHolder(@NonNull ItemOrderCurrentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

