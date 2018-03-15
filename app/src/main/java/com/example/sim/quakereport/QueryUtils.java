package com.example.sim.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils() {
    }

  private static URL createUrl(String stringUrl)
  {
      URL url=null;
      try
      {
          url=new URL(stringUrl);

      }
      catch(MalformedURLException me)
      {
Log.e("Main Activity","error",me);
      }
  return url;
  }
   private static String makeHttpRequest(URL url)throws IOException
   {
       String jsonResponse="";
       if(url==null)
       {
           return jsonResponse;
       }
       HttpURLConnection urlConnection=null;
       InputStream inputStream=null;
       try
       {
           urlConnection=(HttpURLConnection)url.openConnection();
           urlConnection.setRequestMethod("GET");
           urlConnection.setReadTimeout(10000);
           urlConnection.setConnectTimeout(15000);
           urlConnection.connect();
           if(urlConnection.getResponseCode()==200)
           {
           inputStream=urlConnection.getInputStream();
           jsonResponse=readFromStream(inputStream);
           }
           else
           {
        Log.e(LOG_TAG,"Error Response Code "+urlConnection.getResponseCode());
           }
       }
       catch(IOException e)
       {
           Log.e(LOG_TAG,"Problem retrieving the earthquake JSON result ",e);
       }
       finally
       {
           if(urlConnection!=null)
           {
               urlConnection.disconnect();
           }
           if(inputStream!=null)
           {
               inputStream.close();
           }
       }
       return jsonResponse;
   }
    public static String readFromStream(InputStream inputStream)throws IOException
    {
StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null)
            {
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
    public static ArrayList<Earthquake> extractFeatureFromJson(String earthquakeJSon) {

        if(TextUtils.isEmpty(earthquakeJSon))
        {
return null;
        }
        ArrayList<Earthquake> earthquakes = new ArrayList<>();


        try {


            JSONObject baseJsonResponse = new JSONObject(earthquakeJSon);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < earthquakeArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                // Extract the value for the key called "mag"
                double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = properties.getString("url");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                Earthquake earthquake = new Earthquake(magnitude, location, time,url);

                // Add the new {@link Earthquake} to the list of earthquakes.
                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    public static List<Earthquake> fetchEarthquakeData(String requestUrl)
    {
URL url=createUrl(requestUrl);
        String jsonResponse=null;
        try
        {
jsonResponse=makeHttpRequest(url);
        }
        catch(IOException e)
        {
Log.e(LOG_TAG,"Problem making HTTP request",e);

        }
        List<Earthquake> earthquakes=extractFeatureFromJson(jsonResponse);
        return earthquakes;

    }

}