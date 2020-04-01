package com.cops.sofra.ui.home.homeCycleRestaurant;

import android.os.Bundle;
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
import com.cops.sofra.adapters.RestaurantNotificationsAdapter;
import com.cops.sofra.data.model.notifications.Notifications;
import com.cops.sofra.data.model.notifications.NotificationsData;
import com.cops.sofra.databinding.FragmentRestaurantNotificationsBinding;
import com.cops.sofra.ui.BaseFragment;
import com.cops.sofra.utils.CheckInput;
import com.cops.sofra.utils.OnEndLess;
import com.cops.sofra.viewModel.client.ClientNotificationsViewModel;
import com.cops.sofra.viewModel.restaurant.RestaurantNotificationsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;


public class RestaurantNotificationsFragment extends BaseFragment {


    private FragmentRestaurantNotificationsBinding binding;
    private RestaurantNotificationsViewModel notificationsViewModel;
    private RestaurantNotificationsAdapter notificationsAdapter;
    private LinearLayoutManager layoutManager;
    private List<NotificationsData> notificationsData=new ArrayList<>();
    private OnEndLess onEndLess;
    private int lastPage;
    private String apiToken;
    private ClientNotificationsViewModel clientNotificationsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new CheckInput(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_notifications, container, false);
        View view = binding.getRoot();
        setUpActivity();
     //   apiToken="Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";
        if (LoadData(getActivity(), "apiToken") != null) {

            apiToken = LoadData(getActivity(), "apiToken");

            if(LoadData(getActivity(), "userType").equals("client")){

                getClientNotifications();
            }else{

                getNotifications();
            }

        }



        return view;
    }

    private void getNotifications() {

        notificationsViewModel= ViewModelProviders.of(getActivity()).get(RestaurantNotificationsViewModel.class);
        layoutManager=new LinearLayoutManager(getActivity());
        notificationsAdapter =new RestaurantNotificationsAdapter(getActivity(),notificationsData);
        binding.restaurantNotificationsFragmentRv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        notificationsViewModel.getNotifications(apiToken,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.restaurantNotificationsFragmentRv.addOnScrollListener(onEndLess);
        if (notificationsData.size()==0) {

            notificationsViewModel.getNotifications(apiToken,1);
        }
        binding.restaurantNotificationsFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (notificationsData.size() == 0) {

                    notificationsViewModel.getNotifications(apiToken,onEndLess.current_page);
                }
                binding.restaurantNotificationsFragmentSwipe.setRefreshing(false);
            }
        });

        binding.restaurantNotificationsFragmentRv.setAdapter(notificationsAdapter);

        notificationsViewModel.restaurantsNotificationsMutableLiveData.observe(this, new Observer<Notifications>() {
            @Override
            public void onChanged(Notifications notifications) {
                if (notifications.getStatus()==1) {
                    lastPage= notifications.getData().getLastPage();
                    notificationsData.clear();
                    notificationsData.addAll(notifications.getData().getData());
                    notificationsAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void getClientNotifications() {

        clientNotificationsViewModel= ViewModelProviders.of(getActivity()).get(ClientNotificationsViewModel.class);
        layoutManager=new LinearLayoutManager(getActivity());
        notificationsAdapter =new RestaurantNotificationsAdapter(getActivity(),notificationsData);
        binding.restaurantNotificationsFragmentRv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= lastPage) {

                    if (lastPage != 0 && current_page != 1) {

                        onEndLess.previous_page = current_page;

                        clientNotificationsViewModel.getClientNotifications(apiToken,current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        binding.restaurantNotificationsFragmentRv.addOnScrollListener(onEndLess);
        if (notificationsData.size()==0) {

            clientNotificationsViewModel.getClientNotifications(apiToken,1);
        }
        binding.restaurantNotificationsFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (notificationsData.size() == 0) {

                    clientNotificationsViewModel.getClientNotifications(apiToken,onEndLess.current_page);
                }
                binding.restaurantNotificationsFragmentSwipe.setRefreshing(false);
            }
        });

        binding.restaurantNotificationsFragmentRv.setAdapter(notificationsAdapter);

        clientNotificationsViewModel.clientNotificationsMutableLiveData.observe(this, new Observer<Notifications>() {
            @Override
            public void onChanged(Notifications notifications) {
                if (notifications.getStatus()==1) {
                    lastPage= notifications.getData().getLastPage();
                    notificationsData.clear();
                    notificationsData.addAll(notifications.getData().getData());
                    notificationsAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
