package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cops.sofra.R;
import com.cops.sofra.data.model.OrderItem;
import com.cops.sofra.data.model.myOffer.MyOfferData;
import com.cops.sofra.data.model.offerDetails.OfferDetails;
import com.cops.sofra.databinding.FragmentRestaurantListBinding;
import com.cops.sofra.databinding.FragmentViewOfferDetailsBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.viewModel.client.ClientOfferDetailsViewModel;
import com.cops.sofra.viewModel.room.ItemViewModel;

public class ViewOfferDetailsFragment extends BaseFragment {

    public ViewOfferDetailsFragment(String restaurantId,String imageUrl,String price,String name,int offerId)
    {
        this.offerId = offerId;
        this.name=name;
        this.price=price;
        this.imageUrl=imageUrl;
        this.restaurantId=restaurantId;
    }

    private FragmentViewOfferDetailsBinding binding;
    private ClientOfferDetailsViewModel offerDetailsViewModel;
    private int offerId;
    private String name,price,imageUrl,restaurantId;
    private OrderItem orderItem;
    private ItemViewModel itemViewModel;


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

        binding.restaurantOfferListFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderItem =new OrderItem(name,imageUrl,price,1,offerId,restaurantId);
                orderItem.setNote("No Notes");
                itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
                itemViewModel.add(orderItem);

                Toast.makeText(baseActivity, getString(R.string.item_name)+" : "+name, Toast.LENGTH_LONG).show();
            }
        });
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
