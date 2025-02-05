package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MovieScreening {
    private LocalDateTime dateAndTime;
    private double pricePerSeat;
    private ArrayList<MovieTicket> movieTickets = new ArrayList<MovieTicket>();
    private Movie movie;

    public MovieScreening(LocalDateTime dateAndTime, double pricePerSeat, Movie movie) {
        this.dateAndTime = dateAndTime;
        this.pricePerSeat = pricePerSeat;
        this.movie = movie;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    @Override
    public String toString() {
        return dateAndTime.toString();
    }
}
