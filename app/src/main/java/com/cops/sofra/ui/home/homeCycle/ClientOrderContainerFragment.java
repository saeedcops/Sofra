package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.R;
import com.cops.sofra.adapters.ViewPagerWithFragmentAdapter;
import com.cops.sofra.databinding.FragmentClientOrderContainerBinding;
import com.cops.sofra.databinding.FragmentRestaurantOrderContainerBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantOrderCompletedFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantOrderCurrentFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantOrderPendingFragment;
import com.google.android.material.tabs.TabLayout;

public class ClientOrderContainerFragment extends BaseFragment {


    FragmentClientOrderContainerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_client_order_container,container,false);
        View view = binding.getRoot();
        setUpActivity();
        setUpTab();

       return view;
    }
    private void setUpTab() {
        if (binding.clientOrderContainerFragmentItemVp!=null) {
            setUpViewPager();
        }
        binding.clientOrderContainerFragmentItemTabs.setupWithViewPager(binding.clientOrderContainerFragmentItemVp);
        binding.clientOrderContainerFragmentItemTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.clientOrderContainerFragmentItemVp.setCurrentItem(tab.getPosition());

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

        adapter.addPager(new ClientOrderPendingFragment(),getString(R.string.pending_order));
        adapter.addPager(new ClientOrderCurrentFragment(),getString(R.string.current_order));
        adapter.addPager(new ClientOrderCompletedFragment(),getString(R.string.completed_order));
        binding.clientOrderContainerFragmentItemVp.setAdapter(adapter);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
