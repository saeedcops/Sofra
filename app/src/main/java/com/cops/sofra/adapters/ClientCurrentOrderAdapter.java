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
import com.cops.sofra.databinding.ItemClientOrderCurrentBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOrderFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class ClientCurrentOrderAdapter extends RecyclerView.Adapter<ClientCurrentOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<ClientOrderData> myOrderData = new ArrayList<>();

    public ClientCurrentOrderAdapter(Activity activity, List<ClientOrderData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.myOrderData = restaurantDataList;
    }



    @Override
    public ClientCurrentOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemClientOrderCurrentBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_client_order_current, parent, false);

        return new ClientCurrentOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientCurrentOrderAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(ClientCurrentOrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOrderData.get(position).getRestaurant().getPhotoUrl()).into(holder.binding.clientOrderCurrentAdapterIv);

       holder.binding.clientOrderCurrentAdapterName.setText(myOrderData.get(position).getRestaurant().getName());
       holder.binding.clientOrderCurrentAdapterNumber.setText(activity.getString(R.string.order_number)+myOrderData.get(position).getItems().get(0).getPivot().getOrderId());
        holder.binding.clientOrderCurrentAdapterTotal.setText(activity.getString(R.string.total_cost)+" : "+myOrderData.get(position).getTotal() + " $");
        holder.binding.clientOrderCurrentAdapterAddress.setText(activity.getString(R.string.address)+" : "+myOrderData.get(position).getAddress());

    }

//

    private void setAction(final ClientCurrentOrderAdapter.ViewHolder holder, final int position) {

        holder.binding.clientOrderCurrentAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int orderId= myOrderData.get(position).getId();


                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new ViewOrderFragment(orderId)).commit();
            }
        });

        holder.binding.clientOrderCurrentAdapterBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apiToken="";
                if (LoadData(activity,"apiToken")!=null) {

                    apiToken=LoadData(activity,"apiToken");

                }

                getClient().confirmOrder(myOrderData.get(position).getItems().get(position).getPivot().getOrderId(),apiToken).enqueue(new Callback<NewOrder>() {
                    @Override
                    public void onResponse(Call<NewOrder> call, Response<NewOrder> response) {

                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<NewOrder> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemClientOrderCurrentBinding binding;


        public ViewHolder(@NonNull  ItemClientOrderCurrentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

