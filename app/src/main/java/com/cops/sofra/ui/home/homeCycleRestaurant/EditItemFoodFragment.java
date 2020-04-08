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
import com.cops.sofra.data.model.restaurantEditItem.RestaurantEditItem;
import com.cops.sofra.databinding.FragmentEditItemFoodBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.RestaurantEditItemFoodViewModel;
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

public class EditItemFoodFragment extends BaseFragment {


    private RestaurantEditItemFoodViewModel restaurantEditItemFoodViewModel;
    private FragmentEditItemFoodBinding binding;
    private String imageUrl,name,description,priceOffer,mCategoryId,price,mApiToken;
    private int id;
    private AlbumFile albumFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        floatingActionButton.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_item_food,container,false);
        final View view = binding.getRoot();
        setUpActivity();

        if(LoadData(getActivity(),"apiToken")!=null){

            mApiToken=LoadData(getActivity(),"apiToken");
        }

        if (getArguments()!=null) {
            imageUrl=getArguments().getString("imageUrl");
            name=getArguments().getString("name");
            description=getArguments().getString("description");
            priceOffer=getArguments().getString("priceOffer");
            price=getArguments().getString("price");
            mCategoryId=getArguments().getString("categoryId");
            id=getArguments().getInt("id");

        }
        Glide.with(getActivity()).load(imageUrl).into(binding.editItemFoodFragmentIv);
        binding.editItemFoodFragmentEtName.setText(name);
        binding.editItemFoodFragmentEtDescription.setText(description);
        binding.editItemFoodFragmentEtPrice.setText(price);
        binding.editItemFoodFragmentEtPriceOffer.setText(priceOffer);

        binding.editItemFoodFragmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.editItemFoodFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editItemFoodFragmentEtName.getText().toString().equals("")) {
                    binding.editItemFoodFragmentEtName.setError(getString(R.string.item_name));
                    binding.editItemFoodFragmentEtName.requestFocus();
                }else if(binding.editItemFoodFragmentEtDescription.getText().toString().equals("")){
                    binding.editItemFoodFragmentEtDescription.setError(getString(R.string.product_description));
                    binding.editItemFoodFragmentEtDescription.requestFocus();
                }else if(binding.editItemFoodFragmentEtPrice.getText().toString().equals("")){
                    binding.editItemFoodFragmentEtPrice.setError(getString(R.string.price));
                    binding.editItemFoodFragmentEtPrice.requestFocus();
                }else {
                    editItem();
                }
            }
        });

        binding.editItemFoodFragmentParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });


       return view;
    }

    private void editItem(){

        restaurantEditItemFoodViewModel= ViewModelProviders.of(getActivity()).get(RestaurantEditItemFoodViewModel.class);

        RequestBody name= convertToRequestBody(binding.editItemFoodFragmentEtName.getText().toString());
        RequestBody description= convertToRequestBody(binding.editItemFoodFragmentEtDescription.getText().toString());
        RequestBody price= convertToRequestBody(binding.editItemFoodFragmentEtPrice.getText().toString());
        RequestBody apiToken= convertToRequestBody(mApiToken);
        RequestBody categoryId= convertToRequestBody(mCategoryId);
        RequestBody itemId= convertToRequestBody(String.valueOf(id));

        RequestBody time = convertToRequestBody(binding.editItemFoodFragmentEtTime.getText().toString());

        if (albumFile==null) {

            if (binding.editItemFoodFragmentEtPriceOffer.getText().toString().equals("")) {
                restaurantEditItemFoodViewModel.editItemFood(description, price, time, name, null, itemId, apiToken, null, categoryId);
            } else {
                RequestBody priceOffer = convertToRequestBody(binding.editItemFoodFragmentEtPriceOffer.getText().toString());
                restaurantEditItemFoodViewModel.editItemFood(description, price, time, name, null, itemId, apiToken, priceOffer, categoryId);
            }
        }else {
            MultipartBody.Part photo = convertFileToMultipart(albumFile.getPath(), "photo");
            if (binding.editItemFoodFragmentEtPriceOffer.getText().toString().equals("")) {
                restaurantEditItemFoodViewModel.editItemFood(description, price, time, name, photo, itemId, apiToken, null, categoryId);
            } else {
                RequestBody priceOffer = convertToRequestBody(binding.editItemFoodFragmentEtPriceOffer.getText().toString());
                restaurantEditItemFoodViewModel.editItemFood(description, price, time, name, photo, itemId, apiToken, priceOffer, categoryId);
            }
        }
        restaurantEditItemFoodViewModel.editItemMutableLiveData.observe(this, new Observer<RestaurantEditItem>() {
            @Override
            public void onChanged(RestaurantEditItem restaurantEditItem) {
                if (restaurantEditItem.getStatus()==1) {

                    Toast.makeText(getActivity(), restaurantEditItem.getMsg(), Toast.LENGTH_SHORT).show();
                    onBack();
                }else {

                    Toast.makeText(getActivity(), restaurantEditItem.getMsg(), Toast.LENGTH_SHORT).show();
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
                                .title(getString(R.string.select_image))
                                .build()
                )

                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        albumFile=result.get(0);
                        mediaLoader.load(binding.editItemFoodFragmentIv,albumFile.getPath());


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
        floatingActionButton.setVisibility(View.VISIBLE);
    }
}
