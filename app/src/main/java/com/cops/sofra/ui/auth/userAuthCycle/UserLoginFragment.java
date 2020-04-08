package com.cops.sofra.ui.auth.userAuthCycle;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.databinding.FragmentUserLoginBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.CompleteOrderFragment;
import com.cops.sofra.ui.splash.SplashActivity;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.ClientLoginViewModel;
import com.cops.sofra.viewModel.RestaurantLoginViewModel;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.SaveData;
import static com.cops.sofra.ui.auth.userAuthCycle.ClientRegisterFragment.isRegistered;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class UserLoginFragment extends BaseFragment {


    private FragmentUserLoginBinding binding;
    private RestaurantLoginViewModel restaurantLoginViewModel;
    private ClientLoginViewModel clientLoginViewModel;
    private String userType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_user_login,container,false);
       final View view = binding.getRoot();
        setUpActivity();

        if(LoadData(getActivity(), "email")!=null){

            binding.userLoginFragmentEtUserName.setText(LoadData(getActivity(), "email"));
            binding.userLoginFragmentEtPassword.setText(LoadData(getActivity(), "password"));
        }

        if (getArguments()!=null) {
            userType=getArguments().getString("userType");

        }


        binding.userLoginFragmentTvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (userType.equals("client")) {

                        getFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, new ClientRegisterFragment())
                                .addToBackStack(null).commit();
                    } else {

                        getFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, new RegisterRestaurantFragment())
                                .addToBackStack(null).commit();
                    }

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

                if (userType.equals("client")) {
                    clientLogin();

                } else {

                    restaurantLogin();
                }

            }
        });

        binding.userLoginFragmentTvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgetPasswordFragment forgetPasswordFragment=new ForgetPasswordFragment();
                Bundle bundle=new Bundle();
                bundle.putString("userType",userType);
                forgetPasswordFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, forgetPasswordFragment)
                        .addToBackStack(null).commit();
            }
        });



       return view;
    }



    private void clientLogin() {

        if (isEditTextSet(binding.userLoginFragmentEtUserName,binding.userLoginFragmentEtPassword)
                &&isEmailValid(binding.userLoginFragmentEtUserName)) {

            clientLoginViewModel = ViewModelProviders.of(getActivity()).get(ClientLoginViewModel.class);
            clientLoginViewModel.getClientLogin(binding.userLoginFragmentEtUserName.getText().toString(),binding.userLoginFragmentEtPassword.getText().toString());
            clientLoginViewModel.clientLoginMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
                @Override
                public void onChanged(RestaurantLogin restaurantLogin) {
                    if (restaurantLogin.getStatus()==1) {
                        SaveData(getActivity(), "userType", "client");
                        SaveData(getActivity(), "userId", restaurantLogin.getData().getUser().getId());
                        SaveData(getActivity(), "apiToken", restaurantLogin.getData().getApiToken());
                        SaveData(getActivity(), "phone", restaurantLogin.getData().getUser().getPhone());
                        SaveData(getActivity(), "name", restaurantLogin.getData().getUser().getName());
                        SaveData(getActivity(), "email", binding.userLoginFragmentEtUserName.getText().toString());
                        SaveData(getActivity(), "password", binding.userLoginFragmentEtPassword.getText().toString());
                        SaveData(getActivity(), "address",restaurantLogin.getData().getUser().getRegion().getName());

                        Log.i("address",restaurantLogin.getData().getUser().getRegion().getName());
                        if(isRegistered){
                            isRegistered=false;
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.putExtra("userType","client");
                            startActivity(intent);
                        }else
                        onBack();

                    }else {
                        Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    private void restaurantLogin() {

        if (isEditTextSet(binding.userLoginFragmentEtUserName,binding.userLoginFragmentEtPassword)
                &&isEmailValid(binding.userLoginFragmentEtUserName)) {

            restaurantLoginViewModel = ViewModelProviders.of(getActivity()).get(RestaurantLoginViewModel.class);
            restaurantLoginViewModel.getRestaurantLogin(binding.userLoginFragmentEtUserName.getText().toString(),binding.userLoginFragmentEtPassword.getText().toString());
            restaurantLoginViewModel.restaurantsLoginMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
                @Override
                public void onChanged(RestaurantLogin restaurantLogin) {
                    if (restaurantLogin.getStatus()==1) {
                        SaveData(getActivity(), "userType", "seller");
                        SaveData(getActivity(), "apiToken", restaurantLogin.getData().getApiToken());
                        SaveData(getActivity(), "restaurantId", restaurantLogin.getData().getUser().getId());
                        SaveData(getActivity(), "email", binding.userLoginFragmentEtUserName.getText().toString());
                        SaveData(getActivity(), "password", binding.userLoginFragmentEtPassword.getText().toString());
                        Log.i("apiToken",restaurantLogin.getData().getApiToken());
                        Intent intent=new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
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
