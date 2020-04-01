package com.cops.sofra.ui.dialog;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.addReview.AddReview;
import com.cops.sofra.databinding.DialogAddReviewBinding;
import com.cops.sofra.viewModel.client.ClientAddReviewViewModel;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class AddReviewDialog extends DialogFragment implements View.OnClickListener {

    public AddReviewDialog(int orderId) {
        this.restaurantId = orderId;
    }

    private int restaurantId;
    private DialogAddReviewBinding binding;
    private ClientAddReviewViewModel reviewViewModel;
    private String mApiToken;
    private int rate=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_review,container,false);
        View view =binding.getRoot();
      //  mApiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            mApiToken=LoadData(getActivity(),"apiToken");
            Log.i("api",mApiToken);
        }
        binding.addReviewDialogIvLove.setOnClickListener(this);
        binding.addReviewDialogIvGood.setOnClickListener(this);
        binding.addReviewDialogIvGreat.setOnClickListener(this);
        binding.addReviewDialogIvBad.setOnClickListener(this);
        binding.addReviewDialogIvAngry.setOnClickListener(this);


        binding.addReviewDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rate>0){
                    if(binding.addReviewDialogEtReason.getText().toString().equals("")){
                        binding.addReviewDialogEtReason.requestFocus();
                        binding.addReviewDialogEtReason.setError(getString(R.string.add_comment_rate));
                    }else{
                        addReview();

                    }

                }else {
                    Toast.makeText(getActivity(), getString(R.string.select_rate_image), Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;

    }

    private void addReview() {
        reviewViewModel = ViewModelProviders.of(getActivity()).get(ClientAddReviewViewModel.class);

        reviewViewModel.addReview(rate,binding.addReviewDialogEtReason.getText().toString(),restaurantId,mApiToken);

        reviewViewModel.clientReviewMutableLiveData.observe(this, new Observer<AddReview>() {
            @Override
            public void onChanged(AddReview addReview) {

                if (addReview.getStatus()==1) {
                    Toast.makeText(getActivity(), addReview.getMsg(), Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getActivity(), addReview.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.add_review_dialog_iv_love:
                binding.addReviewDialogIvLove.setAlpha(1f);
                binding.addReviewDialogIvGreat.setAlpha(0.3f);
                binding.addReviewDialogIvGood.setAlpha(0.3f);
                binding.addReviewDialogIvBad.setAlpha(0.3f);
                binding.addReviewDialogIvAngry.setAlpha(0.3f);
                rate=5;

                break;
            case R.id.add_review_dialog_iv_great:
                binding.addReviewDialogIvGreat.setAlpha(1f);
                binding.addReviewDialogIvLove.setAlpha(0.3f);
                binding.addReviewDialogIvGood.setAlpha(0.3f);
                binding.addReviewDialogIvBad.setAlpha(0.3f);
                binding.addReviewDialogIvAngry.setAlpha(0.3f);
                rate=4;

                break;
            case R.id.add_review_dialog_iv_good:
                binding.addReviewDialogIvGood.setAlpha(1f);
                binding.addReviewDialogIvGreat.setAlpha(0.3f);
                binding.addReviewDialogIvLove.setAlpha(0.3f);
                binding.addReviewDialogIvBad.setAlpha(0.3f);
                binding.addReviewDialogIvAngry.setAlpha(0.3f);
                rate=3;
                break;
            case R.id.add_review_dialog_iv_bad:

                binding.addReviewDialogIvBad.setAlpha(1f);
                binding.addReviewDialogIvGreat.setAlpha(0.3f);
                binding.addReviewDialogIvGood.setAlpha(0.3f);
                binding.addReviewDialogIvLove.setAlpha(0.3f);
                binding.addReviewDialogIvAngry.setAlpha(0.3f);
                rate=2;
                break;
            case R.id.add_review_dialog_iv_angry:

                binding.addReviewDialogIvAngry.setAlpha(1f);
                binding.addReviewDialogIvGreat.setAlpha(0.3f);
                binding.addReviewDialogIvGood.setAlpha(0.3f);
                binding.addReviewDialogIvBad.setAlpha(0.3f);
                binding.addReviewDialogIvLove.setAlpha(0.3f);
                rate=1;
                break;

        }
    }
}
