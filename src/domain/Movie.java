package domain;

import java.util.ArrayList;

public class Movie {
    private String title;
    private ArrayList<MovieScreening> movieScreenings = new ArrayList<MovieScreening>();

    public Movie(String title) {
        this.title = title;
    }

    public void addScreening(MovieScreening movieScreening) {
        movieScreenings.add(movieScreening);
    }

    @Override
    public String toString() {
        return title;
    }
}
