package com.cops.sofra.ui.home.homeCycle;

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

import com.cops.sofra.R;
import com.cops.sofra.adapters.RestaurantAdapter;
import com.cops.sofra.adapters.RestaurantReviewAdapter;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviewsData;
import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.databinding.FragmentRateCommentsBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.viewModel.RestaurantReviewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class RateCommentsFragment extends BaseFragment {


   private FragmentRateCommentsBinding binding;
   private RestaurantReviewsViewModel restaurantReviewsViewModel;
   private List<RestaurantReviewsData>restaurantReviewsData=new ArrayList<>();
   private RestaurantReviewAdapter reviewAdapter;
   private LinearLayoutManager layoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_rate_comments,container,false);
        View view = binding.getRoot();
        setUpActivity();
        getRestaurantReviews();



       return view;
    }

    private void getRestaurantReviews() {
        restaurantReviewsViewModel= ViewModelProviders.of(getActivity()).get(RestaurantReviewsViewModel.class);
        restaurantReviewsViewModel.getRestaurantReviews("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", RestaurantAdapter.restaurantId);
        reviewAdapter=new RestaurantReviewAdapter(getActivity(),restaurantReviewsData);
        layoutManager=new LinearLayoutManager(getActivity());
        binding.rateCommentsFragmentRv.setLayoutManager(layoutManager);
        binding.rateCommentsFragmentRv.setAdapter(reviewAdapter);
        restaurantReviewsViewModel.restaurantsReviewsMutableLiveData.observe(this, new Observer<List<RestaurantReviewsData>>() {
            @Override
            public void onChanged(List<RestaurantReviewsData> restaurantReviews) {
                restaurantReviewsData.addAll(restaurantReviews);
                reviewAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
