package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.city.CityData;

import com.cops.sofra.data.model.restaurantLogin.User;
import com.cops.sofra.data.model.restaurantProfile.RestaurantProfile;

import com.cops.sofra.databinding.FragmentRestaurantEditProfileBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.restaurant.RestaurantGetProfileViewModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Locale;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.GeneralResponse.getCityAndRegion;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class RestaurantEditProfileFragment extends BaseFragment {


   private FragmentRestaurantEditProfileBinding binding;
   private RestaurantGetProfileViewModel restaurantGetProfileViewModel;
   private ArrayList<CityData> cityData=new ArrayList<>();
   private ArrayList<CityData> regionData=new ArrayList<>();
   private String apiToken;
   private String deliveryTime,deliveryCost,availability,phone,whatsapp;
   private AlbumFile albumFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to use getString() method in CheckInput static methods
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_restaurant_edit_profile,container,false);
        final View view = binding.getRoot();
        setUpActivity();


        binding.restaurantEditProfileFragmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        binding.restaurantEditProfileFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEditTextSet(binding.restaurantEditProfileFragmentName,binding.restaurantEditProfileFragmentEmail,binding.restaurantEditProfileFragmentEtMinimumCharger)
                  && isEmailValid(binding.restaurantEditProfileFragmentEmail)){

                    if(binding.restaurantEditProfileFragmentSpCity.getSelectedItemPosition()>0
                     && binding.restaurantEditProfileFragmentSpRegion.getSelectedItemPosition()>0){

                        Bundle bundle =new Bundle();

                        if(albumFile!=null){
                            bundle.putString("albumFile",albumFile.getPath());
                        }

                        bundle.putString("deliveryTime",deliveryTime);
                        bundle.putString("deliveryCost",deliveryCost);
                        bundle.putString("availability",availability);
                        bundle.putString("phone",phone);
                        bundle.putString("whatsapp",whatsapp);
                        bundle.putString("name",binding.restaurantEditProfileFragmentName.getText().toString());
                        bundle.putString("email",binding.restaurantEditProfileFragmentEmail.getText().toString());
                        bundle.putString("minimumCharger",binding.restaurantEditProfileFragmentEtMinimumCharger.getText().toString());
                        bundle.putInt("regionId",binding.restaurantEditProfileFragmentSpRegion.getSelectedItemPosition());
                        bundle.putInt("cityId",binding.restaurantEditProfileFragmentSpCity.getSelectedItemPosition());

                        RestaurantEditProfile2Fragment restaurantEditProfile2Fragment=new RestaurantEditProfile2Fragment();
                        restaurantEditProfile2Fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame,restaurantEditProfile2Fragment)
                                .addToBackStack(null).commit();

                    }else {

                        Toast.makeText(baseActivity, getString(R.string.select_your_location), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        if (LoadData(getActivity(),"apiToken")!=null) {

            apiToken=LoadData(getActivity(),"apiToken");

            getProfile();
        }
        binding.restaurantEditProfileFragmentLlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);

            }
        });


       return view;
    }

    private void selectImage() {
        final MediaLoader mediaLoader=new MediaLoader();
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(mediaLoader)
                .setLocale(Locale.getDefault())
                .build());


        Album.image(getActivity())
                .singleChoice()
                .camera(true)
                .columnCount(2)
                .widget(
                        Widget.newDarkBuilder(getActivity())
                                .title(getString(R.string.select_restaurant_image))
                                .build()
                )

                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        albumFile=result.get(0);

                        mediaLoader.load(binding.restaurantEditProfileFragmentIv,albumFile.getPath());




                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(getContext(), getString(R.string.canceled), Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }

    private void getProfile(){
        restaurantGetProfileViewModel= ViewModelProviders.of(getActivity()).get(RestaurantGetProfileViewModel.class);
        restaurantGetProfileViewModel.getRestaurantProfile(apiToken);
        restaurantGetProfileViewModel.restaurantGetProfileMutableLiveData.observe(this, new Observer<RestaurantProfile>() {
            @Override
            public void onChanged(RestaurantProfile restaurantLogin) {

                if (restaurantLogin.getStatus()==1) {

                   setData(restaurantLogin.getData().getUser());

                }else {
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setData(final User user) {

        deliveryTime=user.getDeliveryTime();
        deliveryCost=user.getDeliveryCost();
        availability=user.getAvailability();
        phone=user.getPhone();
        whatsapp=user.getWhatsapp();
        Glide.with(getActivity()).load(user.getPhotoUrl()).into(binding.restaurantEditProfileFragmentIv);
        binding.restaurantEditProfileFragmentEmail.setText(user.getEmail());
        binding.restaurantEditProfileFragmentName.setText(user.getName());
        binding.restaurantEditProfileFragmentEtMinimumCharger.setText(user.getMinimumCharger());

        getCityAndRegion(getActivity(),cityData,binding.restaurantEditProfileFragmentSpCity,regionData,binding.restaurantEditProfileFragmentSpRegion);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                binding.restaurantEditProfileFragmentSpCity.setSelection(user.getRegion().getCity().getId());

            }
        },1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                binding.restaurantEditProfileFragmentSpRegion.setSelection(user.getRegion().getId());

            }
        },2000);


    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
