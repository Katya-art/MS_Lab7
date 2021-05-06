package ua.kpi.comsys.iv8214.ui.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import org.jetbrains.annotations.NotNull;
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
import java.net.URLDecoder;
import java.util.ArrayList;

import ua.kpi.comsys.iv8214.R;

public class MoviesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ProgressBar progressBar;
    private MovieArrayAdapter movieArrayAdapter;
    private Context context;

    private String mSearchQuery;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        listView = root.findViewById(R.id.list);
        progressBar = root.findViewById(R.id.progressBar);
        context = getContext();
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(listView),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                movieArrayAdapter.remove(position);
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnItemClickListener(this);

        return root;
    }

    class MovieList extends AsyncTask<String, Void, String> {
        //This method will run on UIThread and it will execute before doInBackground
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
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
                ArrayList<Movie> movies = new ArrayList<>();
                //JSON Array of parent JSON object. Json array starts from [ and end at ]
                JSONArray jsonArray = jsonObject.getJSONArray("Search");
                //Reading JSON object inside Json array
                for (int i = 0; i < jsonArray.length(); i++) {
                    //Reading JSON Object at 'i' th position of JSON Array
                    JSONObject object = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setTitle(object.getString("Title"));
                    movie.setYear(object.getString("Year"));
                    movie.setImdbID(object.getString("imdbID"));
                    movie.setType(object.getString("Type"));
                    movie.setPoster(object.getString("Poster"));
                    movies.add(movie);
                }
                movieArrayAdapter = new MovieArrayAdapter(context, R.layout.fragment_movie_info, movies);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                listView.setAdapter(movieArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Movie movie = (Movie) parent.getItemAtPosition(position);
        if (!movie.getImdbID().equals("noid")) {
            MovieInfo nextFrag = new MovieInfo();
            Bundle movieInfo = new Bundle();
            movieInfo.putString("imdbID", movie.getImdbID());
            nextFrag.setArguments(movieInfo);
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, nextFrag, null)
                    .addToBackStack(null)
                    .commit();
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_movies_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if(mSearchQuery != null){
            searchView.setIconified(true);
            searchView.onActionViewExpanded();
            searchView.setQuery(mSearchQuery, false);
            searchView.setFocusable(true);
            new MovieList().execute("http://www.omdbapi.com/?apikey=7e9fe69e&s=" + mSearchQuery + "&page=1");
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = URLDecoder.decode(newText);
                mSearchQuery = newText;
                //Executing AsyncTask, passing api as parameter
                if (newText.length() >= 3) {
                    new MovieList().execute("http://www.omdbapi.com/?apikey=7e9fe69e&s=" + newText + "&page=1");
                } else {
                    try {
                        movieArrayAdapter.clearData();
                        movieArrayAdapter.notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        outState.putString("searchQuery", mSearchQuery);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_add) {
            AddMovie nextFrag = new AddMovie();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, nextFrag, null)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
