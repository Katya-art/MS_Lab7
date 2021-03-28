package ua.kpi.comsys.iv8214.ui.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import ua.kpi.comsys.iv8214.R;

public class AddMovie extends Fragment implements View.OnClickListener {

    private EditText title;
    private EditText type;
    private EditText year;
    private Button add;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_add_movie, container, false);

        title = (EditText) root.findViewById(R.id.input_title);
        type = (EditText) root.findViewById(R.id.input_type);
        year = (EditText) root.findViewById(R.id.input_year);
        add = (Button) root.findViewById(R.id.btnAdd);
        add.setOnClickListener(this);

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

    //movie.put("Title", title.getText().toString());

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:

                try {
                    InputStream inputStream = getActivity().openFileInput("MoviesList.txt");
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    new File("MoviesList.txt").delete();
                    String json = new String(buffer, "UTF-8");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject movie = new JSONObject();
                    movie.put("Title", title.getText().toString());
                    movie.put("Year", year.getText().toString());
                    movie.put("imdbID", "noid");
                    movie.put("Type", type.getText().toString());
                    movie.put("Poster", "");
                    jsonObject.getJSONArray("Search").put(movie);
                    Log.i("JSON Array: ", jsonObject.toString());
                    OutputStream outputStream = getActivity().openFileOutput("MoviesList.txt", Context.MODE_PRIVATE);
                    outputStream.write(jsonObject.toString().getBytes());
                    outputStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                FragmentManager fm = getFragmentManager();
                assert fm != null;
                fm.popBackStack();
                break;

            default:
                break;
        }
    }
}
