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

public class ListAdapter extends ArrayAdapter<Trip> {

    public ListAdapter(Context context, int resource, List<Trip> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Trip t = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listviewlayout, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.tvplacename=(TextView)convertView.findViewById(R.id.tvplacename);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvplacename.setText(t.name);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvplacename;
    }
}