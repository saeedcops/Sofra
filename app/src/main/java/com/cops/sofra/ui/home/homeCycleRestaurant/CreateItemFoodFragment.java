package com.cops.sofra.ui.home.homeCycleRestaurant;

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
import com.cops.sofra.data.model.restaurantNewItem.RestaurantNewItem;
import com.cops.sofra.databinding.FragmentCreateItemFoodBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.RestaurantNewItemViewModel;
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
import static com.cops.sofra.ui.home.HomeActivity.floatingActionButton;
import static com.cops.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.cops.sofra.utils.HelperMethod.convertToRequestBody;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class CreateItemFoodFragment extends BaseFragment {


   private FragmentCreateItemFoodBinding binding;
   private RestaurantNewItemViewModel restaurantNewItemViewModel;
   private AlbumFile albumFile;
    private String mApiToken;
    private int mCategoryId;
    Action<ArrayList<AlbumFile>> action;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        floatingActionButton.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_item_food,container,false);
        final View view = binding.getRoot();
        setUpActivity();


        if (LoadData(getActivity(),"apiToken")!=null) {

            mApiToken=LoadData(getActivity(),"apiToken");

        }
        if (getArguments()!=null) {
            mCategoryId=getArguments().getInt("categoryId");
            Log.i("cat",String.valueOf(mCategoryId));
        }

        binding.createItemFoodFragmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            selectImage();

            }
        });


        binding.createItemFoodFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.createItemFoodFragmentEtName.getText().toString().equals("")){
                    binding.createItemFoodFragmentEtName.setError(getString(R.string.item_name));
                    binding.createItemFoodFragmentEtName.requestFocus();
                }else if(binding.createItemFoodFragmentEtPrice.getText().toString().equals("")){
                    binding.createItemFoodFragmentEtPrice.setError(getString(R.string.item_name));
                    binding.createItemFoodFragmentEtPrice.requestFocus();
                }else if(binding.createItemFoodFragmentEtTime.getText().toString().equals("")) {
                    binding.createItemFoodFragmentEtTime.setError(getString(R.string.preparing_time));
                    binding.createItemFoodFragmentEtTime.requestFocus();

                }else {
                    addItem();
                }

            }
        });

    binding.createItemFoodFragmentParent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            disappearKeypad(getActivity(),view);
        }
    });

       return view;
    }

    private void addItem() {
        restaurantNewItemViewModel= ViewModelProviders.of(getActivity()).get(RestaurantNewItemViewModel.class);

        RequestBody name= convertToRequestBody(binding.createItemFoodFragmentEtName.getText().toString());
        RequestBody description= convertToRequestBody(binding.createItemFoodFragmentEtDescription.getText().toString());
        RequestBody price= convertToRequestBody(binding.createItemFoodFragmentEtPrice.getText().toString());

        RequestBody apiToken= convertToRequestBody(mApiToken);
        RequestBody categoryId= convertToRequestBody(String.valueOf(mCategoryId));
        MultipartBody.Part photo = convertFileToMultipart(albumFile.getPath(), "photo");

        RequestBody time = convertToRequestBody(binding.createItemFoodFragmentEtTime.getText().toString());

        if(binding.createItemFoodFragmentEtPriceOffer.getText().toString().equals("")){
            restaurantNewItemViewModel.addNewItem(description,price,time,name,photo,apiToken,null,categoryId);
        }else {
            RequestBody priceOffer = convertToRequestBody(binding.createItemFoodFragmentEtPriceOffer.getText().toString());
            restaurantNewItemViewModel.addNewItem(description,price,time,name,photo,apiToken,priceOffer,categoryId);
        }


        restaurantNewItemViewModel.restaurantNewItemListMutableLiveData.observe(this, new Observer<RestaurantNewItem>() {
            @Override
            public void onChanged(RestaurantNewItem restaurantNewItem) {
                if (restaurantNewItem.getStatus()==1) {

                    Toast.makeText(baseActivity, restaurantNewItem.getMsg(), Toast.LENGTH_SHORT).show();
                    onBack();
                }else {
                    Toast.makeText(baseActivity, restaurantNewItem.getMsg(), Toast.LENGTH_SHORT).show();
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
                        mediaLoader.load(binding.createItemFoodFragmentIv,albumFile.getPath());




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
        floatingActionButton.setVisibility(View.VISIBLE);
        super.onBack();
    }
}
