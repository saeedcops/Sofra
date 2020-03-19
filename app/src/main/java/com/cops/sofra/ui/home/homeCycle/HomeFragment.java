package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.adapters.CitySpinnerAdapter;
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.city.CityData;
import com.cops.sofra.data.model.restaurants.RestaurantData;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.R;
import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.CityViewModel;
import com.cops.sofra.viewModel.RestaurantViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.viewModel.RestaurantViewModel.lastPage;

public class HomeFragment extends BaseFragment {

    private RestaurantAdapter restaurantAdapter;
    private LinearLayoutManager layoutManager;
    private List<RestaurantData> restaurantsList = new ArrayList<>();
    private List<CityData> cityList = new ArrayList<>();
    private RestaurantViewModel restaurantViewModel;
    private CityViewModel cityViewModel;
    private CitySpinnerAdapter spinnerAdapter;
    private FragmentHomeBinding binding;
    private OnEndLess onEndLess;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        setUpActivity();

        getRestaurant();

        getCity();

        return view;
    }

    private void getCity() {
        cityViewModel = ViewModelProviders.of(getActivity()).get(CityViewModel.class);
        spinnerAdapter = new CitySpinnerAdapter(getActivity());
        if (cityList.size() == 0) {
            cityViewModel.getCity();
        }

        cityViewModel.cityMutableLiveData.observe(this, new Observer<City>() {
            @Override
            public void onChanged(City cityData) {
                cityList = new ArrayList<>();
                cityList.addAll(cityData.getData());

                spinnerAdapter.setData(cityData.getData(), getString(R.string.choose_city));
                binding.homeFragmentSpCity.setAdapter(spinnerAdapter);
                binding.homeFragmentSpCity.setEnabled(true);

                for (int i = 0; i < spinnerAdapter.generalResponceData.size(); i++) {

                    if (spinnerAdapter.generalResponceData.get(i).getId() == binding.homeFragmentSpCity.getSelectedItemPosition()) {
                        //spinner.setSelection(i-1);
                        binding.homeFragmentSpCity.setSelection(spinnerAdapter.selectedId);

                        break;
                    }

                }
            }
        });


    }

    private void getRestaurant() {
        restaurantViewModel = ViewModelProviders.of(getActivity()).get(RestaurantViewModel.class);

        restaurantAdapter = new RestaurantAdapter(getActivity(), restaurantsList);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.homeFragmentRlv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        restaurantViewModel.getRestaurant(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };

        binding.homeFragmentRlv.addOnScrollListener(onEndLess);
        binding.homeFragmentRlv.setAdapter(restaurantAdapter);

        if (restaurantsList.size() == 0) {
            restaurantViewModel.getRestaurant(1);
        }


        binding.homeFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantsList.size() == 0) {

                    restaurantViewModel.getRestaurant(1);
                }
                binding.homeFragmentSwipe.setRefreshing(false);
            }
        });
        restaurantViewModel.restaurantsMutableLiveData.observe(this, new Observer<List<RestaurantData>>() {
            @Override
            public void onChanged(List<RestaurantData> restaurantData) {
                binding.homeFragmentSwipe.setRefreshing(false);

                restaurantsList.addAll(restaurantData);

                restaurantAdapter.notifyDataSetChanged();

            }
        });

    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
