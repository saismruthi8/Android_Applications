package com.example.inclass12_801073413;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlacedetailsAdapter extends ArrayAdapter<Placedetails> {
    public PlacedetailsAdapter(Context context, int resource, List<Placedetails> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Placedetails p = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.placedetailslayout, parent, false);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.tvtripdate=(TextView)convertView.findViewById(R.id.tvtripdate);
            viewHolder.tvtripname=(TextView)convertView.findViewById(R.id.tvtrpname);
            viewHolder.tvdestcity=(TextView)convertView.findViewById(R.id.tvdestcity);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvtripname.setText(p.tripname);
        viewHolder.tvtripdate.setText(p.date);
        viewHolder.tvdestcity.setText(p.destcity);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvtripname,tvtripdate,tvdestcity;
    }
}
