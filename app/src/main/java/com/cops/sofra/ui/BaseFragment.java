package com.cops.sofra.ui;



import androidx.fragment.app.Fragment;

import com.cops.sofra.ui.BaseActivity;

public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();

        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }

}
