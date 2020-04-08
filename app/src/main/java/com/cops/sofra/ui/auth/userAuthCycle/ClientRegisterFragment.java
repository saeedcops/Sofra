package com.cops.sofra.ui.auth.userAuthCycle;

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
import com.cops.sofra.data.model.city.CityData;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.databinding.FragmentClientRegisterBinding;

import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.utils.MediaLoader;
import com.cops.sofra.viewModel.ClientSignUpViewModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.SaveData;
import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.CheckInput.isPasswordMatched;
import static com.cops.sofra.utils.CheckInput.isPhoneSet;
import static com.cops.sofra.utils.GeneralResponse.getCityAndRegion;
import static com.cops.sofra.utils.HelperMethod.convertFileToMultipart;
import static com.cops.sofra.utils.HelperMethod.convertToRequestBody;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class ClientRegisterFragment extends BaseFragment {


    private FragmentClientRegisterBinding binding;
    private AlbumFile albumFile;
    private ArrayList<CityData> cityList = new ArrayList<>();
    private ArrayList<CityData> regionList = new ArrayList<>();
    private ClientSignUpViewModel clientSignUpViewModel;
    public static boolean isRegistered =false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_client_register, container, false);
        final View view = binding.getRoot();
        setUpActivity();
        getCityAndRegion(getActivity(),cityList,binding.registerClientFragmentSpCity,regionList,binding.registerClientFragmentSpRegion);


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
                    if (binding.registerClientFragmentSpRegion.getSelectedItemPosition() <= 0) {
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

        binding.registerClientFragmentRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });

        return view;
    }

    private void selectImage() {
        final MediaLoader mediaLoader = new MediaLoader();
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
                                .title(getString(R.string.select_profile_image))
                                .build()
                )

                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        albumFile = result.get(0);

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
        RequestBody regionIds = convertToRequestBody(String.valueOf(binding.registerClientFragmentSpRegion.getSelectedItemPosition()));
        RequestBody password = convertToRequestBody(binding.registerClientFragmentEtPassword.getText().toString());
        RequestBody passwordConfirm = convertToRequestBody(binding.registerClientFragmentEtPasswordConfirm.getText().toString());
        RequestBody phone = convertToRequestBody(binding.registerClientFragmentEtPhone.getText().toString());
        MultipartBody.Part image = convertFileToMultipart(albumFile.getPath(), "profile_image");
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
                    SaveData(getActivity(), "clientId", restaurantLogin.getData().getUser().getId());
                    SaveData(getActivity(), "email", binding.registerClientFragmentEtEmail.getText().toString());
                    SaveData(getActivity(), "password", binding.registerClientFragmentEtPassword.getText().toString());
                    SaveData(getActivity(), "apiToken", restaurantLogin.getData().getApiToken());
                    Bundle bundle =new Bundle();
                    bundle.putString("userType","client");
                    UserLoginFragment userLoginFragment=new UserLoginFragment();
                    userLoginFragment.setArguments(bundle);
                    isRegistered =true;
                    getFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, userLoginFragment)
                            .addToBackStack(null).commit();


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
