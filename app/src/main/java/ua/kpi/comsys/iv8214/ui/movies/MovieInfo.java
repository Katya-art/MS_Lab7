package ua.kpi.comsys.iv8214.ui.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ua.kpi.comsys.iv8214.R;

public class MovieInfo extends Fragment {

    private ImageView moviePoster;
    private TextView movieDetails;
    private Context context;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_movie_info, container, false);
        moviePoster = root.findViewById(R.id.poster);
        movieDetails = root.findViewById(R.id.info);
        String movieImdbID = getArguments().getString("imdbID");
        context = getContext();
        new MovieInformation().execute("http://www.omdbapi.com/?apikey=7e9fe69e&i=" + movieImdbID);
        return root;
    }

    class MovieInformation extends AsyncTask<String, Void, String> {
        //This method will run on UIThread and it will execute before doInBackground
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //This method will run on background thread and after completion it will return result to onPostExecute
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                //As we are passing just one parameter to AsyncTask, so used strings[0] to get value at 0th position that is URL
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //Getting input stream from connection, that is response which we got from server
                InputStream inputStream = urlConnection.getInputStream();
                //Reading the response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                //Returning the response message to onPostExecute method
                return s;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }
        //This method runs on UIThread and it will execute when doINBackground is completed
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {
                //Parent JSON Object. Json object start at { and end at }
                jsonObject = new JSONObject(s);
                Movie movie = new Movie();
                movie.setTitle(jsonObject.getString("Title"));
                movie.setYear(jsonObject.getString("Year"));
                movie.setImdbID(jsonObject.getString("imdbID"));
                movie.setType(jsonObject.getString("Type"));
                movie.setPoster(jsonObject.getString("Poster"));
                movie.setRated(jsonObject.getString("Rated"));
                movie.setReleased(jsonObject.getString("Released"));
                movie.setRuntime(jsonObject.getString("Runtime"));
                movie.setGenre(jsonObject.getString("Genre"));
                movie.setDirector(jsonObject.getString("Director"));
                movie.setWriter(jsonObject.getString("Writer"));
                movie.setActors(jsonObject.getString("Actors"));
                movie.setPlot(jsonObject.getString("Plot"));
                movie.setLanguage(jsonObject.getString("Language"));
                movie.setCountry(jsonObject.getString("Country"));
                movie.setAwards(jsonObject.getString("Awards"));
                movie.setImdbRating(jsonObject.getString("imdbRating"));
                movie.setImdbVotes(jsonObject.getString("imdbVotes"));
                movie.setProduction(jsonObject.getString("Production"));

                Glide.with(context).load(movie.getPoster()).into(moviePoster);
                movieDetails.setText(movie.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
}