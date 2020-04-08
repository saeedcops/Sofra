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
import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.databinding.FragmentCreateRestaurantOfferBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.DateTxt;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.restaurant.RestaurantNewOfferViewModel;
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
import static com.cops.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.cops.sofra.utils.HelperMethod.convertToRequestBody;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;
import static com.cops.sofra.utils.HelperMethod.showCalender;

public class CreateRestaurantOfferFragment extends BaseFragment {


   private FragmentCreateRestaurantOfferBinding binding;
   private DateTxt dateTxt=new DateTxt("25","3","2020","25/3/2020");
    private AlbumFile albumFile;
    private RestaurantNewOfferViewModel newOfferViewModel;
    private String mApiToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_create_restaurant_offer,container,false);
        final View view = binding.getRoot();
        setUpActivity();
       // mApiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            mApiToken=LoadData(getActivity(),"apiToken");


        }
        binding.createRestaurantOfferIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.createRestaurantOfferTvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(getActivity(),getString(R.string.from),binding.createRestaurantOfferTilFrom,dateTxt);
            }
        });
        binding.createRestaurantOfferTvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(getActivity(),getString(R.string.to),binding.createRestaurantOfferTilTo,dateTxt);
            }
        });

        binding.createRestaurantOfferFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditTextSet(binding.createRestaurantOfferEtName,binding.createRestaurantOfferEtDescription,
                        binding.createRestaurantOfferEtPrice,binding.createRestaurantOfferEtPrice)){

                    if (isEditTextSet(binding.createRestaurantOfferTilFrom, binding.createRestaurantOfferTilTo)) {

                        if(albumFile==null){
                            Toast.makeText(baseActivity, getString(R.string.select_image), Toast.LENGTH_SHORT).show();
                        }else{
                            newOffer();
                        }

                    }
                }
            }
        });

        binding.createRestaurantOfferParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });


       return view;
    }

    private void newOffer(){
        newOfferViewModel= ViewModelProviders.of(getActivity()).get(RestaurantNewOfferViewModel.class);
        RequestBody name=convertToRequestBody(binding.createRestaurantOfferEtName.getText().toString());
        RequestBody desc=convertToRequestBody(binding.createRestaurantOfferEtDescription.getText().toString());
        RequestBody from=convertToRequestBody(binding.createRestaurantOfferTilFrom.getEditText().getText().toString());
        RequestBody to=convertToRequestBody(binding.createRestaurantOfferTilTo.getEditText().getText().toString());
        RequestBody price=convertToRequestBody(binding.createRestaurantOfferEtPrice.getText().toString());
        RequestBody offerPrice=convertToRequestBody(binding.createRestaurantOfferEtOfferPrice.getText().toString());
        RequestBody api=convertToRequestBody(mApiToken);
        MultipartBody.Part photo= convertFileToMultipart(albumFile.getPath(),"photo");

      newOfferViewModel.newOffer(desc,price,from,name,photo,to,api,offerPrice);
      newOfferViewModel.restaurantsNewOfferMutableLiveData.observe(this, new Observer<NewOffer>() {
          @Override
          public void onChanged(NewOffer newOffer) {

              if (newOffer.getStatus()==1) {
                  Toast.makeText(baseActivity, newOffer.getMsg(), Toast.LENGTH_SHORT).show();
                  onBack();
              }else {
                  Toast.makeText(baseActivity, newOffer.getMsg(), Toast.LENGTH_SHORT).show();
              }
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
                        mediaLoader.load(binding.createRestaurantOfferIv,albumFile.getPath());




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
