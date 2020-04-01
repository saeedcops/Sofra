package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.content.Intent;
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

import com.cops.sofra.adapters.RestaurantReviewAdapter;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviewsData;

import com.cops.sofra.databinding.FragmentRestaurantReviewBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.auth.AuthActivity;
import com.cops.sofra.ui.dialog.AddReviewDialog;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.RestaurantReviewsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class RestaurantReviewFragment extends BaseFragment {


   private FragmentRestaurantReviewBinding binding;
   private RestaurantReviewsViewModel restaurantReviewsViewModel;
   private List<RestaurantReviewsData>restaurantReviewsData=new ArrayList<>();
   private RestaurantReviewAdapter reviewAdapter;
   private LinearLayoutManager layoutManager;
   private OnEndLess onEndLess;
   private int lastPage;
    private String apiToken;
    private int restaurantId;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_review,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if(getArguments()!=null ){
            binding.restaurantReviewFragmentLlAdd.setVisibility(View.VISIBLE);
            restaurantId=getArguments().getInt("restaurantId");
            if(LoadData(getActivity(), "apiToken")!=null)
            apiToken = LoadData(getActivity(), "apiToken");
            else
                apiToken="";

        } else if (LoadData(getActivity(), "apiToken") != null && LoadData(getActivity(), "restaurantId")!=null) {
//            apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
//            restaurantId=1;
            apiToken = LoadData(getActivity(), "apiToken");
            restaurantId = Integer.parseInt(LoadData(getActivity(), "restaurantId"));

        }

        binding.restaurantReviewFragmentBtnNewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoadData(getActivity(), "apiToken") == null) {

                    Toast.makeText(baseActivity, getString(R.string.go_login), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    intent.putExtra("userType","client");
                    startActivity(intent);

                } else {
                    AddReviewDialog dialog = new AddReviewDialog(restaurantId);
                    dialog.show(getChildFragmentManager(), "dialog");
                }
            }
        });

        getRestaurantReviews();



       return view;
    }

    private void getRestaurantReviews() {
        restaurantReviewsViewModel= ViewModelProviders.of(getActivity()).get(RestaurantReviewsViewModel.class);
        reviewAdapter=new RestaurantReviewAdapter(getActivity(),restaurantReviewsData);
        layoutManager=new LinearLayoutManager(getActivity());
        binding.restaurantReviewFragmentRv.setLayoutManager(layoutManager);
        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        restaurantReviewsViewModel.getRestaurantReviews(apiToken,current_page,restaurantId);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.restaurantReviewFragmentRv.addOnScrollListener(onEndLess);
        binding.restaurantReviewFragmentRv.setAdapter(reviewAdapter);

        if (restaurantReviewsData.size()==0) {

            restaurantReviewsViewModel.getRestaurantReviews(apiToken,1,restaurantId);
        }

        binding.restaurantReviewFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (restaurantReviewsData.size() == 0) {

                    restaurantReviewsViewModel.getRestaurantReviews(apiToken,1,restaurantId);
                }
                binding.restaurantReviewFragmentSwipe.setRefreshing(false);
            }
        });

        binding.restaurantReviewFragmentRv.setAdapter(reviewAdapter);
        restaurantReviewsViewModel.restaurantsReviewsMutableLiveData.observe(this, new Observer<RestaurantReviews>() {
            @Override
            public void onChanged(RestaurantReviews restaurantReviews) {
                if (restaurantReviews.getStatus()==1) {
                    lastPage=restaurantReviews.getData().getLastPage();
                    restaurantReviewsData.clear();
                    restaurantReviewsData.addAll(restaurantReviews.getData().getData());
                    reviewAdapter.notifyDataSetChanged();


                }else {

                    Toast.makeText(baseActivity, restaurantReviews.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
