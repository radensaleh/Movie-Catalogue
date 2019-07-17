package com.example.rdsaleh.searchmovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdsaleh.searchmovieuiux.BuildConfig;
import com.example.rdsaleh.searchmovieuiux.DetailMovieActivity;
import com.example.rdsaleh.searchmovieuiux.Movie;
import com.example.rdsaleh.searchmovieuiux.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {

    private ArrayList<Movie> movies;
    private Context mContext;


    public AdapterMovie(ArrayList<Movie> movies, Context mContext) {
        this.movies = movies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        String imgURL = BuildConfig.IMG_URL + movies.get(i).getPoster_path();

        new DownLoadImage(myViewHolder.img).execute(imgURL);
        myViewHolder.title.setText(movies.get(i).getTitle());
        myViewHolder.overview.setText(movies.get(i).getOverview());
        myViewHolder.id_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movie_id = movies.get(i).getId();
                Intent i = new Intent(mContext, DetailMovieActivity.class);
                i.putExtra("movie_id", movie_id);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, overview;
        ImageView img;
        CardView id_movie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title    = itemView.findViewById(R.id.tv_title);
            overview = itemView.findViewById(R.id.tv_overview);
            img      = itemView.findViewById(R.id.img_photo);
            id_movie = itemView.findViewById(R.id.cv_movie);

        }
    }

    //download image dari internet
    public static class DownLoadImage extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImage(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
