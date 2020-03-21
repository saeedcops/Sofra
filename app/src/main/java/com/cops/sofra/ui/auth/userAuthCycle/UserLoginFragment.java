package com.cops.sofra.ui.auth.userAuthCycle;

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

import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.databinding.FragmentUserLoginBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.viewModel.ClientLoginViewModel;
import com.cops.sofra.viewModel.RestaurantLoginViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.cops.sofra.data.local.SharedPreferencesManger.SaveData;
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_user_login,container,false);
       final View view = binding.getRoot();
        setUpActivity();

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
                        SaveData(getActivity(), "apiToken", restaurantLogin.getData().getApiToken());
                        SaveData(getActivity(), "email", binding.userLoginFragmentEtUserName.getText().toString());
                        SaveData(getActivity(), "password", binding.userLoginFragmentEtPassword.getText().toString());

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
                        SaveData(getActivity(), "email", binding.userLoginFragmentEtUserName.getText().toString());
                        SaveData(getActivity(), "password", binding.userLoginFragmentEtPassword.getText().toString());

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
