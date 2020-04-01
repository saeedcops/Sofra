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
import com.cops.sofra.data.model.newPassword.ResetPassword;
import com.cops.sofra.databinding.FragmentForgetPasswordBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.ClientResetPasswordViewModel;
import com.cops.sofra.viewModel.RestaurantResetPasswordViewModel;

import static com.cops.sofra.utils.CheckInput.isEmailValid;

public class ForgetPasswordFragment extends BaseFragment {


    private FragmentForgetPasswordBinding binding;
    private String userType;
    private ClientResetPasswordViewModel clientResetPasswordViewModel;
    private RestaurantResetPasswordViewModel restaurantResetPasswordViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_forget_password,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (getArguments()!=null) {
            userType=getArguments().getString("userType");

        }

        binding.forgetPasswordFragmentBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(binding.forgetPasswordFragmentEtEmail)){
                    if (userType.equals("client")) {
                        clientResetPassword();

                    } else {

                        restaurantResetPassword();
                    }
                }
            }
        });


       return view;
    }

    private void clientResetPassword() {
        clientResetPasswordViewModel= ViewModelProviders.of(getActivity()).get(ClientResetPasswordViewModel.class);
        clientResetPasswordViewModel.clientResetPassword(binding.forgetPasswordFragmentEtEmail.getText().toString());
        clientResetPasswordViewModel.clientResetPasswordMutableLiveData.observe(this, new Observer<ResetPassword>() {
            @Override
            public void onChanged(ResetPassword resetPassword) {
                if(resetPassword.getStatus()==1){

                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_SHORT).show();

                    NewPasswordFragment newPasswordFragment=new NewPasswordFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userType",userType);
                    newPasswordFragment.setArguments(bundle);

                    getChildFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, newPasswordFragment)
                            .addToBackStack(null).commit();

                }else {
                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void restaurantResetPassword() {

        restaurantResetPasswordViewModel=ViewModelProviders.of(getActivity()).get(RestaurantResetPasswordViewModel.class);
        restaurantResetPasswordViewModel.restaurantResetPassword(binding.forgetPasswordFragmentEtEmail.getText().toString());
        restaurantResetPasswordViewModel.restaurantResetPasswordMutableLiveData.observe(this, new Observer<ResetPassword>() {
            @Override
            public void onChanged(ResetPassword resetPassword) {
                if (resetPassword.getStatus()==1) {

                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_SHORT).show();

                    NewPasswordFragment newPasswordFragment=new NewPasswordFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userType",userType);
                    newPasswordFragment.setArguments(bundle);

                    getChildFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, newPasswordFragment)
                            .addToBackStack(null).commit();

                }else {
                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
