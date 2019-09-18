package quiz.android.bits.com.moviesearchfr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movies_db";


    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create movies table
        db.execSQL(Movie.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Movie.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public int addMovie(Movie Movie) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Movie.COLUMN_TITLE, Movie.getTitle());
        values.put(Movie.COLUMN_CAST, Movie.getCast());
        values.put(Movie.COLUMN_DIRECTOR, Movie.getDirector());
        values.put(Movie.COLUMN_PLOT, Movie.getPlot());

        // insert row
        long id = db.insert(Movie.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return (int)id;
    }

    public Movie getMovie(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{Movie.COLUMN_ID, Movie.COLUMN_TITLE, Movie.COLUMN_CAST, Movie.COLUMN_DIRECTOR, Movie.COLUMN_PLOT},
                Movie.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare Movie object
        Movie movie = new Movie(
                cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_CAST)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_DIRECTOR)),
                cursor.getString(cursor.getColumnIndex(Movie.COLUMN_PLOT)));

        // close the db connection
        cursor.close();

        return movie;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Movie.TABLE_NAME + " ORDER BY " +
                Movie.COLUMN_TITLE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
                movie.setCast(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_CAST)));
                movie.setCast(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_DIRECTOR)));
                movie.setCast(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_PLOT)));

                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return movies list
        return movies;
    }

    public int getMoviesCount() {
        String countQuery = "SELECT  * FROM " + Movie.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Movie.COLUMN_TITLE, movie.getTitle());

        // updating row
        return db.update(Movie.TABLE_NAME, values, Movie.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
    }

    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Movie.TABLE_NAME, Movie.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
    }
}
