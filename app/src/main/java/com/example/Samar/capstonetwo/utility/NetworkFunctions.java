package com.example.Samar.capstonetwo.utility;

import android.content.Context;
import android.net.Uri;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.model.Data_;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NetworkFunctions {

    Context context;
    public NetworkFunctions(Context context)
    {

        this.context=context;
    }

    private ArrayList<Data_> dataArrayList;
    public ArrayList<Data_> redditNetwork()
    {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JsonStr = null;

        try {


            Uri BuildURI=Uri.parse(context.getResources().getString(R.string.JsonURL));
            URL url = new URL(BuildURI.toString());

            //Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JsonStr = buffer.toString();

        } catch (IOException e) {
            // Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getDataFromJson(JsonStr);
        } catch (JSONException e) {
            // Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;

    }
    public ArrayList<Data_> getDataFromJson(String JsonStr) throws JSONException {


        final String Data = "data";
        final String Children="children";
        final String Author="author";
        final String Title="title";
        final String Thumbnail="url";
        final String postID="id";

        dataArrayList= new ArrayList<>();

        try {

            JSONObject jsonObj=new JSONObject(JsonStr);
            JSONObject dataObj=jsonObj.getJSONObject(Data);
            JSONArray childrenArr=dataObj.getJSONArray(Children);

            for (int i = 0; i < childrenArr.length(); i++) {
                JSONObject current = childrenArr.getJSONObject(i);
                JSONObject dataObject=current.getJSONObject(Data);
                String authorName=dataObject.getString(Author);
                String TitleJson=dataObject.getString(Title);
                String idJson=dataObject.getString(postID);
                String imageJson=dataObject.getString(Thumbnail);
                Data_ data_=new Data_(TitleJson,authorName,imageJson,idJson);
                dataArrayList.add(data_);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return dataArrayList;
    }



}


