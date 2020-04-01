package com.cops.sofra.ui.home.homeCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.local.room.RoomDao;
import com.cops.sofra.data.model.OrderItem;
import com.cops.sofra.databinding.FragmentReviewItemFoodBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.ui.auth.AuthActivity;
import com.cops.sofra.utils.SelectedOrder;
import com.cops.sofra.viewModel.room.ItemViewModel;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class ReviewItemFoodFragment extends BaseFragment {


    private FragmentReviewItemFoodBinding binding;
    private int count=1;
    private int itemId;
    private String name,price,imageUrl;
    private OrderItem orderItem;
    private ItemViewModel itemViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_review_item_food,container,false);
        View view = binding.getRoot();
        setUpActivity();

        if (getArguments()!=null) {

            name=getArguments().getString("name");
            price=getArguments().getString("price");
            imageUrl=getArguments().getString("imageUrl");
            itemId=getArguments().getInt("itemId");
            Glide.with(getActivity()).load(imageUrl).into(binding.reviewItemFoodFragmentIv);
            binding.reviewItemFoodFragmentTvName.setText(name);
            binding.reviewItemFoodFragmentTvKind.setText(getArguments().getString("description"));
            binding.reviewItemFoodFragmentTvPrice.setText(price+" $");

        }


        setData();

        binding.reviewItemFoodFragmentCivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(LoadData(getActivity(), "userType")==null){
                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    intent.putExtra("userType", "client");
                    startActivity(intent);

                }else{

                    orderItem =new OrderItem(name,imageUrl,price,count,itemId);

                    if (!binding.reviewItemFoodFragmentEtSpecialOrder.getText().toString().equals("")) {

                        orderItem.setNote(binding.reviewItemFoodFragmentEtSpecialOrder.getText().toString());
                    }else{
                        orderItem.setNote("No Notes");
                    }


                    itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
                    itemViewModel.add(orderItem);

                    Toast.makeText(baseActivity, getString(R.string.item_name)+" : "+name, Toast.LENGTH_LONG).show();
                }

            }
        });


       return view;
    }


    private void setData() {

//        if(count==1 && !orderItem.getImageUrl().equals(imageUrl)){
//
//            orderItem =new OrderItem(name,imageUrl,price,1,itemId);
//        }

        binding.reviewItemFoodFragmentCivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=1)
                    return;
                --count;
                binding.reviewItemFoodFragmentTvCount.setText(count+"");
            }
        });

        binding.reviewItemFoodFragmentCivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ++count;
                binding.reviewItemFoodFragmentTvCount.setText(count+"");
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
