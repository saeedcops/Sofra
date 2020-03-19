package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.cops.sofra.R;
import com.cops.sofra.adapters.CategoryAdapter;
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.adapters.RestaurantItemsAdapter;
import com.cops.sofra.data.model.category.CategoryData;
import com.cops.sofra.data.model.restaurantItems.RestaurantItemsData;
import com.cops.sofra.databinding.FragmentFoodListBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.CategoryViewModel;
import com.cops.sofra.viewModel.RestaurantItemsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.viewModel.RestaurantItemsViewModel.itemLastPage;
import static com.cops.sofra.viewModel.RestaurantViewModel.lastPage;

public class FoodListFragment extends BaseFragment {



    private FragmentFoodListBinding binding;
    private CategoryViewModel categoryViewModel;
    private List<CategoryData>categoryDataList=new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager layoutManager;
    private RestaurantItemsViewModel restaurantItemsViewModel;
    private List<RestaurantItemsData>restaurantItemsData=new ArrayList<>();
    private RestaurantItemsAdapter itemsAdapter;
    private LinearLayoutManager itemsLayoutManager;
    private OnEndLess onEndLess;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_food_list,container,false);
        View view = binding.getRoot();
        setUpActivity();

        getCategory();

        getRestaurantItems();



       return view;
    }

    private void getRestaurantItems() {
        restaurantItemsViewModel=ViewModelProviders.of(getActivity()).get( RestaurantItemsViewModel.class);
        itemsAdapter =new RestaurantItemsAdapter(getActivity(),restaurantItemsData);
        itemsLayoutManager=new LinearLayoutManager(getActivity());
        binding.foodListFragmentRvFood.setLayoutManager(itemsLayoutManager);
        //binding.foodFragmentItemRvFood.setAdapter(itemsAdapter);

        onEndLess = new OnEndLess(itemsLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= itemLastPage) {

                    if (itemLastPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        restaurantItemsViewModel.getRestaurantItem(RestaurantAdapter.restaurantId,current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };


        binding.foodListFragmentRvFood.addOnScrollListener(onEndLess);
        binding.foodListFragmentRvFood.setAdapter(itemsAdapter);

        if (restaurantItemsData.size()==0) {
            restaurantItemsViewModel.getRestaurantItem(RestaurantAdapter.restaurantId,1);
        }

        binding.foodListFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantItemsData.size()==0) {

                    restaurantItemsViewModel.getRestaurantItem(RestaurantAdapter.restaurantId,1);
                }
                binding.foodListFragmentSwipe.setRefreshing(false);
            }
        });


        restaurantItemsViewModel.restaurantsItemsMutableLiveData.observe(this, new Observer<List<RestaurantItemsData>>() {
            @Override
            public void onChanged(List<RestaurantItemsData> restaurantItems) {
                binding.foodListFragmentSwipe.setRefreshing(false);

                restaurantItemsData.addAll(restaurantItems);
                itemsAdapter.notifyDataSetChanged();

            }
        });


    }

    private void getCategory() {
        categoryAdapter =new CategoryAdapter(getActivity(),categoryDataList);
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.foodListFragmentRvCategory.setLayoutManager(layoutManager);
        binding.foodListFragmentRvCategory.setAdapter(categoryAdapter);

        categoryViewModel= ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);

        if (categoryDataList.size()==0) {
            categoryViewModel.getCategory(RestaurantAdapter.restaurantId);
        }





        categoryViewModel.categoryMutableLiveData.observe(this, new Observer<List<CategoryData>>() {
            @Override
            public void onChanged(List<CategoryData> categoryData) {
                categoryDataList.addAll(categoryData);
                categoryAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
