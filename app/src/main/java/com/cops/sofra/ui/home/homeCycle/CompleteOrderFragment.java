package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.OrderItem;
import com.cops.sofra.data.model.newOrder.NewOrder;
import com.cops.sofra.databinding.FragmentCompleteOrderBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.viewModel.client.ClientNewOrderViewModel;
import com.cops.sofra.viewModel.room.ItemViewModel;

import java.util.ArrayList;
import java.util.List;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class CompleteOrderFragment extends BaseFragment {


    private FragmentCompleteOrderBinding binding;
    private String address,apiToken,phone,name;
    private List<Integer>items=new ArrayList<>();
    private List<Integer>qty=new ArrayList<>();
    private List<String>notes=new ArrayList<>();
    private String pay="1";
    private ClientNewOrderViewModel newOrderViewModel;
    private List<OrderItem> orderItems=new ArrayList();
    private ItemViewModel itemViewModel;
    private double lastTotal=0.0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_complete_order,container,false);
        final View view = binding.getRoot();
        setUpActivity();
        binding.completeOrderFragmentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.complete_order_fragment_cb_cash){
                    pay="1";

                }else{
                    pay="2";

                }
            }
        });


        if(LoadData(getActivity(), "apiToken")!=null){
            address=LoadData(getActivity(), "address");
            name=LoadData(getActivity(), "name");
            phone=LoadData(getActivity(), "phone");
            apiToken=LoadData(getActivity(), "apiToken");
        }

        setData();

        binding.completeOrderFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrder();
            }
        });




       return view;
    }
    private void getData(){


        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> oItems) {
                orderItems.addAll(oItems);
                for (int i=0;i<oItems.size();i++){

                    items.add(oItems.get(i).getItemId());
                    qty.add(oItems.get(i).getCount());
                    notes.add(oItems.get(i).getNote());
                }
            }
        });


    }

    private void setData() {



        binding.completeOrderFragmentTvLocation.setText(address);
        binding.completeOrderFragmentTvDeliveryCost.setText(getString(R.string.delivery_cost)+" : "+RestaurantItemFoodListFragment.deliveryCost);
        binding.completeOrderFragmentTvCount.setText(getString(R.string.total)+" : "+String.valueOf(CartFragment.total));

//        double d=0;
//        RestaurantItemFoodListFragment.deliveryCost
//        d=Double.parseDouble(RestaurantItemFoodListFragment.deliveryCost);

        lastTotal=CartFragment.total+Double.parseDouble(RestaurantItemFoodListFragment.deliveryCost);
        binding.completeOrderFragmentTvTotal.setText(getString(R.string.total_cost)+" : "+lastTotal);
    }

    private void newOrder() {



        newOrderViewModel = ViewModelProviders.of(getActivity()).get(ClientNewOrderViewModel.class);
        newOrderViewModel.newOrder(Integer.parseInt(orderItems.get(0).getRestaurantId()),binding.completeOrderFragmentEtNote.getText().toString(),
                address,pay,phone,name,apiToken,items,qty,notes);
        newOrderViewModel.clientOrderMutableLiveData.observe(this, new Observer<NewOrder>() {
            @Override
            public void onChanged(NewOrder newOrder) {
                if (newOrder.getStatus()==1) {
                    Toast.makeText(baseActivity, newOrder.getMsg(), Toast.LENGTH_SHORT).show();

                    for (int i=0;i<orderItems.size();i++){

                        itemViewModel.onDelete(orderItems.get(i));
                    }


                }else {
                    Toast.makeText(baseActivity, newOrder.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
