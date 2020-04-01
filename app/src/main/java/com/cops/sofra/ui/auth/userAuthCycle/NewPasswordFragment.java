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
import com.cops.sofra.databinding.FragmentNewPasswordBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.ClientNewPasswordViewModel;
import com.cops.sofra.viewModel.RestaurantNewPasswordViewModel;

import static com.cops.sofra.utils.CheckInput.isPasswordMatched;

public class NewPasswordFragment extends BaseFragment {


    private FragmentNewPasswordBinding binding;
    private String userType;
    private ClientNewPasswordViewModel clientNewPasswordViewModel;
    private RestaurantNewPasswordViewModel restaurantNewPasswordViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_new_password,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (getArguments()!=null) {
            userType=getArguments().getString("userType");

        }
        binding.newPasswordFragmentBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.newPasswordFragmentEtCode.getText().toString().equals("")){
                    binding.newPasswordFragmentEtCode.setError(getString(R.string.enter_your_code));
                    binding.newPasswordFragmentEtCode.requestFocus();
                }else {
                    if(isPasswordMatched(binding.newPasswordFragmentEtPassword,binding.newPasswordFragmentEtPasswordConfirm)){

                        if (userType.equals("client")) {
                            clientNewPassword();

                        } else {

                            restaurantNewPassword();
                        }
                    }
                }
            }
        });



       return view;
    }

    private void clientNewPassword() {

        clientNewPasswordViewModel= ViewModelProviders.of(getActivity()).get(ClientNewPasswordViewModel.class);
        clientNewPasswordViewModel.getClientNewPassword(binding.newPasswordFragmentEtCode.getText().toString(),
                binding.newPasswordFragmentEtPassword.getText().toString(),binding.newPasswordFragmentEtPasswordConfirm.getText().toString());

        clientNewPasswordViewModel.clientNewPasswordMutableLiveData.observe(this, new Observer<ResetPassword>() {
            @Override
            public void onChanged(ResetPassword resetPassword) {
                if(resetPassword.getStatus()==1){

                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_LONG).show();

                    UserLoginFragment userLoginFragment=new UserLoginFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userType",userType);
                    userLoginFragment.setArguments(bundle);

                    getChildFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, userLoginFragment)
                            .addToBackStack(null).commit();


                }else {

                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void restaurantNewPassword() {

        restaurantNewPasswordViewModel=ViewModelProviders.of(getActivity()).get(RestaurantNewPasswordViewModel.class);
        restaurantNewPasswordViewModel.getRestaurantNewPassword(binding.newPasswordFragmentEtCode.getText().toString(),
                binding.newPasswordFragmentEtPassword.getText().toString(),binding.newPasswordFragmentEtPasswordConfirm.getText().toString());

        restaurantNewPasswordViewModel.restaurantNewPasswordMutableLiveData.observe(this, new Observer<ResetPassword>() {
            @Override
            public void onChanged(ResetPassword resetPassword) {
                if(resetPassword.getStatus()==1){

                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_LONG).show();

                    UserLoginFragment userLoginFragment=new UserLoginFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userType",userType);
                    userLoginFragment.setArguments(bundle);

                    getChildFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, userLoginFragment)
                            .addToBackStack(null).commit();


                }else {

                    Toast.makeText(baseActivity, resetPassword.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
