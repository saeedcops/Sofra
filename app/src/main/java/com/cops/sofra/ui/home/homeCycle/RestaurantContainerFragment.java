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

import com.cops.sofra.databinding.FragmentRestaurantContainerBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantReviewFragment;
import com.google.android.material.tabs.TabLayout;

public class RestaurantContainerFragment extends BaseFragment {

    public RestaurantContainerFragment(int restaurantId,String deliveryCost) {
        this.restaurantId = restaurantId;
        this.deliveryCost=deliveryCost;
    }

    private FragmentRestaurantContainerBinding binding;
    private  int restaurantId;
    private String deliveryCost;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_container,container,false);
        View view = binding.getRoot();
        setUpActivity();
        setUpTab();


       return view;
    }

    private void setUpTab() {
        if (binding.restaurantContainerFragmentVp!=null) {
            setUpViewPager();
        }
        binding.restaurantContainerFragmentTabs.setupWithViewPager(binding.restaurantContainerFragmentVp);
        binding.restaurantContainerFragmentTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.restaurantContainerFragmentVp.setCurrentItem(tab.getPosition());

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
        Bundle bundle =new Bundle();
        bundle.putInt("restaurantId",restaurantId);
        bundle.putString("deliveryCost",deliveryCost);
        RestaurantReviewFragment reviewFragment=new RestaurantReviewFragment();
        reviewFragment.setArguments(bundle);
        ViewPagerWithFragmentAdapter adapter =new ViewPagerWithFragmentAdapter(getChildFragmentManager());

        adapter.addPager(new RestaurantItemFoodListFragment(restaurantId, deliveryCost),getString(R.string.food_list));
        adapter.addPager(reviewFragment,getString(R.string.rate_comment));
        adapter.addPager(new RestaurantInfoFragment(restaurantId),getString(R.string.restaurant_info));
        binding.restaurantContainerFragmentVp.setAdapter(adapter);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
