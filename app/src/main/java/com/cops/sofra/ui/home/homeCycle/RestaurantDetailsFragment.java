package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.R;
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.databinding.FragmentRestaurantDetailsBinding;
import com.cops.sofra.ui.BaseFragment;

public class RestaurantDetailsFragment extends BaseFragment {


    FragmentRestaurantDetailsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_details,container,false);
        View view = binding.getRoot();
        setUpActivity();
        binding.restaurantDetailsFragmentTvStatus.setText(getString(R.string.status)+" \t  "+ RestaurantAdapter.availability);
        binding.restaurantDetailsFragmentTvRegion.setText(getString(R.string.region)+" \t  "+ RestaurantAdapter.region);
        binding.restaurantDetailsFragmentTvCity.setText(getString(R.string.city)+" \t  "+ RestaurantAdapter.city);
        binding.restaurantDetailsFragmentTvMinimumCharger.setText(getString(R.string.minimum_charger)+" \t  "+ RestaurantAdapter.minimumCharger+" $");
        binding.restaurantDetailsFragmentTvDeliveryCost.setText(getString(R.string.delivery_cost)+" \t  "+ RestaurantAdapter.deliveryCost+" $");

       return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
