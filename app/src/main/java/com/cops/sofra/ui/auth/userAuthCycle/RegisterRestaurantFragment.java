package com.cops.sofra.ui.auth.userAuthCycle;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.adapters.CitySpinnerAdapter;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.city.CityData;
import com.cops.sofra.databinding.FragmentRegisterRestaurantBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.CityViewModel;
import com.cops.sofra.viewModel.RegionViewModel;

import java.util.ArrayList;

import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.CheckInput.isPasswordMatched;
import static com.cops.sofra.utils.GeneralResponse.getCityAndRegion;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class RegisterRestaurantFragment extends BaseFragment {


    private FragmentRegisterRestaurantBinding binding;
    private ArrayList<CityData> cityList=new ArrayList<>();
    private ArrayList<CityData> regionList=new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to use getString() method in CheckInput static methods
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_register_restaurant,container,false);
        final View view = binding.getRoot();
        setUpActivity();

        getCityAndRegion(getActivity(),cityList,binding.registerRestaurantFragmentSpCity,regionList,binding.registerRestaurantFragmentSpRegion);




        binding.registerRestaurantFragmentBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEditTextSet(binding.registerRestaurantFragmentEtName,binding.registerRestaurantFragmentEtEmail,binding.registerRestaurantFragmentEtDeliveryTime,
                        binding.registerRestaurantFragmentEtPassword,binding.registerRestaurantFragmentEtPasswordConfirm,binding.registerRestaurantFragmentEtMinimumCharger,
                        binding.registerRestaurantFragmentEtDeliveryCost) && isEmailValid(binding.registerRestaurantFragmentEtEmail)
                        &&isPasswordMatched(binding.registerRestaurantFragmentEtPassword,binding.registerRestaurantFragmentEtPasswordConfirm)) {
                    if (binding.registerRestaurantFragmentSpCity.getSelectedItemPosition() > 0 && binding.registerRestaurantFragmentSpRegion.getSelectedItemPosition() > 0) {

                        Bundle bundle =new Bundle();
                        bundle.putString("name",binding.registerRestaurantFragmentEtName.getText().toString());
                        bundle.putString("email",binding.registerRestaurantFragmentEtEmail.getText().toString());
                        bundle.putString("deliveryTime",binding.registerRestaurantFragmentEtDeliveryTime.getText().toString());
                        bundle.putString("password",binding.registerRestaurantFragmentEtPassword.getText().toString());
                        bundle.putString("passwordConfirm",binding.registerRestaurantFragmentEtPasswordConfirm.getText().toString());
                        bundle.putString("minimumCharger",binding.registerRestaurantFragmentEtMinimumCharger.getText().toString());
                        bundle.putString("deliveryCost",binding.registerRestaurantFragmentEtDeliveryCost.getText().toString());
                        bundle.putString("regionId",String.valueOf(binding.registerRestaurantFragmentSpRegion.getSelectedItemPosition()));

                        RegisterRestaurant2Fragment registerRestaurant2Fragment=new RegisterRestaurant2Fragment();
                        registerRestaurant2Fragment.setArguments(bundle);

                        getFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, registerRestaurant2Fragment)
                                .addToBackStack(null).commit();
                    }else {
                        Toast.makeText(baseActivity, "Please Select Restaurant Location", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.registerRestaurantFragmentRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });

        //SaveData(getActivity(), "userType", "seller");

       return view;
    }




    @Override
    public void onBack() {
        super.onBack();
    }
}
