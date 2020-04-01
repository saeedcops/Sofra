package com.cops.sofra.ui.dialog;

import android.os.Bundle;
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

import com.cops.sofra.data.model.acceptOrder.AcceptOrder;
import com.cops.sofra.databinding.DialogOrderRefuseBinding;

import com.cops.sofra.viewModel.restaurant.RestaurantRefuseOrderViewModel;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class OrderRefuseDialog extends DialogFragment {

    public OrderRefuseDialog(int orderId) {
        this.orderId = orderId;
    }

    private int orderId;
    private DialogOrderRefuseBinding binding;
    private RestaurantRefuseOrderViewModel refuseOrderViewModel;
    private String mApiToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_order_refuse,container,false);
        View view =binding.getRoot();
       // mApiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            mApiToken=LoadData(getActivity(),"apiToken");

        }


        binding.dialogOrderRefuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!binding.dialogOrderRefuseEtReason.getText().toString().equals("")){

                    refuseOrder();
                    getDialog().dismiss();

                }else {
                    binding.dialogOrderRefuseEtReason.setError(getString(R.string.refuse_reason));
                    binding.dialogOrderRefuseEtReason.requestFocus();
                }


            }
        });


        return view;

    }

    private void refuseOrder() {

        refuseOrderViewModel= ViewModelProviders.of(getActivity()).get(RestaurantRefuseOrderViewModel.class);

        refuseOrderViewModel.refuseOrder(mApiToken,orderId,binding.dialogOrderRefuseEtReason.getText().toString());

        refuseOrderViewModel.restaurantsRefuseOrderMutableLiveData.observe(this, new Observer<AcceptOrder>() {
            @Override
            public void onChanged(AcceptOrder acceptOrder) {

                if (acceptOrder.getStatus()==1) {
                    Toast.makeText(getActivity(), acceptOrder.getMsg(), Toast.LENGTH_SHORT).show();
                }else
              Toast.makeText(getActivity(), acceptOrder.getMsg(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}
