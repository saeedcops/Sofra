package com.cops.sofra.ui.home;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cops.sofra.R;
import com.cops.sofra.databinding.ActivityHomeBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.auth.AuthActivity;
import com.cops.sofra.ui.home.homeCycle.CartFragment;
import com.cops.sofra.ui.home.homeCycle.ClientMoreFragment;
import com.cops.sofra.ui.home.homeCycle.ClientOrderContainerFragment;
import com.cops.sofra.ui.home.homeCycle.EditClientProfileFragment;
import com.cops.sofra.ui.home.homeCycle.RestaurantListFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantCategoriesFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantCommissionFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantEditProfileFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantMoreFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantNotificationsFragment;
import com.cops.sofra.ui.home.homeCycleRestaurant.RestaurantOrderContainerFragment;
import com.cops.sofra.ui.splash.SplashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class HomeActivity extends BaseActivity {

    //to set onClickListener in fab from fragment implement interface and method

    public interface FabButtonClick {
        void onFabClicked();

    }

    public void setListener(FabButtonClick fab) {
        fabButtonClick = fab;
    }

    private FabButtonClick fabButtonClick;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        
        binding.homeActivityIvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.homeActivityFab.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame, new RestaurantNotificationsFragment()).commit();
            }
        });

        binding.homeActivityIvCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoadData(HomeActivity.this, "userType")!=null) {
                    if (LoadData(HomeActivity.this, "userType").equals("seller")) {
                        binding.homeActivityFab.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_activity_fl_frame, new RestaurantCommissionFragment()).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.home_activity_fl_frame, new CartFragment()).commit();

                    }
                }else{
                    Toast.makeText(HomeActivity.this, getString(R.string.please_login), Toast.LENGTH_LONG).show();
                }

            }
        });

        if (LoadData(this, "apiToken") != null) {

           Log.i("api",LoadData(this, "apiToken"));
            Log.i("user",LoadData(this, "email"));
            Log.i("password",LoadData(this, "password"));


        }


        binding.homeActivityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabButtonClick.onFabClicked();


            }
        });

        if (LoadData(this, "userType") != null) {


            if (LoadData(this, "userType").equals("seller")) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame, new RestaurantCategoriesFragment()).commit();
                binding.homeActivityNavView.setOnNavigationItemSelectedListener(navigationItemSelectedListenerSeller);
                binding.homeActivityFab.setVisibility(View.VISIBLE);
                binding.homeActivityIvCert.setImageResource(R.drawable.calc);
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame, new RestaurantListFragment()).commit();
                binding.homeActivityNavView.setOnNavigationItemSelectedListener(navigationItemSelectedListenerCient);
            }
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame, new RestaurantListFragment()).commit();
            binding.homeActivityNavView.setOnNavigationItemSelectedListener(navigationItemSelectedListenerCient);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListenerSeller =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selected = null;

                    switch (item.getItemId()) {

                        case R.id.home_item:
                            selected = new RestaurantCategoriesFragment();
                            binding.homeActivityFab.setVisibility(View.VISIBLE);
                            break;

                        case R.id.user_item:
                            selected=new RestaurantEditProfileFragment();
                            binding.homeActivityFab.setVisibility(View.GONE);

                            break;

                        case R.id.note_item:
                            selected=new RestaurantOrderContainerFragment();
                            binding.homeActivityFab.setVisibility(View.GONE);

                            break;

                        case R.id.more_item:

                            selected=new RestaurantMoreFragment();
                            binding.homeActivityFab.setVisibility(View.GONE);

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_activity_fl_frame, selected).commit();

                    return true;
                }
            };


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListenerCient =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selected = null;

                    switch (item.getItemId()) {

                        case R.id.home_item:
                            selected = new RestaurantListFragment();
                            break;

                        case R.id.user_item:

                            if (LoadData(HomeActivity.this, "userType") == null){
                                selected=new EditClientProfileFragment();
                                Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
                                intent.putExtra("userType","client");
                                startActivity(intent);

                            }else{
                                selected=new EditClientProfileFragment();
                            }


                            break;

                        case R.id.note_item:
                            selected=new ClientOrderContainerFragment();

                            break;

                        case R.id.more_item:

                            selected=new ClientMoreFragment();

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_activity_fl_frame, selected).commit();

                    return true;
                }
            };
}
