package com.cops.sofra.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cops.sofra.R;
import com.cops.sofra.databinding.ActivityAuthBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.auth.userAuthCycle.UserLoginFragment;

public class AuthActivity extends BaseActivity {
    private ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction().replace(R.id.auth_activity_fl_frame, new UserLoginFragment()).commit();


    }
}
