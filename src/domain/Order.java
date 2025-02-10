package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Order {
    private int orderNr;
    private boolean isStudentOrder;
    private ArrayList<MovieTicket> movieTickets = new ArrayList<MovieTicket>();
    private ExportBehavior exportBehavior;

    private String exportPath = "exports/order" + orderNr;

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public boolean isStudentOrder() {
        return isStudentOrder;
    }

    public ArrayList<MovieTicket> getMovieTickets() {
        return movieTickets;
    }

    public void addSeatReservation(MovieTicket movieTicket) {
        movieTickets.add(movieTicket);
    }

    public double calculatePrice() {
        if (movieTickets.isEmpty()) return 0;
    
        boolean isWeekend = isWeekend(movieTickets.get(0));
        double totalPrice = calculateBasePrice(isWeekend);
        
        return applyDiscount(totalPrice, isWeekend);
    }

    private double calculateBasePrice(boolean isWeekend) {
        double total = 0;
        for (int i = 0; i < movieTickets.size(); i++) {
            total += calculateTicketPrice(movieTickets.get(i), i, isWeekend);
        }
        return total;
    }

    private double calculateTicketPrice(MovieTicket ticket, int index, boolean isWeekend) {
        double basePrice = ticket.getprice();
        double premiumExtra = isStudentOrder ? 2 : 3;
    
        if (index % 2 == 1 && (isStudentOrder || !isWeekend)) {
            return 0; // Gratis ticket
        }
    
        return basePrice + (ticket.isPremium() ? premiumExtra : 0);
    }

    private double applyDiscount(double totalPrice, boolean isWeekend) {
        if (!isStudentOrder && movieTickets.size() >= 6 && isWeekend) {
            return totalPrice * 0.9; // 10% korting
        }
        return totalPrice;
    }   
    
    private boolean isWeekend(MovieTicket movieTicket){
        DayOfWeek day = movieTickets.get(0).getMovieScreening().getDateTime().getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || day == DayOfWeek.FRIDAY;
    }

    public void export(TicketExportFormat exportFormat) throws Exception {
        try {
            switch (exportFormat) {
                case PLAINTEXT:
                    exportBehavior = new ExportPlainText();
                    exportBehavior.export(exportPath, this);
                    break;
                case JSON:
                    exportBehavior = new ExportJson();
                    exportBehavior.export(exportPath, this);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}