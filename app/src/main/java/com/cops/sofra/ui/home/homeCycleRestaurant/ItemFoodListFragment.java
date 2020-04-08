package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.cops.sofra.adapters.RestaurantItemFoodListAdapter;
import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.databinding.FragmentItemFoodListBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.RestaurantGetFoodItemListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class ItemFoodListFragment extends BaseFragment implements HomeActivity.FabButtonClick {


    private FragmentItemFoodListBinding binding;
    private int categoryId;
    private RestaurantGetFoodItemListViewModel restaurantGetFoodItemListViewModel;
    private RestaurantItemFoodListAdapter itemFoodListAdapter;
    private LinearLayoutManager layoutManager;
    private List<Item> restaurantItems=new ArrayList<>();
    private String apiToken;
    private String name;
    private OnEndLess onEndLess;
    private int lastPage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_item_food_list,container,false);
        View view = binding.getRoot();
        setUpActivity();
        ((HomeActivity)getActivity()).setListener(this);
      //  apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

           apiToken=LoadData(getActivity(),"apiToken");

        }
        if (getArguments()!=null) {
            categoryId=getArguments().getInt("categoryId");
            name=getArguments().getString("name");
        }
        binding.itemFoodListFragmentTvName.setText(name);

        getFoodItemList();
       return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        restaurantGetFoodItemListViewModel.getFoodItemList(apiToken,categoryId,1);
    }

    @Override
    public void onFabClicked() {
        Bundle bundle=new Bundle();
        bundle.putInt("categoryId",categoryId);
        CreateItemFoodFragment createItemFoodFragment=new CreateItemFoodFragment();
        createItemFoodFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .addToBackStack(null).replace(R.id.home_activity_fl_frame,createItemFoodFragment).commit();


    }

    private void getFoodItemList(){
        restaurantGetFoodItemListViewModel= ViewModelProviders.of(getActivity()).get(RestaurantGetFoodItemListViewModel.class);
        layoutManager=new LinearLayoutManager(getActivity());
        binding.itemFoodLisFragmentRv.setLayoutManager(layoutManager);
        itemFoodListAdapter=new RestaurantItemFoodListAdapter(getActivity(),restaurantItems);
        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        restaurantGetFoodItemListViewModel.getFoodItemList(apiToken,categoryId,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.itemFoodLisFragmentRv.addOnScrollListener(onEndLess);
        if (restaurantItems.size()==0) {

            restaurantGetFoodItemListViewModel.getFoodItemList(apiToken,categoryId,1);
        }


        binding.itemFoodListFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantItems.size() == 0) {

                    restaurantGetFoodItemListViewModel.getFoodItemList(apiToken,categoryId,1);
                }else {
                    binding.itemFoodListFragmentSwipe.setRefreshing(false);
                }

            }
        });

        binding.itemFoodLisFragmentRv.setAdapter(itemFoodListAdapter);
        restaurantGetFoodItemListViewModel.restaurantsGetFoodItemListMutableLiveData.observe(this, new Observer<RestaurantItems>() {
            @Override
            public void onChanged(RestaurantItems Items) {
                if (Items.getStatus()==1) {

                    binding.itemFoodListFragmentSwipe.setRefreshing(false);
                    lastPage=Items.getData().getLastPage();
                    if(onEndLess.current_page==1){

                        restaurantItems.clear();
                    }

                    restaurantItems.addAll(Items.getData().getData());
                    itemFoodListAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }

}
