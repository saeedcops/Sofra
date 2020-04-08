package com.cops.sofra.ui.home.homeCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.sofra.R;
import com.cops.sofra.adapters.ClientCurrentOrderAdapter;
import com.cops.sofra.adapters.ClientPendingOrderAdapter;
import com.cops.sofra.data.model.clientOrder.ClientOrder;
import com.cops.sofra.data.model.clientOrder.ClientOrderData;
import com.cops.sofra.databinding.FragmentClientOrderCurrentBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.client.ClientGetOrderViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class ClientOrderCurrentFragment extends BaseFragment {


    private FragmentClientOrderCurrentBinding binding;
    private ClientCurrentOrderAdapter orderAdapter;
    private ClientGetOrderViewModel clientGetOrderViewModel;
    private List<ClientOrderData> myOrderData = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private OnEndLess onEndLess;
    private String apiToken;
    private int lastPage;
    private String current="current";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_client_order_current,container,false);
        View view = binding.getRoot();
        setUpActivity();

        clientGetOrderViewModel = ViewModelProviders.of(getActivity()).get(ClientGetOrderViewModel.class);
        layoutManager= new LinearLayoutManager(getActivity());
        binding.clientOrderCurrentFragmentRv.setLayoutManager(layoutManager);
        orderAdapter=new ClientCurrentOrderAdapter(getActivity(),myOrderData);

       // apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(),"apiToken")!=null) {

            apiToken=LoadData(getActivity(),"apiToken");

        }
        getCurrentOrder();
       return view;
    }

    private void getCurrentOrder(){


        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        clientGetOrderViewModel.getOrderList(apiToken,current,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.clientOrderCurrentFragmentRv.addOnScrollListener(onEndLess);
        if (myOrderData.size()==0) {

            clientGetOrderViewModel.getOrderList(apiToken,current,1);
        }
        binding.clientOrderCurrentFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (myOrderData.size() == 0) {

                    clientGetOrderViewModel.getOrderList(apiToken,current,onEndLess.current_page);
                }else{
                    binding.clientOrderCurrentFragmentSwipe.setRefreshing(false);
                }

            }
        });

        binding.clientOrderCurrentFragmentRv.setAdapter(orderAdapter);
        clientGetOrderViewModel.clientGetOrderMutableLiveData.observe(this, new Observer<ClientOrder>() {
            @Override
            public void onChanged(ClientOrder clientOrder) {
                if (clientOrder.getStatus()==1) {
                    binding.clientOrderCurrentFragmentSwipe.setRefreshing(false);
                    lastPage= clientOrder.getData().getLastPage();
                    Log.i("data",clientOrder.getMsg());

                    for (int i = 0; i < clientOrder.getData().getData().size(); i++) {
                        if (clientOrder.getData().getData().get(i).getState().equals("accepted")) {
                            myOrderData.add(clientOrder.getData().getData().get(i));
                        }

                    }
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
