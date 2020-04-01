package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.R;
import com.cops.sofra.adapters.RestaurantOfferAdapter;
import com.cops.sofra.data.model.myOffer.MyOffer;
import com.cops.sofra.data.model.myOffer.MyOfferData;
import com.cops.sofra.databinding.FragmentRestaurantMoreBinding;
import com.cops.sofra.databinding.FragmentRestaurantOfferListBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.restaurant.RestaurantGetMyOfferViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class RestaurantOfferListFragment extends BaseFragment {


    private FragmentRestaurantOfferListBinding binding;
    private RestaurantGetMyOfferViewModel offerViewModel;
    private RestaurantOfferAdapter offerAdapter;
    private LinearLayoutManager layoutManager;
    private List<MyOfferData> myOfferData=new ArrayList<>();
    private OnEndLess onEndLess;
    private int lastPage;
    private String apiToken="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_offer_list,container,false);
        View view = binding.getRoot();
        setUpActivity();
     //  apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            apiToken=LoadData(getActivity(),"apiToken");

        }

        binding.restaurantOfferListFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new CreateRestaurantOfferFragment()).commit();
            }
        });
        getMyOffers();

       return view;
    }

    private void getMyOffers(){

        offerViewModel= ViewModelProviders.of(getActivity()).get(RestaurantGetMyOfferViewModel.class);
        layoutManager=new LinearLayoutManager(getActivity());
        offerAdapter =new RestaurantOfferAdapter(getActivity(),myOfferData);
        binding.restaurantOfferListFragmentRv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        offerViewModel.getOfferList(apiToken,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.restaurantOfferListFragmentRv.addOnScrollListener(onEndLess);
        if (myOfferData.size()==0) {

            offerViewModel.getOfferList(apiToken,1);
        }
        binding.restaurantOfferListFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (myOfferData.size() == 0) {

                    offerViewModel.getOfferList(apiToken,onEndLess.current_page);
                }else{
                    binding.restaurantOfferListFragmentSwipe.setRefreshing(false);
                }

            }
        });

        binding.restaurantOfferListFragmentRv.setAdapter(offerAdapter);

        offerViewModel.restaurantsGetofferMutableLiveData.observe(this, new Observer<MyOffer>() {
            @Override
            public void onChanged(MyOffer myOffer) {

                if (myOffer.getStatus()==1) {
                    lastPage= myOffer.getData().getLastPage();
                    myOfferData.clear();
                    myOfferData.addAll(myOffer.getData().getData());
                    offerAdapter.notifyDataSetChanged();
                }
            }
        });

    }
    @Override
    public void onBack() {
        super.onBack();
    }
}
