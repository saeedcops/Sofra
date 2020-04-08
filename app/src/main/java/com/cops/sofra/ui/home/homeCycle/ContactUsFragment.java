package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.contactUs.ContactUs;
import com.cops.sofra.data.model.offerDetails.OfferDetails;
import com.cops.sofra.databinding.FragmentContactUsBinding;
import com.cops.sofra.databinding.FragmentRestaurantListBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.viewModel.client.ContactUsViewModel;

import static com.cops.sofra.utils.CheckInput.isEditTextSet;
import static com.cops.sofra.utils.CheckInput.isEmailValid;
import static com.cops.sofra.utils.CheckInput.isPhoneSet;
import static com.cops.sofra.utils.HelperMethod.disappearKeypad;

public class ContactUsFragment extends BaseFragment {


   private FragmentContactUsBinding binding;
   private ContactUsViewModel contactUsViewModel;
   private RadioButton radioButton;
   private int radioId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_contact_us,container,false);
        final View view = binding.getRoot();
        setUpActivity();

        binding.contactUsFragmentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton=(RadioButton)view.findViewById(checkedId);
            }
        });


        binding.contactUsFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEditTextSet(binding.contactUsFragmentEtName,binding.contactUsFragmentEtEmail,binding.contactUsFragmentEtPhone,binding.contactUsFragmentEtMessage)
                        && isEmailValid(binding.contactUsFragmentEtEmail) && isPhoneSet(binding.contactUsFragmentEtPhone)){

                    send();
                }
            }
        });

        binding.contactUsFragmentParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappearKeypad(getActivity(),view);
            }
        });


       return view;
    }

    private void send(){
        contactUsViewModel = ViewModelProviders.of(getActivity()).get(ContactUsViewModel.class);
        contactUsViewModel.contactUs(binding.contactUsFragmentEtName.getText().toString(),binding.contactUsFragmentEtEmail.getText().toString(),
                binding.contactUsFragmentEtPhone.getText().toString(),radioButton.getText().toString(),binding.contactUsFragmentEtMessage.getText().toString());

        contactUsViewModel.contactUsMutableLiveData.observe(this, new Observer<ContactUs>() {
            @Override
            public void onChanged(ContactUs offerDetails) {

                Toast.makeText(baseActivity, offerDetails.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
