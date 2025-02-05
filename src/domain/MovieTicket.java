package domain;

public class MovieTicket {
    private int rowNr;
    private int seatNr;
    private boolean isPremium;
    private MovieScreening movieScreening;

    public MovieTicket(int rowNr, int seatNr, boolean isPremium, MovieScreening movieScreening) {
        this.rowNr = rowNr;
        this.seatNr = seatNr;
        this.isPremium = isPremium;
        this.movieScreening = movieScreening;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public double getprice() {
        return 13;
    }

    public MovieScreening getMovieScreening() {
        return movieScreening;
    }

    @Override
    public String toString() {
        return "Row: " + rowNr + ", Seat: " + seatNr + ", Premium: " + isPremium;
    }
}
