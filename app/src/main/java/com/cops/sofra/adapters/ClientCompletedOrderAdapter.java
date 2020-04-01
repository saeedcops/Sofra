package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.clientOrder.ClientOrderData;
import com.cops.sofra.databinding.ItemClientOrderCompletedBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class ClientCompletedOrderAdapter extends RecyclerView.Adapter<ClientCompletedOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<ClientOrderData> myOrderData = new ArrayList<>();

    public ClientCompletedOrderAdapter(Activity activity, List<ClientOrderData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.myOrderData = restaurantDataList;
    }



    @Override
    public ClientCompletedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemClientOrderCompletedBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_client_order_completed, parent, false);

        return new ClientCompletedOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientCompletedOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(ClientCompletedOrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOrderData.get(position).getRestaurant().getPhotoUrl()).into(holder.binding.clientOrderCompletedAdapterIv);

       holder.binding.clientOrderCompletedAdapterName.setText(myOrderData.get(position).getRestaurant().getName());
       holder.binding.clientOrderCompletedAdapterNumber.setText(activity.getString(R.string.order_number)+myOrderData.get(position).getItems().get(0).getPivot().getOrderId());
        holder.binding.clientOrderCompletedAdapterTotal.setText(activity.getString(R.string.total_cost)+" : "+myOrderData.get(position).getTotal() + " $");
        holder.binding.clientOrderCompletedAdapterAddress.setText(activity.getString(R.string.address)+" : "+myOrderData.get(position).getAddress());

    if(myOrderData.get(position).getState().equals("declined")){

        holder.binding.clientOrderCompletedAdapterBtnCancel.setBackground(activity.getResources().getDrawable(R.drawable.rectangle_shape_1) );
        holder.binding.clientOrderCompletedAdapterBtnCancel.setText(R.string.order_refused);

    }else  if(myOrderData.get(position).getState().equals("delivered")){

        holder.binding.clientOrderCompletedAdapterBtnCancel.setBackground(activity.getResources().getDrawable(R.drawable.rectangle_shape_2) );
        holder.binding.clientOrderCompletedAdapterBtnCancel.setText(R.string.order_done);
    }



    }

//

    private void setAction(final ClientCompletedOrderAdapter.ViewHolder holder, final int position) {

        holder.binding.clientOrderCompletedAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId= myOrderData.get(position).getItems().get(0).getPivot().getOrderId();
                String restaurant=myOrderData.get(position).getRestaurant().getName();
                String date=myOrderData.get(position).getRestaurant().getUpdatedAt();
                String address=myOrderData.get(position).getAddress();
                String payMethod=myOrderData.get(position).getPaymentMethodId();
                String imageUrl=myOrderData.get(position).getRestaurant().getPhotoUrl();
                String cost=myOrderData.get(position).getCost();
                String delivery=myOrderData.get(position).getDeliveryCost();
                String total=myOrderData.get(position).getTotal();
                String phone=myOrderData.get(position).getRestaurant().getPhone();

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.home_activity_fl_frame,new ViewOrderFragment(
                                    restaurant,date,address,payMethod,orderId,imageUrl,total,cost,delivery,phone)).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemClientOrderCompletedBinding binding;


        public ViewHolder(@NonNull  ItemClientOrderCompletedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

