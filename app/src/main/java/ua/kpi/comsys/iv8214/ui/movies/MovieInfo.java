package ua.kpi.comsys.iv8214.ui.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kpi.comsys.iv8214.R;

public class MovieInfo extends Fragment {

    private ImageView moviePoster;
    private TextView movieDetails;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_movie_info, container, false);
        moviePoster = root.findViewById(R.id.poster);
        movieDetails = root.findViewById(R.id.info);
        String movieInfo = getArguments().getString("Movie");
        String poster = getArguments().getString("Poster");
        Context context = getContext();
        int resourceId = context.getResources().getIdentifier(poster,
                "drawable", getContext().getPackageName());
        if (resourceId != 0x0) {
            moviePoster.setImageDrawable(getResources().getDrawable(resourceId));
        }
        movieDetails.setText(movieInfo);

        //Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
         //setHasOptionMenu(true) was not working
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //setHasOptionsMenu(true);
        //setHasOptionsMenu(true);
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        onBackPressed();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }

    /*@Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_movie_info);
        poster = findViewById(R.id.poster);
        movieDetails = findViewById(R.id.info);
        Movie movie = (Movie) getIntent().getExtras().getSerializable("MOVIE_DETAILS");
        if (movie != null) {
            int resourceId = getResources().getIdentifier(movie.getPoster(),
                    "drawable", getPackageName());
            if (resourceId != 0x0) {
                poster.setImageDrawable(getResources().getDrawable(resourceId));
            }
            movieDetails.setText(movie.toString());
        }
    }*/
}