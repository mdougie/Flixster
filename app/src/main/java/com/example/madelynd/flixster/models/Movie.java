package com.example.madelynd.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by madelynd on 6/22/17.
 */

@Parcel //annotation indicates class is parselable
public class Movie {

    //values from API; fields public for parseler
    String title;
    String overview;
    String posterPath; //only the path
    String backdropPath;
    //average vote
    Double voteAverage;

    //constructor
    public Movie() {
    }

    //init from JSON data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        //TODO - this might be potential error
        voteAverage = object.getDouble("vote_average");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
}
