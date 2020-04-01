package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
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
import com.cops.sofra.data.model.clientProfile.ClientProfile;
import com.cops.sofra.data.model.restaurantLogin.User;
import com.cops.sofra.data.model.restaurantProfile.RestaurantProfile;
import com.cops.sofra.databinding.FragmentEditClientProfileBinding;
import com.cops.sofra.databinding.FragmentRestaurantListBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.client.ClientEditProfileViewModel;
import com.cops.sofra.viewModel.client.ClientGetProfileViewModel;
import com.cops.sofra.viewModel.restaurant.RestaurantSetProfileViewModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.CheckInput.isPhoneSet;
import static com.cops.sofra.utils.GeneralResponse.getCityAndRegion;
import static com.cops.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.cops.sofra.utils.HelperMethod.convertToRequestBody;

public class EditClientProfileFragment extends BaseFragment {


   private FragmentEditClientProfileBinding binding;
   private ClientGetProfileViewModel clientGetProfileViewModel;
   private ClientEditProfileViewModel editProfileViewModel;
    private String apiToken;
    private ArrayList<CityData> cityData=new ArrayList<>();
    private ArrayList<CityData> regionList=new ArrayList<>();
    private AlbumFile albumFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_client_profile,container,false);
        View view = binding.getRoot();
        setUpActivity();
        if(LoadData(getActivity(), "apiToken")!=null){
            apiToken=LoadData(getActivity(), "apiToken");
        }
        getProfile();

        binding.editClientProfileFragmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.editClientProfileFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditTextSet(binding.editClientProfileFragmentName,binding.editClientProfileFragmentEmail,binding.editClientProfileFragmentEtPhone)
                        &&isEmailValid(binding.editClientProfileFragmentEmail) && isPhoneSet(binding.editClientProfileFragmentEtPhone)){

                    if(binding.editClientProfileFragmentSpRegion.getSelectedItemPosition()>0){
                        setProfile();
                    }else{
                        Toast.makeText(baseActivity, getString(R.string.select_your_location), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


       return view;
    }

    private void getProfile(){

        clientGetProfileViewModel= ViewModelProviders.of(getActivity()).get(ClientGetProfileViewModel.class);
        clientGetProfileViewModel.getClientProfile(apiToken);
        clientGetProfileViewModel.clientProfileMutableLiveData.observe(this, new Observer<ClientProfile>() {
            @Override
            public void onChanged(ClientProfile clientProfile) {
                if (clientProfile.getStatus()==1) {
                    setData(clientProfile.getData().getUser());
                }
            }
        });
    }

    private void setData(final User user) {

        getCityAndRegion(getActivity(),cityData,binding.editClientProfileFragmentSpCity,regionList,binding.editClientProfileFragmentSpRegion);
        Glide.with(getActivity()).load(user.getPhotoUrl()).into(binding.editClientProfileFragmentIv);
        binding.editClientProfileFragmentName.setText(user.getName());
        binding.editClientProfileFragmentEmail.setText(user.getEmail());
        binding.editClientProfileFragmentEtPhone.setText(user.getPhone());

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                binding.editClientProfileFragmentSpCity.setSelection(user.getRegion().getCity().getId());

            }
        },1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                binding.editClientProfileFragmentSpRegion.setSelection(user.getRegion().getId());

            }
        },2000);



    }

    private void setProfile(){

        editProfileViewModel= ViewModelProviders.of(getActivity()).get(ClientEditProfileViewModel.class);

        RequestBody name= convertToRequestBody(binding.editClientProfileFragmentName.getText().toString());
        RequestBody email= convertToRequestBody(binding.editClientProfileFragmentEmail.getText().toString());
        RequestBody regionId= convertToRequestBody(String.valueOf(binding.editClientProfileFragmentSpRegion.getSelectedItemPosition()));
        RequestBody api= convertToRequestBody(apiToken);
        RequestBody phone= convertToRequestBody(binding.editClientProfileFragmentEtPhone.getText().toString());


        if(albumFile==null){
            editProfileViewModel.editClientProfile(api,name,phone,email,regionId,null);
        }else{
            MultipartBody.Part photo=convertFileToMultipart(albumFile.getPath(),"profile_image");
            editProfileViewModel.editClientProfile(api,name,phone,email,regionId,photo);
        }


        editProfileViewModel.clientProfileMutableLiveData.observe(this, new Observer<ClientProfile>() {
            @Override
            public void onChanged(ClientProfile clientProfile) {

                Toast.makeText(baseActivity, clientProfile.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
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

                        mediaLoader.load(binding.editClientProfileFragmentIv,albumFile.getPath());




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

    @Override
    public void onBack() {
        super.onBack();
    }
}
