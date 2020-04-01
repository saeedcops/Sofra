package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.myOffer.MyOfferData;
import com.cops.sofra.data.model.offerDetails.OfferDetails;
import com.cops.sofra.databinding.FragmentRestaurantListBinding;
import com.cops.sofra.databinding.FragmentViewOfferDetailsBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.viewModel.client.ClientOfferDetailsViewModel;

public class ViewOfferDetailsFragment extends BaseFragment {

    public ViewOfferDetailsFragment(int offerId) {
        this.offerId = offerId;
    }

    private FragmentViewOfferDetailsBinding binding;
    private ClientOfferDetailsViewModel offerDetailsViewModel;
    private int offerId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_view_offer_details,container,false);
        View view = binding.getRoot();
        setUpActivity();

        getOfferDetails();
       return view;
    }
    private void getOfferDetails(){
        offerDetailsViewModel = ViewModelProviders.of(getActivity()).get(ClientOfferDetailsViewModel.class);
        offerDetailsViewModel.getOfferDetails(offerId);
        offerDetailsViewModel.getOfferMutableLiveData.observe(this, new Observer<OfferDetails>() {
            @Override
            public void onChanged(OfferDetails offerDetails) {


                if (offerDetails.getStatus()==1) {
                    setData(offerDetails.getData());
                }
            }
        });
    }

    private void setData(MyOfferData data) {
        binding.viewOfferDetailsFragmentName.setText(data.getName());
        binding.viewOfferDetailsFragmentOffer.setText(data.getDescription());
        binding.viewOfferDetailsFragmentFrom.setText(getString(R.string.from)+" "+data.getStartingAt());
        binding.viewOfferDetailsFragmentTo.setText(getString(R.string.to)+" "+data.getEndingAt());
        binding.viewOfferDetailsFragmentPrice.setText(data.getPrice()+" $");
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
