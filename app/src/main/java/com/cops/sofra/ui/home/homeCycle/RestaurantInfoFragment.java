package com.cops.sofra.ui.home.homeCycle;

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
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.data.model.restaurantInfo.RestaurantInfo;
import com.cops.sofra.data.model.restaurantLogin.User;
import com.cops.sofra.databinding.FragmentRestaurantInfoBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.viewModel.client.RestaurantInfoViewModel;

public class RestaurantInfoFragment extends BaseFragment {
    public RestaurantInfoFragment(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    private FragmentRestaurantInfoBinding binding;
    private int restaurantId;
    private RestaurantInfoViewModel infoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_info,container,false);
        View view = binding.getRoot();
        setUpActivity();

        getInfo();

       return view;
    }

    private void getInfo() {
        infoViewModel = ViewModelProviders.of(getActivity()).get(RestaurantInfoViewModel.class);
        infoViewModel.getRestaurantInfo(restaurantId);
        infoViewModel.restaurantInfoMutableLiveData.observe(this, new Observer<RestaurantInfo>() {
            @Override
            public void onChanged(RestaurantInfo restaurantInfo) {
                if (restaurantInfo.getStatus()==1) {
                    setData(restaurantInfo.getData());
                }
            }
        });
    }

    private void setData(User data) {
        binding.restaurantInfoFragmentTvStatus.setText(getString(R.string.status)+" \t  "+ data.getAvailability());
        binding.restaurantInfoFragmentTvRegion.setText(getString(R.string.region)+" \t  "+ data.getRegion().getName());
        binding.restaurantInfoFragmentTvCity.setText(getString(R.string.city)+" \t  "+ data.getRegion().getCity().getName());
        binding.restaurantInfoFragmentTvMinimumCharger.setText(getString(R.string.minimum_charger)+" \t  "+data.getMinimumCharger()+" $");
        binding.restaurantInfoFragmentTvDeliveryCost.setText(getString(R.string.delivery_cost)+" \t  "+ data.getDeliveryCost()+" $");

    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
