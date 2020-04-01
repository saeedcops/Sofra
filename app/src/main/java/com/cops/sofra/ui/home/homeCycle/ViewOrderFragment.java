package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;

import com.cops.sofra.adapters.CartAdapter;
import com.cops.sofra.data.model.OrderItem;
import com.cops.sofra.data.model.acceptOrder.AcceptOrder;
import com.cops.sofra.data.model.newOrder.NewOrder;
import com.cops.sofra.databinding.FragmentCompleteOrderActionBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.dialog.OrderRefuseDialog;
import com.cops.sofra.viewModel.client.ClientConfirmOrderViewModel;
import com.cops.sofra.viewModel.client.ClientDeclineOrderViewModel;
import com.cops.sofra.viewModel.restaurant.RestaurantConfirmOrderViewModel;
import com.cops.sofra.viewModel.room.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class ViewOrderFragment extends BaseFragment {


    public ViewOrderFragment(String restaurant, String date, String address, String payMethod, String orderId,
                             String imageUrl,String total,String cost,String deliveryCost,String phone) {
        this.restaurant = restaurant;
        this.date = date;
        this.address = address;
        this.payMethod = payMethod;
        this.orderId = orderId;
        this.imageUrl = imageUrl;
        this.total=total;
        this.cost=cost;
        this.deliveryCost=deliveryCost;
        this.phone=phone;
    }

    private String phone;
    private String cost;
    private String deliveryCost;
    private String total;
    private String imageUrl;
    private String restaurant;
    private String date;
    private String address;
    private String payMethod;
    private FragmentCompleteOrderActionBinding binding;
    private String orderId;
    private ItemViewModel itemViewModel;
    private ArrayList<OrderItem> orderItems = new ArrayList<>();
    private CartAdapter cartAdapter;
    private LinearLayoutManager layoutManager;
    private String apiToken;
    private ClientDeclineOrderViewModel declineOrderViewModel;
    private ClientConfirmOrderViewModel confirmOrderViewModel;
    private RestaurantConfirmOrderViewModel restaurantConfirmOrderViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_order_action, container, false);
        View view = binding.getRoot();
        setUpActivity();
        if(LoadData(getActivity(), "apiToken")!=null){

            apiToken=LoadData(getActivity(), "apiToken");
        }

        Glide.with(getActivity()).load(imageUrl).into(binding.completeOrderActionFragmentIv);
        binding.completeOrderActionFragmentTvName.setText(restaurant);
        binding.completeOrderActionFragmentTvDate.setText(date);
        binding.completeOrderActionFragmentTvAddress.setText(getString(R.string.address) + " : " + address);
        binding.completeOrderActionFragmentTvOrderDeliveryCost.setText(getString(R.string.delivery_cost) + " : "+deliveryCost +" $");

        binding.completeOrderActionFragmentTvOrderPrice.setText(getString(R.string.total) + " : " + cost + " $");

        binding.completeOrderActionFragmentTvOrderTotal.setText(getString(R.string.total_cost) + " : " +total + " $");

        if (payMethod.equals("1")) {
            binding.completeOrderActionFragmentTvOrderPay.setText(getString(R.string.payments)+" : "+getString(R.string.cash));
        }else{
            binding.completeOrderActionFragmentTvOrderPay.setText(getString(R.string.payments)+" : "+getString(R.string.online));
        }

        setListViewData();

        binding.completeOrderActionFragmentBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoadData(getActivity(), "apiToken")!=null){

                    if(LoadData(getActivity(), "userType").equals("client")){
                        confirm();
                    }else{
                        restaurantConfirm();
                    }
                }


            }
        });

        binding.completeOrderActionFragmentBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoadData(getActivity(), "apiToken")!=null){

                    if(LoadData(getActivity(), "userType").equals("client")){
                        decline();
                    }else{

                        OrderRefuseDialog refuseDialog = new OrderRefuseDialog(Integer.parseInt(orderId));
                        refuseDialog.show(getFragmentManager(), "dialog");
                    }
                }

            }
        });
        binding.completeOrderActionFragmentBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,"")));

            }
        });

        return view;
    }


    private void setListViewData() {
        layoutManager = new LinearLayoutManager(getActivity());
        cartAdapter = new CartAdapter(getActivity(), orderItems);
        binding.completeOrderActionFragmentRv.setLayoutManager(layoutManager);
        binding.completeOrderActionFragmentRv.setAdapter(cartAdapter);


        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> items) {

                orderItems.addAll(items);
                cartAdapter.notifyDataSetChanged();
            }

        });
    }

    private void decline(){

        declineOrderViewModel =ViewModelProviders.of(getActivity()).get(ClientDeclineOrderViewModel.class);
        declineOrderViewModel.declineOrder(apiToken,orderId);
        declineOrderViewModel.clientOrderMutableLiveData.observe(this, new Observer<NewOrder>() {
            @Override
            public void onChanged(NewOrder newOrder) {
                Toast.makeText(baseActivity, newOrder.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void confirm(){
        confirmOrderViewModel= ViewModelProviders.of(getActivity()).get(ClientConfirmOrderViewModel.class);
        confirmOrderViewModel.confirmOrder(apiToken,orderId);
        confirmOrderViewModel.clientOrderMutableLiveData.observe(this, new Observer<NewOrder>() {
            @Override
            public void onChanged(NewOrder newOrder) {

                    Toast.makeText(baseActivity, newOrder.getMsg(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void restaurantConfirm(){
        restaurantConfirmOrderViewModel =ViewModelProviders.of(getActivity()).get(RestaurantConfirmOrderViewModel.class);
        restaurantConfirmOrderViewModel.confirmOrder(apiToken,orderId);
        restaurantConfirmOrderViewModel.confirmOrderMutableLiveData.observe(this, new Observer<AcceptOrder>() {
            @Override
            public void onChanged(AcceptOrder acceptOrder) {
                Toast.makeText(baseActivity, acceptOrder.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
