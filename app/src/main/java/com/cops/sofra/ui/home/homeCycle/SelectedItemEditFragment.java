package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cops.sofra.R;
import com.cops.sofra.adapters.SelectedOrderAdapter;
import com.cops.sofra.databinding.FragmentSelectedItemBinding;
import com.cops.sofra.databinding.FragmentSelectedItemEditBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.auth.AuthActivity;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.SelectedOrder;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.cops.sofra.ui.home.homeCycle.SelectedItemFragment.selectedOrder;

public class SelectedItemEditFragment extends BaseFragment {


    private FragmentSelectedItemEditBinding binding;
    private SelectedOrderAdapter selectedOrderAdapter;
    private List<SelectedOrder> selectedOrders=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private double total;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_selected_item_edit,container,false);
        View view = binding.getRoot();
        setUpActivity();


        selectedOrders.add(new SelectedOrder(selectedOrder.getName()
                , selectedOrder.getImageUrl(), selectedOrder.getPrice()
                , selectedOrder.getCount()));

        selectedOrderAdapter=new SelectedOrderAdapter(getActivity(),selectedOrders);
        layoutManager=new LinearLayoutManager(getActivity());
        binding.selectedItemEditFragmentRv.setLayoutManager(layoutManager);
        binding.selectedItemEditFragmentRv.setAdapter(selectedOrderAdapter);

        total=Double.parseDouble(selectedOrder.getPrice())*selectedOrder.getCount();
        binding.selectedItemEditFragmentTvTotal.setText(total+" $");

        binding.selectedItemEditFragmentBtnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // if (LoadData(getActivity(), "userType").equals("client"))


                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    intent.putExtra("userType", "client");

                    startActivity(intent);

            }
        });

       return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
