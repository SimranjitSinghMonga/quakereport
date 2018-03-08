package com.example.sim.quakereport;

/**
 * Created by Sim on 08/03/2018.
 */
public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private long mTimeInMilliSeconds;

    public Earthquake(String magnitude,String location,long timeInMilliSeconds)
    {
      mMagnitude=magnitude;
        mLocation=location;
        mTimeInMilliSeconds=timeInMilliSeconds;
    }
    public String getMagnitude()
    {
        return mMagnitude;
    }
    public String getLocation()
    {
        return mLocation;
    }
    public long getTimeInMilliSeconds()
    {
        return mTimeInMilliSeconds;
    }
}
