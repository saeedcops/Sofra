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
import com.cops.sofra.databinding.FragmentRegisterRestaurant2Binding;
import com.cops.sofra.databinding.FragmentRegisterRestaurantBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.RestaurantSignUpViewModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cops.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.cops.sofra.utils.CheckInput.isPhoneSet;
import static com.cops.sofra.utils.MultipartConverter.convertFileToMultipart;
import static com.cops.sofra.utils.MultipartConverter.convertToRequestBody;

public class RegisterRestaurant2Fragment extends BaseFragment {


    private FragmentRegisterRestaurant2Binding binding;

    private String imagePath;
    private AlbumFile albumFile;
    private RestaurantSignUpViewModel signUpViewModel;
    private String mName,mEmail,mDeliveryTime,mPassword,mPasswordConfirm,mMinimumCharger,mDeliveryCost,mRegionId;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_register_restaurant_2,container,false);
        final View view = binding.getRoot();
        setUpActivity();

        if (getArguments()!=null) {
            mName=getArguments().getString("name");
            mEmail=getArguments().getString("email");
            mDeliveryTime=getArguments().getString("deliveryTime");
            mPassword=getArguments().getString("password");
            mPasswordConfirm=getArguments().getString("passwordConfirm");
            mMinimumCharger=getArguments().getString("minimumCharger");
            mDeliveryCost=getArguments().getString("deliveryCost");
            mRegionId=getArguments().getString("regionId");
        }

            binding.registerRestaurant2FragmentIbPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(baseActivity,"hello" +RegisterRestaurantFragment.binding.registerRestaurantFragmentEtName.getText(), Toast.LENGTH_LONG).show();
                 selectImage();
                }
            });

            binding.registerRestaurant2FragmentBtnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isPhoneSet(binding.registerRestaurant2FragmentEtPhone) && isPhoneSet(binding.registerRestaurant2FragmentEtWhats)){
                        if(albumFile!=null) {
                            signUp();
                           // Toast.makeText(baseActivity, "good", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(baseActivity, "Please Pick Restaurant Logo", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });



       return view;
    }

    private void signUp() {
        signUpViewModel= ViewModelProviders.of(getActivity()).get(RestaurantSignUpViewModel.class);

        RequestBody name= convertToRequestBody(mName);
        RequestBody email= convertToRequestBody(mEmail);
        RequestBody deliveryTime= convertToRequestBody(mDeliveryTime);
        RequestBody regionId= convertToRequestBody(mRegionId);
        RequestBody password= convertToRequestBody(mPassword);
        RequestBody passwordConfirm= convertToRequestBody(mPasswordConfirm);
        RequestBody deliveryCost= convertToRequestBody(mDeliveryCost);
        RequestBody minimumCharge= convertToRequestBody(mMinimumCharger);

        RequestBody phone= convertToRequestBody(binding.registerRestaurant2FragmentEtPhone.getText().toString());
        RequestBody whatsApp= convertToRequestBody(binding.registerRestaurant2FragmentEtWhats.getText().toString());
        MultipartBody.Part image=convertFileToMultipart(imagePath,albumFile.getBucketName());
        Log.i("image",image.toString());

        signUpViewModel.getRestaurant(name,email,password,passwordConfirm,phone,whatsApp,regionId,deliveryCost,minimumCharge,image,deliveryTime);
        Log.i("methodCalled","ok");
        signUpViewModel.restaurantSignUpMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
            @Override
            public void onChanged(RestaurantLogin restaurantLogin) {
                if (restaurantLogin.getStatus()==1) {
                    Log.i("yes",restaurantLogin.getMsg());
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_LONG).show();
                    SaveData(getActivity(), "userType", "seller");
                    SaveData(getActivity(), "email", mEmail);
                    SaveData(getActivity(), "password", mPassword);
                    SaveData(getActivity(), "apiToken", restaurantLogin.getData().getApiToken());
                    Intent intent=new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);

                }else {
                    Log.i("no",restaurantLogin.getMsg());
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImage() {

//        Album.initialize(AlbumConfig.newBuilder(getActivity())
//                .setAlbumLoader(new MediaLoader())
//                .setLocale(Locale.getDefault())
//                .build());


        Album.image(this)
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
                        MediaLoader mediaLoader=new MediaLoader();
                        mediaLoader.load(binding.registerRestaurant2FragmentIbPhoto,albumFile);
                        imagePath=albumFile.getPath();
                       // previewImage();


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
