package com.cops.sofra.ui;



import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    public BaseFragment baseFragment;
    
    public void superBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
