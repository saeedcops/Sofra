package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.R;
import com.cops.sofra.adapters.ViewPagerWithFragmentAdapter;

import com.cops.sofra.databinding.FragmentRestaurantOrderContainerBinding;
import com.cops.sofra.ui.BaseFragment;

import com.google.android.material.tabs.TabLayout;

public class RestaurantOrderContainerFragment extends BaseFragment {


    FragmentRestaurantOrderContainerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_order_container,container,false);
        View view = binding.getRoot();
        setUpActivity();
        setUpTab();

       return view;
    }
    private void setUpTab() {
        if (binding.restaurantOrderContainerFragmentItemVp!=null) {
            setUpViewPager();
        }
        binding.restaurantOrderContainerFragmentItemTabs.setupWithViewPager(binding.restaurantOrderContainerFragmentItemVp);
        binding.restaurantOrderContainerFragmentItemTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.restaurantOrderContainerFragmentItemVp.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager() {

        ViewPagerWithFragmentAdapter adapter =new ViewPagerWithFragmentAdapter(getChildFragmentManager());

        adapter.addPager(new RestaurantOrderPendingFragment(),getString(R.string.pending_order));
        adapter.addPager(new RestaurantOrderCurrentFragment(),getString(R.string.current_order));
        adapter.addPager(new RestaurantOrderCompletedFragment(),getString(R.string.completed_order));
        binding.restaurantOrderContainerFragmentItemVp.setAdapter(adapter);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
