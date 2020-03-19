package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.category.CategoryData;
import com.cops.sofra.databinding.ItemFoodCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<CategoryData> categoryData = new ArrayList<>();

    public CategoryAdapter(Activity activity, List<CategoryData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.categoryData = restaurantDataList;
    }



    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemFoodCategoryBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_food_category, parent, false);

        return new CategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(CategoryAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(categoryData.get(position).getPhotoUrl()).into(holder.binding.foodCategoryAdapterIv);
        holder.binding.foodCategoryAdapterTv.setText(categoryData.get(position).getName());

    }

//

    private void setAction(final CategoryAdapter.ViewHolder holder, final int position) {
//        holder.binding.restaurantAdapterRlParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
//                        .addToBackStack(null)
//                        .replace(R.id.home_activity_fl_frame, new RestaurantItemsFragment())
//                        .commit();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFoodCategoryBinding binding;


        public ViewHolder(@NonNull  ItemFoodCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

