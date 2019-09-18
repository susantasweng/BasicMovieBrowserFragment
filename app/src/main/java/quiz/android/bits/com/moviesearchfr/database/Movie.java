package quiz.android.bits.com.moviesearchfr.database;

public class Movie {
    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CAST = "cast";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_PLOT = "plot";

    private int id;
    private String title;
    private String cast;
    private String director;
    private String plot;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_CAST + " TEXT,"
                    + COLUMN_DIRECTOR + " TEXT,"
                    + COLUMN_PLOT + " TEXT"
                    + ")";

    public Movie() {
    }

    public Movie(int id, String title, String cast, String director, String plot) {
        this.id = id;
        this.title = title;
        this.cast = cast;
        this.director = director;
        this.plot = plot;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public String getPlot() {
        return plot;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
