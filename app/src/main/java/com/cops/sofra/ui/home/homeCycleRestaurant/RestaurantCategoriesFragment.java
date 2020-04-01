package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.R;
import com.cops.sofra.adapters.RestaurantCategoriesAdapter;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategories;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategoriesData;
import com.cops.sofra.databinding.FragmentRestaurantCategoriesBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.dialog.ItemDialog;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.RestaurantCategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class RestaurantCategoriesFragment extends BaseFragment implements HomeActivity.FabButtonClick {


    private FragmentRestaurantCategoriesBinding binding;
    private RestaurantCategoriesViewModel restaurantCategoriesViewModel;
    private RestaurantCategoriesAdapter categoriesAdapter;
    private List<RestaurantCategoriesData> restaurantCategoriesData=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private String apiToken;
    private OnEndLess onEndLess;
    private int lastPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_categories,container,false);
        View view = binding.getRoot();
        ((HomeActivity)getActivity()).setListener(this);
        setUpActivity();

//        apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
//        getCategories();

        if (LoadData(getActivity(),"apiToken")!=null) {

            apiToken=LoadData(getActivity(),"apiToken");
            getCategories();
        }


       return view;
    }
    //implementing FabButtonClick method which is in homeActivity
    @Override
    public void onFabClicked() {

        ItemDialog dialog=new ItemDialog();
        dialog.show(getChildFragmentManager(),"Dialog");

    }



    private void getCategories() {
        restaurantCategoriesViewModel= ViewModelProviders.of(getActivity()).get(RestaurantCategoriesViewModel.class);
        categoriesAdapter=new RestaurantCategoriesAdapter(getActivity(),restaurantCategoriesData);
        layoutManager=new LinearLayoutManager(getActivity());
        binding.restaurantCategoriesFragmentRv.setLayoutManager(layoutManager);


        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        restaurantCategoriesViewModel.getRestaurantCategories(apiToken,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };

        binding.restaurantCategoriesFragmentRv.addOnScrollListener(onEndLess);
        binding.restaurantCategoriesFragmentRv.setAdapter(categoriesAdapter);

        if (restaurantCategoriesData.size()==0) {

            restaurantCategoriesViewModel.getRestaurantCategories(apiToken,1);
        }

        binding.restaurantCategoriesFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantCategoriesData.size() == 0) {

                    restaurantCategoriesViewModel.getRestaurantCategories(apiToken,1);
                }
                binding.restaurantCategoriesFragmentSwipe.setRefreshing(false);
            }
        });

        restaurantCategoriesViewModel.restaurantsCategoriesMutableLiveData.observe(this, new Observer<RestaurantCategories>() {
            @Override
            public void onChanged(RestaurantCategories restaurantCategories) {
                if (restaurantCategories.getStatus()==1) {
                    lastPage=restaurantCategories.getData().getLastPage();
                    restaurantCategoriesData.clear();
                    restaurantCategoriesData.addAll(restaurantCategories.getData().getData());
                    categoriesAdapter.notifyDataSetChanged();


                }else {

                    Toast.makeText(baseActivity, restaurantCategories.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onBack() {
        super.onBack();
    }



}
