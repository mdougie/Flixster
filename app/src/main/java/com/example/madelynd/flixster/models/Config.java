package com.example.madelynd.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by madelynd on 6/22/17.
 */

public class Config {

    //base url for loading images
    String imageBaseUrl;
    //poster size, part of URL
    String posterSize;
    //backdrop size
    String backdropSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        //get image base URL
        imageBaseUrl = images.getString("secure_base_url");
        //get poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        //get value at index 3 or w342 as fallback
        posterSize = posterSizeOptions.optString(3, "w342");
        //parse backdrop sizes and use value at index 1 or w780
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");
    }

    //helper method for creating urls
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s", imageBaseUrl, size, path); //concat all three
    }


    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
