package domain;

import java.time.LocalDateTime;

public class OrderTestMain {
    public static void main(String[] args) {
        // Maak een film aan
        Movie movie = new Movie("Interstellar");

        // Maak een filmvertoning aan (datum, prijs per stoel, film)
        MovieScreening screening = new MovieScreening(LocalDateTime.of(2024, 2, 6, 19, 30), 10.00, movie);
        
        // Maak een order aan (ordernummer, isStudentOrder)
        Order studentOrder = new Order(1, true);
        Order regularOrder = new Order(2, false);

        // Voeg tickets toe (rij, stoel, premium?, screening)
        studentOrder.addSeatReservation(new MovieTicket(5, 8, false, screening));
        studentOrder.addSeatReservation(new MovieTicket(5, 9, true, screening)); // Premium ticket

        regularOrder.addSeatReservation(new MovieTicket(6, 10, false, screening));
        regularOrder.addSeatReservation(new MovieTicket(6, 11, false, screening));
        regularOrder.addSeatReservation(new MovieTicket(6, 12, true, screening)); // Premium ticket
        regularOrder.addSeatReservation(new MovieTicket(6, 13, false, screening));
        regularOrder.addSeatReservation(new MovieTicket(6, 14, true, screening));
        regularOrder.addSeatReservation(new MovieTicket(6, 15, true, screening)); // Premium ticket

        // Print de prijsberekening
        // System.out.println("Student Order Price: " + studentOrder.calculatePrice());
        System.out.println("Regular Order Price: " + regularOrder.calculatePrice());
    }
}
