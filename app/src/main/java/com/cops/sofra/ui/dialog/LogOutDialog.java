package com.cops.sofra.ui.dialog;

import android.content.Intent;
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

import com.cops.sofra.R;

import com.cops.sofra.databinding.DialogLogOutBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.splash.SplashActivity;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.clean;


public class LogOutDialog extends DialogFragment {

    private DialogLogOutBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_log_out,container,false);
        View view =binding.getRoot();


     binding.logOutDialogNo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             getDialog().dismiss();
         }
     });
     binding.logOutDialogYes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             getActivity().finish();
             getDialog().dismiss();
             clean(getActivity());
             startActivity(new Intent(getActivity(), SplashActivity.class));
         }
     });




        return view;

    }


}
