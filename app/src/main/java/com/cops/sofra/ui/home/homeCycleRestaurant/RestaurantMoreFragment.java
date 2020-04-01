package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.R;

import com.cops.sofra.databinding.FragmentRestaurantMoreBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.homeCycle.AboutUsFragment;
import com.cops.sofra.ui.home.homeCycle.ContactUsFragment;
import com.cops.sofra.ui.dialog.LogOutDialog;

public class RestaurantMoreFragment extends BaseFragment {


    FragmentRestaurantMoreBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_more,container,false);
        View view = binding.getRoot();
        setUpActivity();

        binding.moreFragmentAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new AboutUsFragment())
                        .addToBackStack(null).commit();
            }
        });

        binding.moreFragmentOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new RestaurantOfferListFragment())
                        .addToBackStack(null).commit();
            }
        });
        binding.moreFragmentChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new ChangePasswordFragment())
                        .addToBackStack(null).commit();
            }
        });
        binding.moreFragmentRateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new RestaurantReviewFragment())
                        .addToBackStack(null).commit();
            }
        });
        binding.moreFragmentLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutDialog logOutDialog= new LogOutDialog();
                logOutDialog.show(getChildFragmentManager(),"exit");
            }
        });

        binding.moreFragmentContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new ContactUsFragment())
                        .addToBackStack(null).commit();
            }
        });


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
