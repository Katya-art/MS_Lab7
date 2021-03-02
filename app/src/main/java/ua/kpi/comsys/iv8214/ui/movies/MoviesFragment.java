package ua.kpi.comsys.iv8214.ui.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ua.kpi.comsys.iv8214.R;

public class MoviesFragment extends Fragment {

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies, container, false);
        TableLayout tableLayout = root.findViewById(R.id.movies_list);
        TableRow tableRow;
        TextView textView;
        ImageView imageView;

        for (int i = 0; i < movieList().size(); i++) {
            tableRow = new TableRow(getActivity().getApplicationContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            imageView = new ImageView(getActivity().getApplicationContext());
            imageView.setLayoutParams(new TableRow.LayoutParams(300, 466));
            Context context = getContext();
            int resourceId = context.getResources().getIdentifier(movieList().get(i).getPoster(),
                    "drawable", getContext().getPackageName());
            if (resourceId != 0x0) {
                imageView.setImageDrawable(getResources().getDrawable(resourceId));
            }

            if (i == movieList().size() - 1) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.poster_10));
            }
            imageView.setPadding(30, 0, 20, 0);
            tableRow.addView(imageView);

            textView = new TextView(getActivity().getApplicationContext());
            textView.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            textView.setText(movieList().get(i).toString());
            textView.setPadding(20, 50, 325, 0);
            tableRow.addView(textView);

            tableLayout.addView(tableRow);
        }
        return root;
    }

    private String readFile() {
        AssetManager assetManager = getActivity().getAssets();

        InputStream input;
        try {
            input = assetManager.open("MoviesList.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            String text = new String(buffer);

            //tvText.setText(text);
            return text;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return "Can't open file";
        }
    }

    private ArrayList<Movie> movieList() {
        ArrayList<Movie> moviesList = new ArrayList<>();
        String text = readFile();
        text = text.split("\\[\\{")[1].replace("}]}", "").replaceAll(
                "\\{", "");
        String[] movies = text.split("\\},");
        for (String movie : movies) {
            String[] parameters = movie.split("\",\"");
            Movie movie1 = new Movie();
            movie1.setTitle(parameters[0].split("\":\"")[1]);
            movie1.setYear(parameters[1].split(":")[1].replaceAll("\"",
                    ""));
            movie1.setImdbID(parameters[2].split("\":\"")[1]);
            movie1.setType(parameters[3].split(":")[1].replaceAll("\"",
                    ""));
            movie1.setPoster(parameters[4].split("\":\"")[1].replace("\"",
                    "").replace(".jpg", "").toLowerCase().replaceAll(
                            " ", ""));
            moviesList.add(movie1);
        }
        return moviesList;
    }
}
