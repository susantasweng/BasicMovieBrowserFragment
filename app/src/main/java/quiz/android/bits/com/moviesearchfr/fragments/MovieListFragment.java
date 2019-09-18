package quiz.android.bits.com.moviesearchfr.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import quiz.android.bits.com.moviesearchfr.R;
import quiz.android.bits.com.moviesearchfr.adapters.MovieListAdapter;
import quiz.android.bits.com.moviesearchfr.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MovieListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String TAG = MovieListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TextView settingsTextView;

    private Utils utils;

    private String[] movieNamesList;
    private String[] moviePlotsList;
    private String[] movieCastsList;
    private String[] movieDirectorsList;
    private ListView movieList;


    private int screenOrientation;
    private boolean mBind;

    private AppBarLayout appBarLayout;
    private TextView titleView;
    private TextView plotView;
    private Bundle listItemBundle;

    public static String LIST_POSITION = "quiz.android.bits.com.movieassignment.list_position";
    public static String MOVIE_PLOT_URI = "quiz.android.bits.com.movieassignment.movie_plot";
    public static String MOVIE_TITLE_URI = "quiz.android.bits.com.movieassignment.movie_title";
    public static String MOVIE_IMAGE_URI = "quiz.android.bits.com.movieassignment.movie_poster_uri";
    public static String MOVIE_CAST_URI = "quiz.android.bits.com.movieassignment.movie_cast_uri";
    public static String MOVIE_DIRECTOR_URI = "quiz.android.bits.com.movieassignment.movie_director_uri";

    private HashMap<String, String> moviePlotHashMap = new HashMap<>();
    private HashMap<String, String> movieCastsHashMap = new HashMap<>();
    private HashMap<String, String> movieDirectorsHashMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new Utils();

        movieNamesList = getResources().getStringArray(R.array.movie_list_data_feed);
        moviePlotsList = getResources().getStringArray(R.array.movie_plots_data_feed);
        movieCastsList = getResources().getStringArray(R.array.movie_cast_list_data_feed);
        movieDirectorsList = getResources().getStringArray(R.array.movie_director_list_data_feed);

        moviePlotHashMap = utils.getMoviePlotMap(moviePlotsList);
        movieCastsHashMap = utils.getMovieCastsMap(movieCastsList);
        movieDirectorsHashMap = utils.getMovieDirectorsMap(movieDirectorsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list_fragment, container, false);

        movieList = view.findViewById(R.id.list_view);
        MovieListAdapter listAdapter = new MovieListAdapter(getActivity(), movieNamesList);
        movieList.setAdapter(listAdapter);
        movieList.setOnItemClickListener(this);

        screenOrientation = this.getResources().getConfiguration().orientation;

        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(LAST_SELECTED_LIST_INDEX);
        }

        updateOrientionViews(view, selectedIndex);

        return view;
    }

    private final String LAST_SELECTED_LIST_INDEX = "LAST_SELECTED_LIST_INDEX";
    private int selectedIndex;

    private void updateOrientionViews(View view, int lastSelectedIndex) {
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            appBarLayout = view.findViewById(R.id.info_screen_appbar);
            titleView = view.findViewById(R.id.movie_info_title_textview);
            plotView = view.findViewById(R.id.movie_plot_textview);

            updateMovieDetails(lastSelectedIndex);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick in t" + position);

        Bundle movieBundle = getMovieItemDetails(position);

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mListener != null) {
                mListener.onFragmentInteraction(movieBundle);
            }
        } else if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateMovieDetails(position);
        }

        selectedIndex = position;
    }

    private void updateMovieDetails(int position) {
        try {
            appBarLayout.setBackground(Drawable.createFromStream(getActivity().getAssets().open(utils.getMovieIdFromName(movieNamesList[position])+".jpeg"),
                    null));
        } catch (IOException e) {
            Log.e("MovieInfo", "IOException: Failed to load the movie image from asset");
            e.printStackTrace();
        }
        titleView.setText(movieNamesList[position]);
        plotView.setText(moviePlotsList[position]);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(LAST_SELECTED_LIST_INDEX, selectedIndex);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Bundle movieBundle);
    }

    private Bundle getMovieItemDetails(int position) {
        String movie = movieNamesList[position];
        String movie_id = utils.getMovieIdFromName(movie);
        Bundle bundle = new Bundle();
        bundle.putInt(LIST_POSITION, position);
        bundle.putString(MOVIE_TITLE_URI, movie);
        bundle.putString(MOVIE_PLOT_URI, moviePlotHashMap.get(movie_id));
        bundle.putString(MOVIE_CAST_URI, movieCastsHashMap.get(movie_id));
        bundle.putString(MOVIE_DIRECTOR_URI, movieDirectorsHashMap.get(movie_id));
        bundle.putString(MOVIE_IMAGE_URI, movie_id + ".jpeg");
        return bundle;
    }
}
