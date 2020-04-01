package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.cops.sofra.R;

import com.cops.sofra.data.model.commission.Commission;
import com.cops.sofra.data.model.commission.CommissionData;
import com.cops.sofra.databinding.FragmentRestaurantCommissionBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.restaurant.RestaurantCommissionViewModel;


import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class RestaurantCommissionFragment extends BaseFragment {


    private FragmentRestaurantCommissionBinding binding;
    private String apiToken;
    private RestaurantCommissionViewModel commissionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_commission, container, false);
        View view = binding.getRoot();
        setUpActivity();

       // apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";

        if (LoadData(getActivity(), "apiToken") != null) {

            apiToken = LoadData(getActivity(), "apiToken");

        }

        getCommission();



        return view;
    }

    private void getCommission() {
        commissionViewModel = ViewModelProviders.of(getActivity()).get(RestaurantCommissionViewModel.class);
        commissionViewModel.getCommission(apiToken);
        commissionViewModel.restaurantsCommissionMutableLiveData.observe(this, new Observer<Commission>() {
            @Override
            public void onChanged(Commission commission) {
                if (commission.getStatus()==1) {
                    setData(commission.getData());
                }

            }
        });

    }

    private void setData(CommissionData data) {
        binding.restaurantCommissionFragmentTotal.setText(getString(R.string.total)+" : "+data.getTotal()+" $");
        binding.restaurantCommissionFragmentCommission.setText(getString(R.string.commission)+" : "+data.getCommissions()+" $");
        binding.restaurantCommissionFragmentPayedCommission.setText(getString(R.string.payments)+" : "+data.getPayments()+" $");
        binding.restaurantCommissionFragmentNetCommission.setText(getString(R.string.net_commission)+" : "+data.getCommissions()+" $");
    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
