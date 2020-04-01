package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.R;
import com.cops.sofra.adapters.CategoryAdapter;
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.adapters.RestaurantItemsAdapter;
import com.cops.sofra.data.model.category.CategoryData;
import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.databinding.FragmentRestaurantItemFoodListBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.CategoryViewModel;
import com.cops.sofra.viewModel.RestaurantItemsViewModel;

import java.util.ArrayList;
import java.util.List;


public class RestaurantItemFoodListFragment extends BaseFragment implements CategoryAdapter.RecyclerViewClickListener {


    public RestaurantItemFoodListFragment(int restaurantId,String deliveryCost) {
        this.restaurantId = restaurantId;
        this.deliveryCost=deliveryCost;
    }

    private FragmentRestaurantItemFoodListBinding binding;
    private CategoryViewModel categoryViewModel;
    private List<CategoryData>categoryDataList=new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager layoutManager;
    private RestaurantItemsViewModel restaurantItemsViewModel;
    private List<Item>restaurantItemsData=new ArrayList<>();
    private RestaurantItemsAdapter itemsAdapter;
    private LinearLayoutManager itemsLayoutManager;
    private OnEndLess onEndLess;
    private int lastPage;
    private int categoryId=0;
    public static int restaurantId;
    public static String deliveryCost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_item_food_list,container,false);
        View view = binding.getRoot();
        setUpActivity();

        getCategory();

        getRestaurantItems();


       return view;
    }
    @Override
    public void recyclerViewListClicked(View v, int position) {
        categoryId=position+1;
        getRestaurantItems();
        Log.i("position", String.valueOf(categoryId));
    }

    private void getRestaurantItems() {
        restaurantItemsViewModel=ViewModelProviders.of(getActivity()).get( RestaurantItemsViewModel.class);
        if(categoryId>0){
            restaurantItemsViewModel.getRestaurantItem(restaurantId,categoryId,1);
        }
        itemsAdapter =new RestaurantItemsAdapter(getActivity(),restaurantItemsData);
        itemsLayoutManager=new LinearLayoutManager(getActivity());
        binding.restaurantItemFoodListFragmentRvFood.setLayoutManager(itemsLayoutManager);
        //binding.foodFragmentItemRvFood.setAdapter(itemsAdapter);

        onEndLess = new OnEndLess(itemsLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        restaurantItemsViewModel.getRestaurantItem(restaurantId,categoryId,current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };


        binding.restaurantItemFoodListFragmentRvFood.addOnScrollListener(onEndLess);
        binding.restaurantItemFoodListFragmentRvFood.setAdapter(itemsAdapter);

        if (restaurantItemsData.size()==0) {
            restaurantItemsViewModel.getRestaurantItem(restaurantId,categoryId,1);
        }

        binding.restaurantItemFoodListFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantItemsData.size()==0) {

                    restaurantItemsViewModel.getRestaurantItem(restaurantId,categoryId,1);
                }else
                binding.restaurantItemFoodListFragmentSwipe.setRefreshing(false);
            }
        });


        restaurantItemsViewModel.restaurantsItemsMutableLiveData.observe(this, new Observer<RestaurantItems>() {
            @Override
            public void onChanged(RestaurantItems restaurantItems) {
                lastPage=restaurantItems.getData().getLastPage();
                restaurantItemsData.clear();
                binding.restaurantItemFoodListFragmentSwipe.setRefreshing(false);

                restaurantItemsData.addAll(restaurantItems.getData().getData());
                itemsAdapter.notifyDataSetChanged();
            }
        });


    }

    private void getCategory() {
        categoryAdapter =new CategoryAdapter(getActivity(),categoryDataList,this);
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.restaurantItemFoodListFragmentRvCategory.setLayoutManager(layoutManager);
        binding.restaurantItemFoodListFragmentRvCategory.setAdapter(categoryAdapter);

        categoryViewModel= ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);

        if (categoryDataList.size()==0) {
            categoryViewModel.getCategory(restaurantId);
        }

        categoryViewModel.categoryMutableLiveData.observe(this, new Observer<List<CategoryData>>() {
            @Override
            public void onChanged(List<CategoryData> categoryData) {
                categoryDataList.clear();
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
