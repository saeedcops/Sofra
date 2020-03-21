package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.R;
import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.databinding.FragmentRestaurantCategoriesBinding;
import com.cops.sofra.ui.BaseFragment;

public class RestaurantCategoriesFragment extends BaseFragment {


    private FragmentRestaurantCategoriesBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_categories,container,false);
        View view = binding.getRoot();
        setUpActivity();


       return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
