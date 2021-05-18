package ua.kpi.comsys.iv8214.ui.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lab7";
    private static final String TABLE_MOVIES = "movies";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_YEAR = "year";
    private static final String KEY_IMDB_ID = "imdbID";
    private static final String KEY_TYPE = "type";
    private static final String KEY_POSTER = "poster";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT NOT NULL UNIQUE," + KEY_YEAR +
                " TEXT," + KEY_IMDB_ID + " TEXT," + KEY_TYPE + " TEXT," + KEY_POSTER + " TEXT)";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop old version if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        //Create table again
        onCreate(db);
    }

    //Add the new movie
    void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_IMDB_ID, movie.getImdbID());
        values.put(KEY_TYPE, movie.getType());
        values.put(KEY_POSTER, movie.getPoster());

        //Inserting row
        db.insert(TABLE_MOVIES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    //Code to get the single movie
    Movie getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_ID,
                        KEY_TITLE, KEY_YEAR, KEY_IMDB_ID, KEY_TYPE, KEY_POSTER }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Movie movie = new Movie(cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5));
        // return movie
        return movie;
    }

    // code to get all movies in a list view
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(1));
                movie.setYear(cursor.getString(2));
                movie.setImdbID(cursor.getString(3));
                movie.setType(cursor.getString(4));
                movie.setPoster(cursor.getString(5));
                // Adding contact to list
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }

        // return contact list
        return moviesList;
    }

    // code to get all movies in a list view
    public ArrayList<Movie> getMovies(String s) {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES + " WHERE " + KEY_TITLE + " LIKE " +
                "\"" + s + "%\";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(1));
                movie.setYear(cursor.getString(2));
                movie.setImdbID(cursor.getString(3));
                movie.setType(cursor.getString(4));
                movie.setPoster(cursor.getString(5));
                // Adding contact to list
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }

        // return contact list
        return moviesList;
    }
}
