package com.cops.sofra.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cops.sofra.R;
import com.cops.sofra.data.model.city.CityData;

import java.util.ArrayList;
import java.util.List;

public class CitySpinnerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private Integer selectedId = 0;
    private List<CityData> generalResponceData = new ArrayList();

    public CitySpinnerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    public void setData(List<CityData> generalResponseDataList, String hint) {
        generalResponseDataList.add(0,new CityData( hint));
        this.generalResponceData = generalResponseDataList;
    }


    @Override
    public int getCount() {
        return generalResponceData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_spinner, null);

        TextView names = (TextView) convertView.findViewById(R.id.item_spinner_tv);
        //names.setText(generalResponceData.get(position).getName());
        names.setText(generalResponceData.get(position).getName());
        selectedId = generalResponceData.get(position).getId();

        return convertView;
    }
}
