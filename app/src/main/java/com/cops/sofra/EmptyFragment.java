package com.cops.sofra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cops.sofra.databinding.FragmentHomeBinding;
import com.cops.sofra.ui.BaseFragment;

public class EmptyFragment extends BaseFragment {


    EmptyFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        View view = binding.getRoot();
        setUpActivity();


       return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
