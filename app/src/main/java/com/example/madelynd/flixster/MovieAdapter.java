package com.example.madelynd.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.madelynd.flixster.models.Config;
import com.example.madelynd.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by madelynd on 6/22/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //list of movies
    ArrayList<Movie> movies;
    //config for image urls
    Config config;
    //context for rendering
    Context context;

    //initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //TODO - check here that it was getter and setter, not just setter...
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return a new ViewHolder
        return new ViewHolder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get the movie data at the specified position
        Movie movie = movies.get(position);
        //populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //determine current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        //build url for poster image
        String imageUrl = null;

        //if in portrait mode, load poster image
        if (isPortrait) {
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        }
        else {
            //load image URL for landscape
            imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());
        }

        //get correct placeholder/imageview
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;

        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);

    }

    //returns total number of items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create the view holder as a static inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //track image objects
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;



        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objs by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            //added as itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        //when user clicks on row, run MovieDetailsActivity for movie
        @Override
        public void onClick(View v) {
            //get item position
            int position = getAdapterPosition();
            //does position exist in view
            if (position != RecyclerView.NO_POSITION) {
                //get movie at position
                Movie movie = movies.get(position);
                //create intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //serialize movie with parceler
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //show activity
                context.startActivity(intent);
            }
        }
    }
}
