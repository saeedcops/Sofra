package com.cops.sofra.ui.home;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.cops.sofra.R;
import com.cops.sofra.databinding.ActivityHomeBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.home.homeCycle.HomeFragment;
import com.cops.sofra.ui.auth.userAuthCycle.UserLoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.cops.sofra.data.local.SharedPreferencesManger.LoadData;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);

       binding.homeActivityNavView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

      getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame, new HomeFragment()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selected=null;

                    switch (item.getItemId()){

                        case R.id.home_item:
                           selected=new HomeFragment();
                            break;

                        case R.id.user_item:
                           // selected=new UserFragment();

                            break;

                        case R.id.note_item:
                           // selected=new NotificationFragment();

                            break;

                        case R.id.more_item:
                            //MoreItemFragment()
                           // selected=new MoreItemFragment();

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_activity_fl_frame,selected).commit();

                    return true;
                }
            };
}
