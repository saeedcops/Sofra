package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.clientOrder.ClientOrderData;
import com.cops.sofra.data.model.newOrder.NewOrder;
import com.cops.sofra.databinding.ItemClientOrderPendingBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOrderFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class ClientPendingOrderAdapter extends RecyclerView.Adapter<ClientPendingOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<ClientOrderData> myOrderData = new ArrayList<>();

    public ClientPendingOrderAdapter(Activity activity, List<ClientOrderData> restaurantDataList) {
        this.context = activity;
        this.activity = activity;
        this.myOrderData = restaurantDataList;
    }


    @Override
    public ClientPendingOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemClientOrderPendingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_client_order_pending, parent, false);

        return new ClientPendingOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientPendingOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(ClientPendingOrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOrderData.get(position).getRestaurant().getPhotoUrl()).into(holder.binding.clientOrderPendingAdapterIv);

        holder.binding.clientOrderPendingAdapterName.setText(myOrderData.get(position).getRestaurant().getName());
        holder.binding.clientOrderPendingAdapterNumber.setText(activity.getString(R.string.order_number) + myOrderData.get(position).getItems().get(0).getPivot().getOrderId());
        holder.binding.clientOrderPendingAdapterTotal.setText(activity.getString(R.string.total_cost) + " : " + myOrderData.get(position).getTotal() + " $");
        holder.binding.clientOrderPendingAdapterAddress.setText(activity.getString(R.string.address) + " : " + myOrderData.get(position).getAddress());

    }

//

    private void setAction(final ClientPendingOrderAdapter.ViewHolder holder, final int position) {

        holder.binding.clientOrderPendingAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId = myOrderData.get(position).getItems().get(0).getPivot().getOrderId();
                String restaurant = myOrderData.get(position).getRestaurant().getName();
                String date = myOrderData.get(position).getRestaurant().getUpdatedAt();
                String address = myOrderData.get(position).getAddress();
                String payMethod = myOrderData.get(position).getPaymentMethodId();
                String imageUrl = myOrderData.get(position).getRestaurant().getPhotoUrl();
                String cost = myOrderData.get(position).getCost();
                String delivery = myOrderData.get(position).getDeliveryCost();
                String total = myOrderData.get(position).getTotal();
                String phone = myOrderData.get(position).getRestaurant().getPhone();

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame, new ViewOrderFragment(
                                restaurant, date, address, payMethod, orderId, imageUrl, total, cost, delivery, phone)).commit();
            }
        });

        holder.binding.clientOrderPendingAdapterBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apiToken = "";
                if (LoadData(activity, "apiToken") != null) {

                    apiToken = LoadData(activity, "apiToken");

                }
                try {


                getClient().declineOrder(myOrderData.get(position).getItems().get(position).getPivot().getOrderId(), apiToken).enqueue(new Callback<NewOrder>() {
                    @Override
                    public void onResponse(Call<NewOrder> call, Response<NewOrder> response) {

                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<NewOrder> call, Throwable t) {

                    }
                });
                } catch (Exception e) {
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return myOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemClientOrderPendingBinding binding;


        public ViewHolder(@NonNull ItemClientOrderPendingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

