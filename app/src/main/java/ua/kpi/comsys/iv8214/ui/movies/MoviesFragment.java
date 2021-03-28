package ua.kpi.comsys.iv8214.ui.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import ua.kpi.comsys.iv8214.R;

public class MoviesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private MovieArrayAdapter movieArrayAdapter;
    private Context context;
    private ArrayList<Movie> movies;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        listView = root.findViewById(R.id.list);
        try {
            movies = getMoviesList(loadJson("MoviesList.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        context = getContext();
        movieArrayAdapter = new MovieArrayAdapter(context, R.layout.fragment_movie_info, movies);
        listView.setAdapter(movieArrayAdapter);
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

    private ArrayList<Movie> getMoviesList(String s) {
        JSONObject jsonObject = null;
        ArrayList<Movie> movies = null;
        try {
            jsonObject = new JSONObject(s);
            movies = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setTitle(object.getString("Title"));
                movie.setYear(object.getString("Year"));
                movie.setImdbID(object.getString("imdbID"));
                movie.setType(object.getString("Type"));
                movie.setPoster(object.getString("Poster")
                        .toLowerCase()
                        .replace(".jpg", ""));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return movies;
    }

    public String loadFromAssets(String fileName) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJson(String fileName) throws IOException {
        String json = null;
        try {
            InputStream is = getActivity().openFileInput(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
            InputStream inputStream = getActivity().getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String data = new String(buffer, "UTF-8");
            OutputStream outputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
            return data;
        }
        return json;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent intent = new Intent(getActivity(), MovieInfo.class);
        intent.putExtra("MOVIE_DETAILS", (Movie) parent.getItemAtPosition(position));
        startActivity(intent);*/
        Movie movie = (Movie) parent.getItemAtPosition(position);
        if (!movie.getImdbID().equals("noid")) {
            updateMovie(movie, movie.getImdbID() + ".txt");
            MovieInfo nextFrag = new MovieInfo();
            Bundle movieInfo = new Bundle();
            movieInfo.putString("Poster", movie.getPoster());
            movieInfo.putString("Movie", movie.toString());
            nextFrag.setArguments(movieInfo);
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, nextFrag, null)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void updateMovie(Movie movie, String filename) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(loadFromAssets(filename));
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_task, menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search data here");
    }*/

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_task, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieArrayAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                return true;

            case R.id.action_add:
                AddMovie nextFrag = new AddMovie();
                Movie newMovie = new Movie();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, null)
                        .addToBackStack(null)
                        .commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
