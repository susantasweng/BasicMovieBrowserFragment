package quiz.android.bits.com.moviesearchfr.fragments;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import quiz.android.bits.com.moviesearchfr.R;

public class MovieInformationFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private ImageView movieBackground;
    private TextView titleView;
    private TextView plotView;
    private TextView castView;
    private TextView directorView;
    private int screenOrientation;

    private String movieName;
    private String movieCast;
    private String movieDirector;
    private String moviePlot;
    private String movieImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movieName = getArguments().getString(MovieListFragment.MOVIE_TITLE_URI);
            movieCast = getArguments().getString(MovieListFragment.MOVIE_CAST_URI);
            movieDirector = getArguments().getString(MovieListFragment.MOVIE_DIRECTOR_URI);
            moviePlot = getArguments().getString(MovieListFragment.MOVIE_PLOT_URI);
            movieImage = getArguments().getString(MovieListFragment.MOVIE_IMAGE_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_info_fragment, container, false);
        screenOrientation = getResources().getConfiguration().orientation;

        appBarLayout = view.findViewById(R.id.info_screen_appbar);
        titleView = view.findViewById(R.id.movie_info_title_textview);
        plotView = view.findViewById(R.id.movie_plot_textview);
        castView = view.findViewById(R.id.movie_cast_textview);
        directorView = view.findViewById(R.id.movie_director_textview);

        if(screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            appBarLayout = view.findViewById(R.id.info_screen_appbar);
            updatePortraitViews();
        } else {
            movieBackground = view.findViewById(R.id.movie_image);
            updateLandscapeView();
        }

        updateGeneralViews();

        return view;
    }

    private void updateGeneralViews() {
        titleView.setText(movieName);
        plotView.setText(moviePlot);
        castView.setText(movieCast);
        directorView.setText(movieDirector);
    }

    private void updatePortraitViews() {
        try {
            appBarLayout.setBackground(Drawable.createFromStream(getActivity().getAssets().open(movieImage), null));
        } catch (IOException e) {
            Log.e("MovieInfo", "IOException: Failed to load the movie image from asset");
            e.printStackTrace();
        }

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
        AppBarLayout.Behavior layoutBehaviour = new AppBarLayout.Behavior();
        appBarLayoutParams.setBehavior(layoutBehaviour);
        layoutBehaviour.setDragCallback(new AppBarLayout.Behavior.BaseDragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
    }

    private void updateLandscapeView() {
        try {
            movieBackground.setBackground(Drawable.createFromStream(getActivity().getAssets().open(movieImage), null));
        } catch (IOException e) {
            Log.e("MovieInfo", "IOException: Failed to load the movie image from asset");
            e.printStackTrace();
        }
    }
}
