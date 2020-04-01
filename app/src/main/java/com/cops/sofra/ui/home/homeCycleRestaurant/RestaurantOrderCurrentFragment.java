package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.R;
import com.cops.sofra.adapters.CurrentOrderAdapter;

import com.cops.sofra.adapters.PendingOrderAdapter;
import com.cops.sofra.data.model.myOrder.MyOrder;
import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.cops.sofra.databinding.FragmentRestaurantOrderCurrentBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.restaurant.RestaurantGetOrderViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class RestaurantOrderCurrentFragment extends BaseFragment {


    private FragmentRestaurantOrderCurrentBinding binding;
    private CurrentOrderAdapter orderAdapter;
    private RestaurantGetOrderViewModel restaurantGetOrderViewModel;
    private List<MyOrderData> myOrderData = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private OnEndLess onEndLess;
    private String apiToken;
    private int lastPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_order_current,container,false);
        View view = binding.getRoot();
        setUpActivity();

       // apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            apiToken=LoadData(getActivity(),"apiToken");

        }
        getCurrentOrder();
       return view;
    }

    private void getCurrentOrder(){
        restaurantGetOrderViewModel= ViewModelProviders.of(getActivity()).get(RestaurantGetOrderViewModel.class);
        layoutManager= new LinearLayoutManager(getActivity());
        binding.restaurantOrderCurrentFragmentRv.setLayoutManager(layoutManager);
        orderAdapter=new CurrentOrderAdapter(getActivity(),myOrderData);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        restaurantGetOrderViewModel.getOrderList(apiToken,"current",current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.restaurantOrderCurrentFragmentRv.addOnScrollListener(onEndLess);
        if (myOrderData.size()==0) {

            restaurantGetOrderViewModel.getOrderList(apiToken,"current",1);
        }
        binding.restaurantOrderCurrentFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (myOrderData.size() == 0) {

                    restaurantGetOrderViewModel.getOrderList(apiToken,"current",1);
                }
                binding.restaurantOrderCurrentFragmentSwipe.setRefreshing(false);
            }
        });

        binding.restaurantOrderCurrentFragmentRv.setAdapter(orderAdapter);
        restaurantGetOrderViewModel.restaurantsGetOrderMutableLiveData.observe(this, new Observer<MyOrder>() {
            @Override
            public void onChanged(MyOrder myOrder) {
                if (myOrder.getStatus()==1) {
                    lastPage= myOrder.getData().getLastPage();
                    Log.i("data",myOrder.getMsg());
                    myOrderData.clear();
                    myOrderData.addAll(myOrder.getData().getData());
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
