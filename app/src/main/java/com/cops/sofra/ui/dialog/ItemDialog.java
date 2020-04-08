package com.cops.sofra.ui.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategories;
import com.cops.sofra.data.model.updateCategory.UpdateCategory;
import com.cops.sofra.databinding.DialogItemBinding;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantCategoriesFragment;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.RestaurantNewCategoryViewModel;
import com.cops.sofra.viewModel.RestaurantUpdateCategoryViewModel;
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
import static com.cops.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.cops.sofra.utils.HelperMethod.convertToRequestBody;

public class ItemDialog extends DialogFragment {

    public ItemDialog() {
    }

    public ItemDialog(String itemName, String imageUrl,int categoryId ) {
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.mCategoryId = categoryId;
    }

    private String itemName;
    private String imageUrl;
    private int mCategoryId;
    private AlbumFile albumFile;
    private DialogItemBinding binding;
    private RestaurantNewCategoryViewModel newCategoryViewModel;
    private RestaurantUpdateCategoryViewModel updateCategoryViewModel;
    private String mApiToken;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_item,container,false);
        View view =binding.getRoot();

        if (LoadData(getActivity(),"apiToken")!=null) {


            mApiToken=LoadData(getActivity(),"apiToken");
            Log.i("api",mApiToken);
        }

        if(mCategoryId>0 && !itemName.equals("") && !imageUrl.equals("") ){
            binding.dialogAddItemTv.setText(getString(R.string.update_item));

            binding.dialogAddItemEtName.setText(itemName);
            binding.dialogAddItemBtn.setText(getString(R.string.update));
            binding.dialogAddItemCivAdd.setBackground(null);
            Glide.with(getActivity()).load(imageUrl).into(binding.dialogAddItemCivAdd);

            binding.dialogAddItemCivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });
            binding.dialogAddItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (binding.dialogAddItemEtName.getText().toString().equals("")) {

                        binding.dialogAddItemEtName.requestFocus();
                        binding.dialogAddItemEtName.setError(getString(R.string.item_name));

                    } else {

                        updateItem();
                        getDialog().dismiss();

                    }


                }
            });


        }else {

            binding.dialogAddItemCivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });

            binding.dialogAddItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (binding.dialogAddItemEtName.getText().toString().equals("")) {

                        binding.dialogAddItemEtName.requestFocus();
                        binding.dialogAddItemEtName.setError(getString(R.string.item_name));

                    } else {

                        if (albumFile != null) {

                            setNewItem();
                            getDialog().dismiss();



                        } else {

                            Toast.makeText(getActivity(), getString(R.string.select_image), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });
        }

        return view;

    }

    private void setNewItem() {
        newCategoryViewModel= ViewModelProviders.of(getActivity()).get(RestaurantNewCategoryViewModel.class);

        RequestBody name= convertToRequestBody(binding.dialogAddItemEtName.getText().toString());
        RequestBody apiToken= convertToRequestBody(mApiToken);
        MultipartBody.Part photo=convertFileToMultipart(albumFile.getPath(),"photo");

        newCategoryViewModel.addNewCategory(name,photo,apiToken);

        newCategoryViewModel.newCategoryMutableLiveData.observe(this, new Observer<RestaurantCategories>() {
            @Override
            public void onChanged(RestaurantCategories restaurantCategories) {
                if (restaurantCategories.getStatus()==1) {
                    Toast.makeText(getActivity(), restaurantCategories.getMsg(), Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(getActivity(), restaurantCategories.getMsg(), Toast.LENGTH_LONG).show();

            }
        });
    }
    private void updateItem(){
        updateCategoryViewModel=ViewModelProviders.of(getActivity()).get(RestaurantUpdateCategoryViewModel.class);

        RequestBody name= convertToRequestBody(binding.dialogAddItemEtName.getText().toString());
        RequestBody apiToken= convertToRequestBody(mApiToken);
        RequestBody categoryId= convertToRequestBody(String.valueOf(mCategoryId));

        if(albumFile!=null) {

            MultipartBody.Part photo = convertFileToMultipart(albumFile.getPath(), "photo");

            updateCategoryViewModel.updateCategory(name, photo, apiToken, categoryId);
        }else {
            updateCategoryViewModel.updateCategory(name, null, apiToken, categoryId);
        }

        updateCategoryViewModel.updateCategoryMutableLiveData.observe(this, new Observer<UpdateCategory>() {
            @Override
            public void onChanged(UpdateCategory updateCategory) {
                if (updateCategory.getStatus()==1) {
                    Toast.makeText(getActivity(), updateCategory.getMsg(), Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(getActivity(), updateCategory.getMsg(), Toast.LENGTH_LONG).show();

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
                        binding.dialogAddItemCivAdd.setBackground(null);
                        mediaLoader.load(binding.dialogAddItemCivAdd,albumFile.getPath());




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

}
