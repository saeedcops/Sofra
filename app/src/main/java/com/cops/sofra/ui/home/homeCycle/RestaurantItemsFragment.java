package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.sofra.R;
import com.cops.sofra.adapters.CategoryAdapter;
import com.cops.sofra.adapters.ViewPagerWithFragmentAdapter;
import com.cops.sofra.data.model.category.Category;
import com.cops.sofra.data.model.category.CategoryData;
import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.databinding.FragmentRestaurantItemBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.viewModel.CategoryViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItemsFragment extends BaseFragment {


    private FragmentRestaurantItemBinding binding;
//    public static int restaurantId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_item,container,false);
        View view = binding.getRoot();
        setUpActivity();
        setUpTab();


       return view;
    }

    private void setUpTab() {
        if (binding.restaurantFragmentItemVp!=null) {
            setUpViewPager();
        }
        binding.restaurantFragmentItemTabs.setupWithViewPager(binding.restaurantFragmentItemVp);
        binding.restaurantFragmentItemTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.restaurantFragmentItemVp.setCurrentItem(tab.getPosition());

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

        ViewPagerWithFragmentAdapter adapter =new ViewPagerWithFragmentAdapter(getFragmentManager());

        adapter.addPager(new FoodListFragment(),getString(R.string.food_list));
        adapter.addPager(new RateCommentsFragment(),getString(R.string.rate_comment));
        adapter.addPager(new RestaurantDetailsFragment(),getString(R.string.restaurant_details));
        binding.restaurantFragmentItemVp.setAdapter(adapter);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
