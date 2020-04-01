package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.content.Intent;
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
import com.cops.sofra.data.model.restaurantProfile.RestaurantProfile;
import com.cops.sofra.databinding.FragmentRestaurantEditProfile2Binding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.restaurant.RestaurantSetProfileViewModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isPhoneSet;
import static com.cops.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.cops.sofra.utils.HelperMethod.convertToRequestBody;

public class RestaurantEditProfile2Fragment extends BaseFragment {


    private FragmentRestaurantEditProfile2Binding binding;
    private RestaurantSetProfileViewModel restaurantSetProfileViewModel;
    private String mName, mEmail, mMinimumCharger,imagePath,apiToken;
    private int mRegionId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to use getString() method in CheckInput static methods
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_edit_profile_2,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (LoadData(getActivity(),"apiToken")!=null) {
            apiToken=LoadData(getActivity(),"apiToken");
        }

        if (getArguments()!=null) {

            binding.restaurantEditProfile2FragmentEtDeliveryCost.setText(getArguments().getString("deliveryCost"));
            binding.restaurantEditProfile2FragmentEtDeliveryTime.setText(getArguments().getString("deliveryTime"));
            if (getArguments().getString("availability").equals("open")) {
                binding.restaurantEditProfile2FragmentSw.setChecked(true);
            }
            binding.restaurantEditProfile2FragmentEtPhone.setText(getArguments().getString("phone"));
            binding.restaurantEditProfile2FragmentEtWhatsapp.setText(getArguments().getString("whatsapp"));
            mName =getArguments().getString("name");
            mEmail =getArguments().getString("email");
            mMinimumCharger =getArguments().getString("minimumCharger");
            mRegionId=getArguments().getInt("regionId");

            if(getArguments().getString("albumFile")!=null){
                imagePath=getArguments().getString("albumFile");
            }

        }

        binding.restaurantEditProfileFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isEditTextSet(binding.restaurantEditProfile2FragmentEtDeliveryCost,binding.restaurantEditProfile2FragmentEtDeliveryTime
                )&& isPhoneSet(binding.restaurantEditProfile2FragmentEtPhone)&& isPhoneSet(binding.restaurantEditProfile2FragmentEtWhatsapp)){

                   setProfile();
               }


            }
        });


       return view;
    }

    private void setProfile(){

        restaurantSetProfileViewModel= ViewModelProviders.of(getActivity()).get(RestaurantSetProfileViewModel.class);
        RequestBody availability;
        if(binding.restaurantEditProfile2FragmentSw.isChecked()) {
            availability = convertToRequestBody("open");
        }else {
            availability = convertToRequestBody("closed");
        }
        RequestBody name= convertToRequestBody(mName);
        RequestBody email= convertToRequestBody(mEmail);
        RequestBody deliveryTime= convertToRequestBody(binding.restaurantEditProfile2FragmentEtDeliveryTime.getText().toString());
        RequestBody regionId= convertToRequestBody(String.valueOf(mRegionId));

        RequestBody deliveryCost= convertToRequestBody( binding.restaurantEditProfile2FragmentEtDeliveryCost.getText().toString());
        RequestBody minimumCharge= convertToRequestBody(mMinimumCharger);
        RequestBody api= convertToRequestBody(apiToken);
        RequestBody phone= convertToRequestBody(binding.restaurantEditProfile2FragmentEtPhone.getText().toString());
        RequestBody whatsApp= convertToRequestBody(binding.restaurantEditProfile2FragmentEtWhatsapp.getText().toString());

        if(imagePath==null){
            restaurantSetProfileViewModel.setRestaurantProfile(email,name,phone,regionId,deliveryCost,minimumCharge,availability,
                    null,api,deliveryTime,whatsApp);
        }
        MultipartBody.Part photo=convertFileToMultipart(imagePath,"photo");
        restaurantSetProfileViewModel.setRestaurantProfile(email,name,phone,regionId,deliveryCost,minimumCharge,availability,
                photo,api,deliveryTime,whatsApp);

        restaurantSetProfileViewModel.restaurantSetProfileMutableLiveData.observe(this, new Observer<RestaurantProfile>() {
            @Override
            public void onChanged(RestaurantProfile restaurantProfile) {
                if(restaurantProfile.getStatus()==1){

                    Toast.makeText(baseActivity, restaurantProfile.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }else {
                    Toast.makeText(baseActivity, restaurantProfile.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
