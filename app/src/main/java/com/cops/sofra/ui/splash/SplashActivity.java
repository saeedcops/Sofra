package com.cops.sofra.ui.splash;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.cops.sofra.databinding.ActivitySplashBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.R;
import com.cops.sofra.ui.auth.AuthActivity;
import com.cops.sofra.ui.home.HomeActivity;

import static com.cops.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.cops.sofra.data.local.SharedPreferencesManger.SaveData;

public class SplashActivity extends BaseActivity {

   private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (LoadData(this,"userType")!=null) {


            if (LoadData(this, "userType").equals("client")) {

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);

            } else if (LoadData(this, "userType").equals("seller")) {

                Intent intent = new Intent(this, AuthActivity.class);

                startActivity(intent);
            }

        }else {

            binding.splashActivityBtnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.putExtra("userType","client");
                    startActivity(intent);
                }
            });

            binding.splashActivityBtnSell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                    intent.putExtra("userType","seller");
                    startActivity(intent);

                }
            });
        }

    }
}
