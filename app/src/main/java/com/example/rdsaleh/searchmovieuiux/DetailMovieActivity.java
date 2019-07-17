package com.example.rdsaleh.searchmovieuiux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdsaleh.searchmovieuiux.adapter.AdapterMovie;
import com.example.rdsaleh.searchmovieuiux.retroserver.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity {

    private String api_key  = BuildConfig.API_KEY;
    private String language = "en-US";

    private TextView tv_title, tv_tagline, tv_rating, tv_runtime, tv_language, tv_release, tv_budget, tv_overview;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.detail_movie);
        setContentView(R.layout.activity_detail_movie);

        tv_title    = findViewById(R.id.tv_title);
        tv_tagline  = findViewById(R.id.tv_tagline);
        tv_rating   = findViewById(R.id.tv_rating);
        tv_runtime  = findViewById(R.id.tv_runtime);
        tv_language = findViewById(R.id.tv_language);
        tv_release  = findViewById(R.id.tv_release);
        tv_budget   = findViewById(R.id.tv_budget);
        tv_overview = findViewById(R.id.tv_overview);

        img = findViewById(R.id.img);

        showDetailMovie();

    }

    public void showDetailMovie(){
        Intent i = getIntent();
        Bundle b = i.getExtras();

        String movie_id = (String) b.get("movie_id");

        Call<MovieDetail> detailMovieCall = Retrofit
                .getInstance()
                .baseAPI()
                .getDetailMovie(movie_id, api_key, language);

        detailMovieCall.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                String title    = response.body().getTitle();
                String tagline  = response.body().getTagline();
                String rating   = response.body().getVote_average();
                int runtime     = response.body().getRuntime();
                String language = response.body().getOriginal_language();
                String release  = response.body().getRelease_date();
                int budget      = response.body().getBudget();
                String overview = response.body().getOverview();
                String poster   = response.body().getPoster_path();

                String imgURL = BuildConfig.IMG_URL + poster;

                new AdapterMovie.DownLoadImage(img).execute(imgURL);

                tv_title.setText(title);
                tv_tagline.setText(tagline);
                tv_rating.setText(rating);
                tv_runtime.setText(String.valueOf(runtime));
                tv_language.setText(language.toUpperCase());
                tv_release.setText(release);
                tv_budget.setText(String.valueOf(budget));
                tv_overview.setText(overview);

            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e("Error : ", t.toString());
            }
        });


    }
}
