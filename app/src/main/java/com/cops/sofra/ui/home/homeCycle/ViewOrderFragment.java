package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.cops.sofra.data.model.newOrder.NewOrder;
import com.cops.sofra.data.model.viewOrder.ViewOrder;
import com.cops.sofra.databinding.FragmentCompleteOrderActionBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.dialog.OrderRefuseDialog;
import com.cops.sofra.viewModel.client.ClientConfirmOrderViewModel;
import com.cops.sofra.viewModel.client.ClientDeclineOrderViewModel;
import com.cops.sofra.viewModel.client.ClientViewOrderViewModel;
import com.cops.sofra.viewModel.restaurant.RestaurantConfirmOrderViewModel;
import com.cops.sofra.viewModel.restaurant.RestaurantViewOrderViewModel;
import com.cops.sofra.viewModel.room.ItemViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;
import static com.cops.sofra.utils.HelperMethod.isRTL;
import static java.sql.Types.TIME;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SHORT;


public class ViewOrderFragment extends BaseFragment {


    private String date=" ";

    public ViewOrderFragment(int orderId) {
        this.orderId = orderId;
    }

    private String phone;
    private FragmentCompleteOrderActionBinding binding;
    private int orderId;
    private ItemViewModel itemViewModel;
    private List<Item> orderItems = new ArrayList<>();
    private CartAdapter cartAdapter;
    private LinearLayoutManager layoutManager;
    private String apiToken;
    private ClientDeclineOrderViewModel declineOrderViewModel;
    private ClientConfirmOrderViewModel confirmOrderViewModel;
    private RestaurantConfirmOrderViewModel restaurantConfirmOrderViewModel;
    private RestaurantViewOrderViewModel restaurantViewOrderViewModel;
    private ClientViewOrderViewModel clientViewOrderViewModel;


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
        if (LoadData(getActivity(), "apiToken") != null) {

            apiToken = LoadData(getActivity(), "apiToken");
        }



        setListViewData();

        binding.completeOrderActionFragmentBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoadData(getActivity(), "apiToken") != null) {

                    if (LoadData(getActivity(), "userType").equals("client")) {
                        confirm();
                    } else {
                        restaurantConfirm();
                    }
                }


            }
        });

        binding.completeOrderActionFragmentBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoadData(getActivity(), "apiToken") != null) {

                    if (LoadData(getActivity(), "userType").equals("client")) {
                        decline();
                    } else {

                        OrderRefuseDialog refuseDialog = new OrderRefuseDialog(orderId);
                        refuseDialog.show(getFragmentManager(), "dialog");
                    }
                }

            }
        });
        binding.completeOrderActionFragmentBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, "")));

            }
        });

        return view;
    }


    private void setListViewData() {
        layoutManager = new LinearLayoutManager(getActivity());
        cartAdapter = new CartAdapter(getActivity(), orderItems);
        binding.completeOrderActionFragmentRv.setLayoutManager(layoutManager);


        if (LoadData(getActivity(), "apiToken") != null) {

            if (LoadData(getActivity(), "userType").equals("client")) {

                clientViewOrderViewModel = ViewModelProviders.of(getActivity()).get(ClientViewOrderViewModel.class);
                clientViewOrderViewModel.clientViewOrder(apiToken, orderId);
                clientViewOrderViewModel.clientViewOrderMutableLiveData.observe(this, new Observer<ViewOrder>() {
                    @Override
                    public void onChanged(ViewOrder viewOrder) {
                        if (viewOrder.getStatus() == 1) {
                            Log.i("client","called");
                            orderItems.clear();
                            setData(viewOrder.getData());
                            orderItems.addAll(viewOrder.getData().getItems());
                            cartAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(baseActivity, viewOrder.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {

                restaurantViewOrderViewModel = ViewModelProviders.of(getActivity()).get(RestaurantViewOrderViewModel.class);
                restaurantViewOrderViewModel.viewOrder(apiToken, orderId);
                restaurantViewOrderViewModel.restaurantsViewOrderMutableLiveData.observe(this, new Observer<ViewOrder>() {
                    @Override
                    public void onChanged(ViewOrder viewOrder) {
                        if (viewOrder.getStatus() == 1) {
                            orderItems.clear();
                            setData(viewOrder.getData());
                            orderItems.addAll(viewOrder.getData().getItems());
                            cartAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(baseActivity, viewOrder.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
        binding.completeOrderActionFragmentRv.setAdapter(cartAdapter);


    }

    private void setData(MyOrderData data) {
        Date createdAt;
        try {
            date=" ";
            createdAt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(data.getCreatedAt());
            Calendar c = Calendar.getInstance();
            c.setTime(createdAt);
            date+= c.getDisplayName(DAY_OF_WEEK, SHORT, Locale.getDefault());
            date+=" : ";
            date+= c.get(DAY_OF_MONTH);
            date+=" ";
            date+= c.getDisplayName(MONTH, SHORT, Locale.getDefault());

            //int dayOfWeek = c.get(DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Glide.with(getActivity()).load(data.getRestaurant().getPhotoUrl()).into(binding.completeOrderActionFragmentIv);
        binding.completeOrderActionFragmentTvName.setText(data.getRestaurant().getName());
        binding.completeOrderActionFragmentTvDate.setText( date);
        binding.completeOrderActionFragmentTvAddress.setText(getString(R.string.address) + " : " + data.getAddress());
        binding.completeOrderActionFragmentTvOrderDeliveryCost.setText(getString(R.string.delivery_cost) + " : " + data.getDeliveryCost() + " EGP");

        binding.completeOrderActionFragmentTvOrderPrice.setText(getString(R.string.total) + " : " + data.getCost() + " EGP");

        binding.completeOrderActionFragmentTvOrderTotal.setText(getString(R.string.total_cost) + " : " + data.getTotal() + " EGP");

        if (data.getPaymentMethodId().equals("1")) {
            binding.completeOrderActionFragmentTvOrderPay.setText(getString(R.string.payments) + " : " + getString(R.string.cash));
        } else {
            binding.completeOrderActionFragmentTvOrderPay.setText(getString(R.string.payments) + " : " + getString(R.string.online));
        }
        if(data.getClient()!=null){
            phone = data.getClient().getPhone();
        }else{
            phone = data.getRestaurant().getPhone();
        }


}
    private void decline(){

        declineOrderViewModel =ViewModelProviders.of(getActivity()).get(ClientDeclineOrderViewModel.class);
        declineOrderViewModel.declineOrder(apiToken, String.valueOf(orderId));
        declineOrderViewModel.clientOrderMutableLiveData.observe(this, new Observer<NewOrder>() {
            @Override
            public void onChanged(NewOrder newOrder) {
                Toast.makeText(baseActivity, newOrder.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void confirm(){
        confirmOrderViewModel= ViewModelProviders.of(getActivity()).get(ClientConfirmOrderViewModel.class);
        confirmOrderViewModel.confirmOrder(apiToken, String.valueOf(orderId));
        confirmOrderViewModel.clientOrderMutableLiveData.observe(this, new Observer<NewOrder>() {
            @Override
            public void onChanged(NewOrder newOrder) {

                    Toast.makeText(baseActivity, newOrder.getMsg(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void restaurantConfirm(){
        restaurantConfirmOrderViewModel =ViewModelProviders.of(getActivity()).get(RestaurantConfirmOrderViewModel.class);
        restaurantConfirmOrderViewModel.confirmOrder(apiToken, String.valueOf(orderId));
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
        orderItems.clear();
    }
}
