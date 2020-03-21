package com.cops.sofra.ui.auth.userAuthCycle;

import android.content.Intent;
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
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.databinding.FragmentClientRegisterBinding;
import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.CityViewModel;
import com.cops.sofra.viewModel.ClientSignUpViewModel;
import com.cops.sofra.viewModel.RegionViewModel;
import com.cops.sofra.viewModel.RestaurantSignUpViewModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cops.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.CheckInput.isPasswordMatched;
import static com.cops.sofra.utils.CheckInput.isPhoneSet;
import static com.cops.sofra.utils.MultipartConverter.convertFileToMultipart;
import static com.cops.sofra.utils.MultipartConverter.convertToRequestBody;

public class ClientRegisterFragment extends BaseFragment {


    private FragmentClientRegisterBinding binding;
    private AlbumFile albumFile;
    private ArrayList<CityData> cityList = new ArrayList<>();
    private ArrayList<CityData> regionList = new ArrayList<>();
    private int cityId;
    private int regionId;
    private ClientSignUpViewModel clientSignUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_client_register, container, false);
        View view = binding.getRoot();
        setUpActivity();
        getCity();
        binding.registerClientFragmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        binding.registerClientFragmentBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEditTextSet(binding.registerClientFragmentEtName, binding.registerClientFragmentEtEmail,
                        binding.registerClientFragmentEtPhone, binding.registerClientFragmentEtPassword, binding.registerClientFragmentEtPasswordConfirm)
                        && isEmailValid(binding.registerClientFragmentEtEmail) && isPhoneSet(binding.registerClientFragmentEtPhone)
                        && isPasswordMatched(binding.registerClientFragmentEtPassword, binding.registerClientFragmentEtPasswordConfirm)) {
                    if (regionId <= 0) {
                        Toast.makeText(baseActivity, getString(R.string.select_your_location), Toast.LENGTH_LONG).show();
                    } else {
                        if(albumFile==null){
                            Toast.makeText(baseActivity, getString(R.string.select_image), Toast.LENGTH_LONG).show();
                        }else {
                            signUp();
                        }
                    }
                }

            }
        });

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
                binding.registerClientFragmentSpCity.setAdapter(spinnerAdapter);
                binding.registerClientFragmentSpCity.setEnabled(true);


            }
        });
        binding.registerClientFragmentSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId = position;
                if (position != 0) {

                    getRegion(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getRegion(int position) {

        RegionViewModel regionViewModel = ViewModelProviders.of(getActivity()).get(RegionViewModel.class);
        final CitySpinnerAdapter spinnerAdapter = new CitySpinnerAdapter(getActivity());

        regionViewModel.getRegion(position);

        regionViewModel.regionMutableLiveData.observe(this, new Observer<City>() {
            @Override
            public void onChanged(City city) {
                regionList.clear();
                regionList = new ArrayList<>();
                regionList.addAll(city.getData());

                spinnerAdapter.setData(regionList, getString(R.string.choose_regoin));
                binding.registerClientFragmentSpRegion.setAdapter(spinnerAdapter);
                binding.registerClientFragmentSpRegion.setEnabled(true);

                binding.registerClientFragmentSpRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        regionId = position;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

    }


    private void selectImage() {

        Album.image(this)
                .singleChoice()
                .camera(true)
                .columnCount(2)
                .widget(
                        Widget.newDarkBuilder(getActivity())
                                .title(getString(R.string.select_profile_image))
                                .build()
                )

                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        albumFile = result.get(0);
                        MediaLoader mediaLoader = new MediaLoader();
                        mediaLoader.load(binding.registerClientFragmentIv, albumFile);


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

    private void signUp() {
        clientSignUpViewModel = ViewModelProviders.of(getActivity()).get(ClientSignUpViewModel.class);


        RequestBody name = convertToRequestBody(binding.registerClientFragmentEtName.getText().toString());
        RequestBody email = convertToRequestBody(binding.registerClientFragmentEtEmail.getText().toString());
        RequestBody regionIds = convertToRequestBody(String.valueOf(regionId));
        RequestBody password = convertToRequestBody(binding.registerClientFragmentEtPassword.getText().toString());
        RequestBody passwordConfirm = convertToRequestBody(binding.registerClientFragmentEtPasswordConfirm.getText().toString());
        RequestBody phone = convertToRequestBody(binding.registerClientFragmentEtPhone.getText().toString());
        MultipartBody.Part image = convertFileToMultipart(albumFile.getPath(), albumFile.getBucketName());
        Log.i("image", image.toString());

        clientSignUpViewModel.getClientSignUp(name, email, password, passwordConfirm, phone, regionIds, image);
        Log.i("methodCalled", "ok");
        clientSignUpViewModel.clientSignUpMutableLiveData.observe(this, new Observer<RestaurantLogin>() {
            @Override
            public void onChanged(RestaurantLogin restaurantLogin) {
                if (restaurantLogin.getStatus() == 1) {
                    Log.i("yes", restaurantLogin.getMsg());
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_LONG).show();
                    SaveData(getActivity(), "userType", "client");
                    SaveData(getActivity(), "email", binding.registerClientFragmentEtEmail.getText().toString());
                    SaveData(getActivity(), "password", binding.registerClientFragmentEtPassword.getText().toString());
                    SaveData(getActivity(), "apiToken", restaurantLogin.getData().getApiToken());
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);

                } else {
                    Log.i("no", restaurantLogin.getMsg());
                    Toast.makeText(baseActivity, restaurantLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
