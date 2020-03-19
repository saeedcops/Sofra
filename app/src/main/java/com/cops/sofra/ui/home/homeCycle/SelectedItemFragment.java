package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.databinding.FragmentSelectedItemBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.SelectedOrder;

public class SelectedItemFragment extends BaseFragment {


    private FragmentSelectedItemBinding binding;
    private int count=1;
    public static SelectedOrder selectedOrder;
    private String name,price,imageUrl;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_selected_item,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (getArguments()!=null) {

            name=getArguments().getString("name");
            price=getArguments().getString("price");
            imageUrl=getArguments().getString("imageUrl");

            Glide.with(getActivity()).load(imageUrl).into(binding.selectedItemFragmentIv);
            binding.selectedItemFragmentTvName.setText(name);
            binding.selectedItemFragmentTvKind.setText(getArguments().getString("description"));
            binding.selectedItemFragmentTvPrice.setText(price+" $");

        }

        setCount();

        binding.selectedItemFragmentCivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedOrder=new SelectedOrder(name,imageUrl,price,count);

                getFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new SelectedItemEditFragment()).commit();
            }
        });


       return view;
    }

    private void setCount() {

        binding.selectedItemFragmentCivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1)
                    return;
                count--;
                binding.selectedItemFragmentTvCount.setText(count+"");
            }
        });

        binding.selectedItemFragmentCivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                binding.selectedItemFragmentTvCount.setText(count+"");
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
