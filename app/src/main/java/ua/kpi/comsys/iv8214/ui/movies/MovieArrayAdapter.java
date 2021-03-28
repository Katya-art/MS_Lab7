package ua.kpi.comsys.iv8214.ui.movies;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ua.kpi.comsys.iv8214.R;

public class MovieArrayAdapter extends ArrayAdapter implements Filterable {

    private List<Movie> movies;
    private List<Movie> moviesFiltered;
    private int resource;
    private Context context;

    public MovieArrayAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);
        this.context = context;
        this.resource = resource;
        this.movies = movies;
        moviesFiltered = new ArrayList<>(movies);
    }

    @Override
    public int getCount() {
        return moviesFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView movieMainInfo = (TextView) view.findViewById(R.id.textView);
        ImageView moviePoster = (ImageView) view.findViewById(R.id.imageView);

        movieMainInfo.setText(moviesFiltered.get(position).mainInfo());
        int resourceId = context.getResources().getIdentifier(moviesFiltered.get(position).getPoster(),
                "drawable", getContext().getPackageName());
        if (resourceId != 0x0) {
            moviePoster.setImageDrawable(context.getResources().getDrawable(resourceId));
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(movies);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Movie item : movies) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moviesFiltered.clear();
            moviesFiltered.addAll((List) results.values);
            if (moviesFiltered.size() == 0) {
                Toast.makeText(getContext(), "No matches", Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void remove(int position) {
        //movies.remove(position);
        //notifyDataSetChanged();
        try {
            InputStream inputStream = context.openFileInput("MoviesList.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            new File("MoviesList.txt").delete();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            jsonObject.getJSONArray("Search").remove(position);
            OutputStream outputStream = context.openFileOutput("MoviesList.txt", Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movies.remove(position);
        moviesFiltered.remove(position);
        notifyDataSetChanged();
    }
}