package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
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

import com.cops.sofra.R;
import com.cops.sofra.adapters.SelectedOrderAdapter;
import com.cops.sofra.data.local.room.RoomDao;
import com.cops.sofra.data.model.OrderItem;
import com.cops.sofra.databinding.FragmentCartBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.auth.AuthActivity;
import com.cops.sofra.utils.SelectedOrder;
import com.cops.sofra.viewModel.room.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class CartFragment extends BaseFragment {


    private FragmentCartBinding binding;
    private SelectedOrderAdapter selectedOrderAdapter;
    private List<OrderItem> orderItems =new ArrayList<>();
    private LinearLayoutManager layoutManager;
    public static double total=0.0;
    private ItemViewModel itemViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false);
        View view = binding.getRoot();
        setUpActivity();
        if(total>0.0)
            binding.cartTvTotal.setText(total + " $");
        layoutManager=new LinearLayoutManager(getActivity());
        binding.cartFragmentRv.setLayoutManager(layoutManager);
        selectedOrderAdapter=new SelectedOrderAdapter(getActivity(),orderItems);
        binding.cartFragmentRv.setAdapter(selectedOrderAdapter);



        binding.cartFragmentBtnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoadData(getActivity(), "userType").equals("client")){

                    if(total>0){
                        getFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.home_activity_fl_frame,new CompleteOrderFragment()).commit();
                    }else {

                        Toast.makeText(baseActivity, getString(R.string.add_to_order), Toast.LENGTH_LONG).show();
                    }


                }else {

                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    intent.putExtra("userType", "client");
                    startActivity(intent);
                }

            }
        });

        binding.cartFragmentBtnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new RestaurantItemFoodListFragment(RestaurantItemFoodListFragment.restaurantId,RestaurantItemFoodListFragment.deliveryCost)).commit();
            }
        });
       return view;
    }

    private void setData(){


        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> items) {
                total=0.0;
                for (int i = 0; i < items.size(); i++) {
                    total += items.get(i).getCount() * Double.parseDouble(items.get(i).getPrice());

                }

                orderItems.addAll(items);
                selectedOrderAdapter.notifyDataSetChanged();

            }

        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
