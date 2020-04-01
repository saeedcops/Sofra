package com.cops.sofra.utils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.adapters.CitySpinnerAdapter;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.city.CityData;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.auth.userAuthCycle.RegisterRestaurantFragment;
import com.cops.sofra.viewModel.CityViewModel;
import com.cops.sofra.viewModel.RegionViewModel;

import java.util.ArrayList;

import static com.google.gson.reflect.TypeToken.get;

public class GeneralResponse {

    public static void getCity(final Activity activity, final ArrayList<CityData> cityList, final Spinner citySpinner) {
        CityViewModel cityViewModel = ViewModelProviders.of((BaseActivity)activity).get(CityViewModel.class);
        final CitySpinnerAdapter spinnerAdapter = new CitySpinnerAdapter(activity);
        if (cityList.size() == 0) {
            cityViewModel.getCity();
        }

        cityViewModel.cityMutableLiveData.observe((BaseActivity)activity, new Observer<City>() {
            @Override
            public void onChanged(City cityData) {
                cityList.clear();
                cityList.addAll(cityData.getData());

                spinnerAdapter.setData(cityList, activity.getString(R.string.choose_city));
                citySpinner.setAdapter(spinnerAdapter);
                citySpinner.setEnabled(true);


            }
        });


    }

    public static void getCityAndRegion(final Activity activity, final ArrayList<CityData> cityList, final Spinner citySpinner
            , final ArrayList<CityData> regionList, final Spinner regionSpinner) {
        CityViewModel cityViewModel = ViewModelProviders.of((BaseActivity)activity).get(CityViewModel.class);
        final CitySpinnerAdapter spinnerAdapter = new CitySpinnerAdapter(activity);
        if (cityList.size() == 0) {
            cityViewModel.getCity();
        }

        cityViewModel.cityMutableLiveData.observe((BaseActivity)activity, new Observer<City>() {
            @Override
            public void onChanged(City cityData) {
                cityList.clear();
//                cityList = new ArrayList<>();
                cityList.addAll(cityData.getData());

                spinnerAdapter.setData(cityList, activity.getString(R.string.choose_city));
                citySpinner.setAdapter(spinnerAdapter);
                citySpinner.setEnabled(true);


            }
        });
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // cityId=position;
                if (position != 0) {

                    getRegion(position,activity,regionList,regionSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private static void getRegion(int position,final Activity activity, final ArrayList<CityData> regionList, final Spinner RegionSpinner){

        RegionViewModel regionViewModel=ViewModelProviders.of((BaseActivity)activity).get(RegionViewModel.class);
        final CitySpinnerAdapter spinnerAdapter = new CitySpinnerAdapter(activity);

        regionViewModel.getRegion(position);

        regionViewModel.regionMutableLiveData.observe((BaseActivity)activity, new Observer<City>() {
            @Override
            public void onChanged(City city) {
                regionList.clear();
               // regionList = new ArrayList<>();
                regionList.addAll(city.getData());

                spinnerAdapter.setData(regionList, activity.getString(R.string.choose_regoin));
                RegionSpinner.setAdapter(spinnerAdapter);
                RegionSpinner.setEnabled(true);

                RegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //regionId=position;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

    }
}
