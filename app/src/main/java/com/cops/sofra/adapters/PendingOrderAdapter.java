package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.cops.sofra.databinding.ItemOrderPendingBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOrderFragment;
import com.cops.sofra.utils.HelperMethod;
import com.cops.sofra.ui.dialog.OrderRefuseDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MyOrderData> myOrderData = new ArrayList<>();

    public PendingOrderAdapter(Activity activity, List<MyOrderData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.myOrderData = restaurantDataList;
    }



    @Override
    public PendingOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemOrderPendingBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_order_pending, parent, false);

        return new PendingOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(PendingOrderAdapter.ViewHolder holder, int position) {

    //    if (myOrderData.get(position).getState().equals("pending")) {
            Glide.with(context).load(myOrderData.get(position).getClient().getPhotoUrl()).into(holder.binding.orderPendingAdapterIv);

            holder.binding.orderPendingAdapterName.setText(myOrderData.get(position).getClient().getName());
            holder.binding.orderPendingAdapterNumber.setText(activity.getString(R.string.order_number)+myOrderData.get(position).getItems().get(0).getPivot().getOrderId());
            holder.binding.orderPendingAdapterTotal.setText(activity.getString(R.string.total_cost)+" : "+myOrderData.get(position).getTotal() + " $");
            holder.binding.orderPendingAdapterAddress.setText(activity.getString(R.string.address)+" : "+myOrderData.get(position).getAddress());

//        }else if(!myOrderData.get(position).getState().equals("pending")){
//
//
//    }



    }

//

    private void setAction(final PendingOrderAdapter.ViewHolder holder, final int position) {

        holder.binding.orderPendingAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                OrderViewDialog viewDialog =new OrderViewDialog(myOrderData.get(position).getId());
//                viewDialog.show(((BaseActivity) activity).getSupportFragmentManager(), "dialog");
//

                int orderId= myOrderData.get(position).getId();;


                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new ViewOrderFragment(orderId)).commit();
            }
        });

        holder.binding.orderPendingAdapterBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.onPermission(activity);
                activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",myOrderData.get(position).getClient().getPhone(),"")));
            }
        });
        holder.binding.orderPendingAdapterBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apiToken="";
                if (LoadData(activity, "apiToken") != null) {

                    apiToken = LoadData(activity, "apiToken");

                }
                getClient().acceptOrder(apiToken,myOrderData.get(position).getId()).enqueue(new Callback<AcceptOrder>() {
                    @Override
                    public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                        try {

                            if (response.body().getStatus()==1) {
                                Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                myOrderData.remove(position);
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFailure(Call<AcceptOrder> call, Throwable t) {
                        Log.i("failed",t.getMessage());
                    }
                });

            }
        });

        holder.binding.orderPendingAdapterBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderRefuseDialog refuseDialog=new OrderRefuseDialog(myOrderData.get(position).getId());
                refuseDialog.show(((BaseActivity) activity).getSupportFragmentManager(),"dialog");

            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemOrderPendingBinding binding;


        public ViewHolder(@NonNull  ItemOrderPendingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

