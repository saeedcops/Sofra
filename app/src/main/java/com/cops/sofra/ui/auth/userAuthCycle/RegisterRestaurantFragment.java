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
import com.cops.sofra.viewModel.CityViewModel;
import com.cops.sofra.viewModel.RegionViewModel;

import java.util.ArrayList;

import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.CheckInput.isPasswordMatched;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class RegisterRestaurantFragment extends BaseFragment {


    private FragmentRegisterRestaurantBinding binding;
    private ArrayList<CityData> cityList=new ArrayList<>();
    private ArrayList<CityData> regionList=new ArrayList<>();
    private int cityId=0;
    private int regionId=0;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_register_restaurant,container,false);
        final View view = binding.getRoot();
        setUpActivity();
        getCity();




        binding.registerRestaurantFragmentBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEditTextSet(binding.registerRestaurantFragmentEtName,binding.registerRestaurantFragmentEtEmail,binding.registerRestaurantFragmentEtDeliveryTime,
                        binding.registerRestaurantFragmentEtPassword,binding.registerRestaurantFragmentEtPasswordConfirm,binding.registerRestaurantFragmentEtMinimumCharger,
                        binding.registerRestaurantFragmentEtDeliveryCost) && isEmailValid(binding.registerRestaurantFragmentEtEmail)
                        &&isPasswordMatched(binding.registerRestaurantFragmentEtPassword,binding.registerRestaurantFragmentEtPasswordConfirm)) {
                    if (cityId > 0 && regionId > 0) {

                        Bundle bundle =new Bundle();
                        bundle.putString("name",binding.registerRestaurantFragmentEtName.getText().toString());
                        bundle.putString("email",binding.registerRestaurantFragmentEtEmail.getText().toString());
                        bundle.putString("deliveryTime",binding.registerRestaurantFragmentEtDeliveryTime.getText().toString());
                        bundle.putString("password",binding.registerRestaurantFragmentEtPassword.getText().toString());
                        bundle.putString("passwordConfirm",binding.registerRestaurantFragmentEtPasswordConfirm.getText().toString());
                        bundle.putString("minimumCharger",binding.registerRestaurantFragmentEtMinimumCharger.getText().toString());
                        bundle.putString("deliveryCost",binding.registerRestaurantFragmentEtDeliveryCost.getText().toString());
                        bundle.putString("regionId",String.valueOf(regionId));

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

    private void getCity() {
        CityViewModel cityViewModel = ViewModelProviders.of(getActivity()).get(CityViewModel.class);
        final CitySpinnerAdapter spinnerAdapter = new CitySpinnerAdapter(getActivity());
        if (cityList.size() == 0) {
            cityViewModel.getCity();
        }

        cityViewModel.cityMutableLiveData.observe(this, new Observer<City>() {
            @Override
            public void onChanged(City cityData) {
                cityList = new ArrayList<>();
                cityList.addAll(cityData.getData());

                spinnerAdapter.setData(cityList, getString(R.string.choose_city));
                binding.registerRestaurantFragmentSpCity.setAdapter(spinnerAdapter);
                binding.registerRestaurantFragmentSpCity.setEnabled(true);


            }
        });
        binding.registerRestaurantFragmentSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId=position;
                if (position != 0) {

                    getRegion(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void getRegion(int position){

        RegionViewModel regionViewModel=ViewModelProviders.of(getActivity()).get(RegionViewModel.class);
        final CitySpinnerAdapter spinnerAdapter = new CitySpinnerAdapter(getActivity());

        regionViewModel.getRegion(position);

        regionViewModel.regionMutableLiveData.observe(RegisterRestaurantFragment.this, new Observer<City>() {
            @Override
            public void onChanged(City city) {
                regionList.clear();
                regionList = new ArrayList<>();
                regionList.addAll(city.getData());

                spinnerAdapter.setData(regionList, getString(R.string.choose_regoin));
                binding.registerRestaurantFragmentSpRegion.setAdapter(spinnerAdapter);
                binding.registerRestaurantFragmentSpRegion.setEnabled(true);

                binding.registerRestaurantFragmentSpRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            regionId=position;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
