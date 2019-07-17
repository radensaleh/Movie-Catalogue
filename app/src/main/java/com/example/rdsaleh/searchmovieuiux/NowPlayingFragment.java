package com.example.rdsaleh.searchmovieuiux;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rdsaleh.searchmovieuiux.adapter.AdapterMovie;
import com.example.rdsaleh.searchmovieuiux.retroserver.Retrofit;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NowPlayingFragment extends Fragment {

    private Context mContext;
    private ResultMovie resultMovie;
    private ArrayList<Movie> movies;

    private AdapterMovie adapterMovie;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private String api_key  = BuildConfig.API_KEY;
    private String language = "en-US";
    private int page        = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_now_playing, container, false);

        mContext = getContext();
        recyclerView  = getView.findViewById(R.id.recyclerMovie);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        showMovies();

        return getView;
    }

    public void showMovies(){
        Call<ResultMovie> resultMovieCall = Retrofit.getInstance()
                .baseAPI()
                .getMoviePlaying(api_key, language, page);

        resultMovieCall.enqueue(new Callback<ResultMovie>() {
            @Override
            public void onResponse(Call<ResultMovie> call, Response<ResultMovie> response) {
                resultMovie = response.body();
                movies = new ArrayList<>(Arrays.asList(resultMovie.getMovies()));
                adapterMovie = new AdapterMovie(movies, mContext);
                recyclerView.setAdapter(adapterMovie);
                adapterMovie.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResultMovie> call, Throwable t) {
                Log.e("Error : ", t.toString());
            }
        });
    }

}
