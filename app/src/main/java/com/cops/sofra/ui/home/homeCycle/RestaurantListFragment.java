package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.adapters.CitySpinnerAdapter;
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.adapters.RestaurantItemsAdapter;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.city.CityData;
import com.cops.sofra.data.model.restaurantLogin.User;
import com.cops.sofra.data.model.restaurants.Restaurants;
import com.cops.sofra.databinding.FragmentRestaurantListBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.R;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.CityViewModel;
import com.cops.sofra.viewModel.RestaurantListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.utils.GeneralResponse.getCity;


public class RestaurantListFragment extends BaseFragment {

    private RestaurantAdapter restaurantAdapter;
    private LinearLayoutManager layoutManager;
    private List<User> restaurantsList = new ArrayList<>();
    private ArrayList<CityData> cityList = new ArrayList<>();
    private RestaurantListViewModel restaurantViewModel;
    private FragmentRestaurantListBinding binding;
    private OnEndLess onEndLess;
    private int lastPage;
    private int regionId=0;
    private String key="";
    private boolean doubleBackToExistNotOnce;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_list, container, false);
        View view = binding.getRoot();
        setUpActivity();

        getRestaurant();

        getCity(getActivity(),cityList,binding.restaurantListFragmentSpCity);

        binding.restaurantListFragmentSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                regionId=position;
                restaurantsList =new ArrayList<>();
                restaurantAdapter =new RestaurantAdapter(getActivity(),restaurantsList);
                layoutManager=new LinearLayoutManager(getActivity());
                binding.restaurantListFragmentRv.setLayoutManager(layoutManager);
                binding.restaurantListFragmentRv.setAdapter(restaurantAdapter);
              //  getRestaurant();
                restaurantViewModel.getRestaurantList(key,regionId,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.restaurantListFragmentSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                regionId=binding.restaurantListFragmentSpCity.getSelectedItemPosition();
                key=query;
                restaurantViewModel.getRestaurantList(key,regionId,1);
               // getRestaurant();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                key=newText;

                restaurantViewModel.getRestaurantList(key,regionId,1);
                return false;
            }
        });




        return view;
    }


    private void getRestaurant() {
        restaurantViewModel = ViewModelProviders.of(getActivity()).get(RestaurantListViewModel.class);
//        if(!key.equals("")|| regionId>0){
//
//            restaurantViewModel.getRestaurantList(key,regionId,1);
//        }

        restaurantAdapter = new RestaurantAdapter(getActivity(), restaurantsList);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.restaurantListFragmentRv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        //loadMore.setVisibility(View.VISIBLE);
                        Log.i("page", String.valueOf(current_page));
                        restaurantViewModel.getRestaurantList(key,regionId,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };

        binding.restaurantListFragmentRv.addOnScrollListener(onEndLess);


        if (restaurantsList.size() == 0) {
            restaurantViewModel.getRestaurantList(key,regionId,1);
        }


        binding.restaurantListFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantsList.size() == 0) {

                    restaurantViewModel.getRestaurantList(key,regionId,1);

                }else
                {

                    binding.restaurantListFragmentSwipe.setRefreshing(false);
                }

            }
        });
        binding.restaurantListFragmentRv.setAdapter(restaurantAdapter);
        restaurantViewModel.restaurantsMutableLiveData.observe(this, new Observer<Restaurants>() {
            @Override
            public void onChanged(Restaurants restaurantData) {
                if(restaurantData.getStatus()==1){
                    lastPage=restaurantData.getData().getLastPage();
                    binding.restaurantListFragmentSwipe.setRefreshing(false);
                    if(!key.equals("")|| regionId>0 || onEndLess.current_page==1){
                        restaurantsList.clear();
                        restaurantsList.addAll(restaurantData.getData().getData());
                    }else {

                        restaurantsList.addAll(restaurantData.getData().getData());
                    }



                    restaurantAdapter.notifyDataSetChanged();
                }



            }
        });

    }


    @Override
    public void onBack() {

        if(doubleBackToExistNotOnce) {
            baseActivity.finishAffinity();
        }
        this.doubleBackToExistNotOnce=true;
        Toast.makeText(getActivity(), getString(R.string.exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExistNotOnce=false;
            }
        },2000);

    }
}
