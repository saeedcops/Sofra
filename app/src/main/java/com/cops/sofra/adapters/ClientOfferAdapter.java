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
import com.cops.sofra.data.model.myOffer.MyOfferData;
import com.cops.sofra.databinding.ItemClientOfferBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.ViewOfferDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class ClientOfferAdapter extends RecyclerView.Adapter<ClientOfferAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MyOfferData> myOfferData = new ArrayList<>();

    public ClientOfferAdapter(Activity activity, List<MyOfferData> restaurantDataList) {
        this.context = activity;
        this.activity = activity;
        this.myOfferData = restaurantDataList;
    }


    @Override
    public ClientOfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemClientOfferBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_client_offer, parent, false);

        return new ClientOfferAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientOfferAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(ClientOfferAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOfferData.get(position).getPhotoUrl()).into(holder.binding.clientAdapterIv);
        holder.binding.clientOfferAdapterTvName.setText(myOfferData.get(position).getName());

    }

//

    private void setAction(final ClientOfferAdapter.ViewHolder holder, final int position) {

        holder.binding.clientOfferAdapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_activity_fl_frame,new ViewOfferDetailsFragment(
                                myOfferData.get(position).getRestaurantId(),myOfferData.get(position).getPhotoUrl()
                                ,myOfferData.get(position).getPrice(),myOfferData.get(position).getName(),
                                myOfferData.get(position).getId())).addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return myOfferData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemClientOfferBinding binding;


        public ViewHolder(@NonNull ItemClientOfferBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

