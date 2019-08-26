package com.example.hw3_grp3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Results> {

    public ResultAdapter(@NonNull Context context, int resource, @NonNull List<Results> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Results res=getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.listview_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvtrack = (TextView) convertView.findViewById(R.id.tvtrack);
            viewHolder.tvtrackname = (TextView)convertView.findViewById(R.id.tvtrackname);
            viewHolder.tvartist = (TextView)convertView.findViewById(R.id.tvartist);
            viewHolder.tvartistname=(TextView)convertView.findViewById(R.id.tvartistname);
            viewHolder.tvtrackprice=(TextView)convertView.findViewById(R.id.tvtrackprice);
            viewHolder.tvpriceval=(TextView)convertView.findViewById(R.id.tvpriceval);
            viewHolder.tvdate=(TextView)convertView.findViewById(R.id.tvdate);
            viewHolder.tvdateval=(TextView)convertView.findViewById(R.id.tvdatevalue);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //TextView tvtrack,tvtrackname,tvtrackprice,tvpriceval,tvartist,tvartistname,tvdate,tvdateval;

        viewHolder.tvtrack.setText("Track");
        viewHolder.tvtrackname.setText(res.trackname);
        viewHolder.tvtrackprice.setText("Price");
        viewHolder.tvpriceval.setText(res.trackprice+""+"$");
        viewHolder.tvartist.setText("Artist");
        viewHolder.tvartistname.setText(res.artist);
        viewHolder.tvdate.setText("Date");
        viewHolder.tvdateval.setText(res.date);
        //return super.getView(position, convertView, parent);
        return convertView;
    }
    private static class ViewHolder{
        TextView tvtrack,tvtrackname,tvtrackprice,tvpriceval,tvartist,tvartistname,tvdate,tvdateval;
    }

}
