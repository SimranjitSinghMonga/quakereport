package com.example.sim.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sim on 08/03/2018.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake>
{
    public EarthquakeAdapter(Context context,List<Earthquake> earthquakes)
    {
        super(context,0,earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView=convertView;
        if(listItemView==null)
        {
              listItemView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);

        }
        Earthquake currentEarthquake=getItem(position);
        TextView magnitudeView=(TextView)listItemView.findViewById(R.id.magnitude);
        magnitudeView.setText(currentEarthquake.getMagnitude());

        TextView locationView=(TextView)listItemView.findViewById(R.id.location);
        locationView.setText(currentEarthquake.getLocation());

        Date dateObject=new Date(currentEarthquake.getTimeInMilliSeconds());

        TextView dateView=(TextView)listItemView.findViewById(R.id.date);
        String formatDate=formatDate(dateObject);
        dateView.setText(formatDate);

        TextView timeView=(TextView)listItemView.findViewById(R.id.time);
        String formatTime=formatTime(dateObject);
        timeView.setText(formatTime);


        return listItemView;
    }
    public String formatDate(Date dateObject)
    {
  SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd,yyyy");
        return dateFormat.format(dateObject);
    }
    public String formatTime(Date dateObject)
    {
        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
