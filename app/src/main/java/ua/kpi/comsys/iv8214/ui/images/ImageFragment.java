package ua.kpi.comsys.iv8214.ui.images;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

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
import java.util.ArrayList;

import ua.kpi.comsys.iv8214.R;
import ua.kpi.comsys.iv8214.ui.movies.Movie;
import ua.kpi.comsys.iv8214.ui.movies.MovieArrayAdapter;
import ua.kpi.comsys.iv8214.ui.movies.MoviesFragment;

import static android.app.Activity.RESULT_OK;

public class ImageFragment extends Fragment {

    private ProgressBar progressBar;
    private Context context;
    private LinearLayout linearLayout;
    int numberOfImages = 0;
    int id = 0;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_images, container, false);
        progressBar = root.findViewById(R.id.progressBar1);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        linearLayout = root.findViewById(R.id.linearLayout1);
        context = getContext();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetImages().execute("https://pixabay.com/api/?key=19193969-87191e5db266905fe8936d565&q=yellow+flowers&image_type=photo&per_page=27");
    }

    class GetImages extends AsyncTask<String, Void, String> {
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
                //JSON Array of parent JSON object. Json array starts from [ and end at ]
                JSONArray jsonArray = jsonObject.getJSONArray("hits");
                //Reading JSON object inside Json array
                for (int i = 0; i < jsonArray.length(); i++) {
                    //Reading JSON Object at 'i' th position of JSON Array
                    JSONObject object = jsonArray.getJSONObject(i);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    setImage(object.getString("previewURL"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setImage(String image) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        System.out.println("Screen width " + width);
        LinearLayout linearLayout1 = null;
        LinearLayout linearLayout2;
        if (numberOfImages == 0 || numberOfImages == 3 || numberOfImages == 6) {
            linearLayout1 = new LinearLayout(getActivity());
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            id ++;
            linearLayout1.setId(id);
            linearLayout.addView(linearLayout1);
        }
        if (numberOfImages == 0 || numberOfImages == 7) {
            if (numberOfImages == 7) {
                linearLayout1 = (LinearLayout) getActivity().findViewById(id - 1);
            }
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3*2, (int)width/3*2));
            imageView.setBackgroundColor(Color.rgb(190, 190, 190));
            Glide.with(context).load(image).into(imageView);
            linearLayout1.addView(imageView);
        }
        if (numberOfImages == 1 || numberOfImages == 6) {
            if (numberOfImages == 1) {
                linearLayout1 = (LinearLayout) getActivity().findViewById(id);
            } else {
                linearLayout1 = (LinearLayout) getActivity().findViewById(id);
            }
            linearLayout2 = new LinearLayout(getActivity());
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            id++;
            linearLayout2.setId(id);
            linearLayout1.addView(linearLayout2);
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3, (int)width/3));
            imageView.setBackgroundColor(Color.rgb(190, 190, 190));
            Glide.with(context).load(image).into(imageView);
            linearLayout2.addView(imageView);
        } else if (numberOfImages == 2 || numberOfImages == 8) {
            if (numberOfImages == 2) {
                linearLayout2 = (LinearLayout) getActivity().findViewById(id);
            } else {
                linearLayout2 = (LinearLayout) getActivity().findViewById(id);
            }
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3, (int)width/3));
            imageView.setBackgroundColor(Color.rgb(190, 190, 190));
            Glide.with(context).load(image).into(imageView);
            linearLayout2.addView(imageView);
        }
        if (numberOfImages == 3 || numberOfImages == 4 || numberOfImages == 5) {
            linearLayout1 = (LinearLayout) getActivity().findViewById(id);
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3, (int)width/3));
            imageView.setBackgroundColor(Color.rgb(190, 190, 190));
            Glide.with(context).load(image).into(imageView);
            linearLayout1.addView(imageView);
        }
        numberOfImages++;
        System.out.println("Number of images: " + numberOfImages);
        if (numberOfImages == 9) {
            numberOfImages = 0;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_images_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    //permission not granted, request it
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup for runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else {
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else {
                //system os is less then marshmallow
                pickImageFromGallery();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //handle result of picked image
    @SuppressLint("ResourceType")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            int width = display.getWidth();  // deprecated
            System.out.println("Screen width " + width);
            LinearLayout linearLayout1 = null;
            LinearLayout linearLayout2;
            if (numberOfImages == 0 || numberOfImages == 3 || numberOfImages == 6) {
                linearLayout1 = new LinearLayout(getActivity());
                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                id ++;
                linearLayout1.setId(id);
                linearLayout.addView(linearLayout1);
            }
            if (numberOfImages == 0 || numberOfImages == 7) {
                if (numberOfImages == 7) {
                    linearLayout1 = (LinearLayout) getActivity().findViewById(id - 1);
                }
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3*2, (int)width/3*2));
                imageView.setImageURI(data.getData());
                linearLayout1.addView(imageView);
            }
            if (numberOfImages == 1 || numberOfImages == 6) {
                if (numberOfImages == 1) {
                    linearLayout1 = (LinearLayout) getActivity().findViewById(id);
                } else {
                    linearLayout1 = (LinearLayout) getActivity().findViewById(id);
                }
                linearLayout2 = new LinearLayout(getActivity());
                linearLayout2.setOrientation(LinearLayout.VERTICAL);
                id++;
                linearLayout2.setId(id);
                linearLayout1.addView(linearLayout2);
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3, (int)width/3));
                imageView.setImageURI(data.getData());
                linearLayout2.addView(imageView);
            } else if (numberOfImages == 2 || numberOfImages == 8) {
                if (numberOfImages == 2) {
                    linearLayout2 = (LinearLayout) getActivity().findViewById(id);
                } else {
                    linearLayout2 = (LinearLayout) getActivity().findViewById(id);
                }
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3, (int)width/3));
                imageView.setImageURI(data.getData());
                linearLayout2.addView(imageView);
            }
            if (numberOfImages == 3 || numberOfImages == 4 || numberOfImages == 5) {
                linearLayout1 = (LinearLayout) getActivity().findViewById(id);
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams((int)width/3, (int)width/3));
                imageView.setImageURI(data.getData());
                linearLayout1.addView(imageView);
            }
            numberOfImages++;
            System.out.println("Number of images: " + numberOfImages);
            if (numberOfImages == 9) {
                numberOfImages = 0;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            LinearLayout linearLayoutChildAt = (LinearLayout) linearLayout.getChildAt(i);
            for (int j = 0; j < linearLayoutChildAt.getChildCount(); j++) {
                if (linearLayoutChildAt.getChildAt(j) instanceof ImageView) {
                    if (linearLayoutChildAt.getChildAt(j + 1) instanceof ImageView ||
                            linearLayoutChildAt.getChildAt(j - 1) instanceof ImageView) {
                        linearLayoutChildAt.getChildAt(j).setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
                    } else {
                        linearLayoutChildAt.getChildAt(j).setLayoutParams(new LinearLayout.LayoutParams(width / 3 * 2, width / 3 * 2));
                    }
                }
                if (linearLayoutChildAt.getChildAt(j) instanceof LinearLayout) {
                    LinearLayout linearLayout1 = (LinearLayout) linearLayoutChildAt.getChildAt(j);
                    for (int k = 0; k < linearLayout1.getChildCount(); k++) {
                        linearLayout1.getChildAt(k).setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
                    }
                }
            }
        }
    }
}
