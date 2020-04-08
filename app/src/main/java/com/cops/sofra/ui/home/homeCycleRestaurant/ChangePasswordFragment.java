package com.cops.sofra.ui.home.homeCycleRestaurant;

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
import com.cops.sofra.databinding.FragmentChangePasswordBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.client.ClientChangePasswordViewModel;
import com.cops.sofra.viewModel.restaurant.RestaurantChangePasswordViewModel;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.SaveData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isPasswordMatched;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class ChangePasswordFragment extends BaseFragment {


    private FragmentChangePasswordBinding binding;
    private RestaurantChangePasswordViewModel changePasswordViewModel;
    private ClientChangePasswordViewModel clientChangePasswordViewModel;
    private String apiToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);
        final View view = binding.getRoot();
        setUpActivity();

        if (LoadData(getActivity(), "apiToken") != null) {

            apiToken = LoadData(getActivity(), "apiToken");

        }
        binding.changePasswordFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditTextSet(binding.changePasswordFragmentOldPassword, binding.changePasswordFragmentPassword, binding.changePasswordFragmentPldPasswordConfirm)
                        && isPasswordMatched(binding.changePasswordFragmentPassword, binding.changePasswordFragmentPldPasswordConfirm)) {
                    if (LoadData(getActivity(), "apiToken") != null){

                        if(LoadData(getActivity(), "userType").equals("seller")){
                            changePassword();
                        }else{
                            clientChangePassword();
                        }
                    }else {
                        Toast.makeText(baseActivity, getString(R.string.please_login), Toast.LENGTH_LONG).show();
                    }



                }
            }
        });
        binding.changePasswordFragmentParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });


        return view;
    }

    private void clientChangePassword() {

        clientChangePasswordViewModel = ViewModelProviders.of(getActivity()).get(ClientChangePasswordViewModel.class);

        clientChangePasswordViewModel.clientChangePassword(apiToken,binding.changePasswordFragmentOldPassword.getText().toString(),
                binding.changePasswordFragmentPassword.getText().toString(),binding.changePasswordFragmentPldPasswordConfirm.getText().toString());

        clientChangePasswordViewModel.clientChangePasswordMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
            @Override
            public void onChanged(RestaurantLogin restaurantLogin) {

                if (restaurantLogin.getStatus()==1) {
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();

                    SaveData(getActivity(),"password",binding.changePasswordFragmentPassword);
                    onBack();
                }else{

                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void changePassword() {

        changePasswordViewModel = ViewModelProviders.of(getActivity()).get(RestaurantChangePasswordViewModel.class);

        changePasswordViewModel.changePassword(apiToken,binding.changePasswordFragmentOldPassword.getText().toString(),
                binding.changePasswordFragmentPassword.getText().toString(),binding.changePasswordFragmentPldPasswordConfirm.getText().toString());

        changePasswordViewModel.restaurantsChangePasswordMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
            @Override
            public void onChanged(RestaurantLogin restaurantLogin) {

                if (restaurantLogin.getStatus()==1) {
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();

                    SaveData(getActivity(),"password",binding.changePasswordFragmentPassword);
                    onBack();
                }else{

                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
