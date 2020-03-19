package com.cops.sofra.ui.auth.userAuthCycle;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.databinding.FragmentUserLoginBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.splash.SplashActivity;
import com.cops.sofra.viewModel.RestaurantLoginViewModel;

import static com.cops.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class UserLoginFragment extends BaseFragment {


    private FragmentUserLoginBinding binding;
    private RestaurantLoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_user_login,container,false);
        final View view = binding.getRoot();
        setUpActivity();

        binding.userLoginFragmentTvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame,new RegisterRestaurantFragment())
                        .addToBackStack(null).commit();
            }
        });

        binding.userLoginFragmentRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });

        binding.userLoginFragmentBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantLogin();
            }
        });


        //SaveData(SplashActivity.this, "userType", "client");
       return view;
    }

    private void restaurantLogin() {

        if (isEditTextSet(binding.userLoginFragmentEtUserName,binding.userLoginFragmentEtPassword)
                &&isEmailValid(binding.userLoginFragmentEtUserName)) {

            loginViewModel= ViewModelProviders.of(getActivity()).get(RestaurantLoginViewModel.class);
            loginViewModel.getRestaurantLogin(binding.userLoginFragmentEtUserName.getText().toString(),binding.userLoginFragmentEtPassword.getText().toString());
            loginViewModel.restaurantsLoginMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
                @Override
                public void onChanged(RestaurantLogin restaurantLogin) {
                    if (restaurantLogin.getStatus()==1) {
                        SaveData(getActivity(), "userType", "seller");
                        Toast.makeText(baseActivity, "good", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
