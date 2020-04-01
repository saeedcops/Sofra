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

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.databinding.FragmentEditRestaurantOfferBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.DateTxt;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.restaurant.RestaurantEditOfferViewModel;
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
import static com.cops.sofra.utils.HelperMethod.showCalender;

public class EditRestaurantOfferFragment extends BaseFragment {

    public EditRestaurantOfferFragment(String name, String desc, String price, int offerId, String imageUrl, String from, String to) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.offerId = offerId;
        this.imageUrl = imageUrl;
        this.from = from;
        this.to = to;
    }
    private FragmentEditRestaurantOfferBinding binding;
   private DateTxt dateTxt=new DateTxt("25","3","2020","25/3/2020");
   private AlbumFile albumFile;
   private RestaurantEditOfferViewModel editOfferViewModel;
   private String mApiToken;
   private String name,desc,price,imageUrl,from,to;
   private int offerId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_restaurant_offer,container,false);
        View view = binding.getRoot();
        setUpActivity();
       // mApiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            mApiToken=LoadData(getActivity(),"apiToken");


        }
        setValues();
        binding.editRestaurantOfferIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.editRestaurantOfferTvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(getActivity(),getString(R.string.from),binding.editRestaurantOfferTilFrom,dateTxt);
            }
        });
        binding.editRestaurantOfferTvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(getActivity(),getString(R.string.to),binding.editRestaurantOfferTilTo,dateTxt);
            }
        });

        binding.editRestaurantOfferFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditTextSet(binding.editRestaurantOfferEtName,binding.editRestaurantOfferEtDescription,binding.editRestaurantOfferEtPrice)){

                    if (isEditTextSet(binding.editRestaurantOfferTilFrom, binding.editRestaurantOfferTilTo)) {
                        newOffer();

                    }
                }
            }
        });


       return view;
    }

    private void setValues() {

        Glide.with(getActivity()).load(imageUrl).into(binding.editRestaurantOfferIv);
        binding.editRestaurantOfferEtName.setText(name);
        binding.editRestaurantOfferEtDescription.setText(desc);
        binding.editRestaurantOfferTilFrom.getEditText().setText(from);
        binding.editRestaurantOfferTilTo.getEditText().setText(to);
        binding.editRestaurantOfferEtPrice.setText(price);

    }

    private void newOffer(){
        editOfferViewModel = ViewModelProviders.of(getActivity()).get(RestaurantEditOfferViewModel.class);
        RequestBody name=convertToRequestBody(binding.editRestaurantOfferEtName.getText().toString());
        RequestBody desc=convertToRequestBody(binding.editRestaurantOfferEtDescription.getText().toString());
        RequestBody from=convertToRequestBody(binding.editRestaurantOfferTilFrom.getEditText().getText().toString());
        RequestBody to=convertToRequestBody(binding.editRestaurantOfferTilTo.getEditText().getText().toString());
        RequestBody price=convertToRequestBody(binding.editRestaurantOfferEtPrice.getText().toString());
        RequestBody offerID=convertToRequestBody(String.valueOf(offerId));
        RequestBody api=convertToRequestBody(mApiToken);
        if(albumFile==null){
            editOfferViewModel.updateOffer(desc,price,from,name,null,to,api,offerID);
        }else{

            MultipartBody.Part photo= convertFileToMultipart(albumFile.getPath(),"photo");

            editOfferViewModel.updateOffer(desc,price,from,name,photo,to,api,offerID);
        }

      editOfferViewModel.restaurantsUpdateOfferMutableLiveData.observe(this, new Observer<NewOffer>() {
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
                        mediaLoader.load(binding.editRestaurantOfferIv,albumFile.getPath());




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
