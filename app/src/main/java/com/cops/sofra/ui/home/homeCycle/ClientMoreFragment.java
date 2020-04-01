package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.R;
import com.cops.sofra.databinding.FragmentClientMoreBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.ChangePasswordFragment;
import com.cops.sofra.ui.dialog.LogOutDialog;

public class ClientMoreFragment extends BaseFragment {


    private FragmentClientMoreBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_client_more,container,false);
        View view = binding.getRoot();
        setUpActivity();

        binding.moreFragmentAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new AboutUsFragment())
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

        binding.moreFragmentOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,new ClientOfferListFragment())
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

        binding.moreFragmentLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogOutDialog logOutDialog= new LogOutDialog();
                logOutDialog.show(getFragmentManager(),"exit");

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
