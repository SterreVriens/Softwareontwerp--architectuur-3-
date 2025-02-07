package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Order {
    private int orderNr;
    private boolean isStudentOrder;
    private ArrayList<MovieTicket> movieTickets = new ArrayList<MovieTicket>();

    private String exportPath = "exports/order" + orderNr;

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;
    }

    public int getOrderNr() {
        return orderNr;
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
    
    private boolean isWeekend(MovieTicket movieTicket){
        DayOfWeek day = movieTickets.get(0).getMovieScreening().getDateTime().getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || day == DayOfWeek.FRIDAY;
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

    public void export(TicketExportFormat exportFormat) {
        try {
            switch (exportFormat) {
                case PLAINTEXT:
                    exportToPlainText(exportPath);
                    break;
                case JSON:
                    exportToJson(exportPath);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportToPlainText(String exportPath) throws IOException {
        String text = "";
        text += "Order nr: " + orderNr + "\n";
        text += "Is student order: " + isStudentOrder + "\n";
        for (MovieTicket movieTicket : movieTickets) {
            text += movieTicket.toString() + "\n";
        }

        // Write to txt file
        try (FileWriter writer = new FileWriter(exportPath + ".txt")) {
            writer.write(text);
        }
    }

    private void exportToJson(String exportPath) throws IOException {
        String json = "{\n";
        json += "  \"OrderNr\": " + orderNr + ",\n";
        json += "  \"IsStudentOrder\": " + isStudentOrder + ",\n";

        // MovieTickets array
        json += "  \"MovieTickets\": [\n";
        for (int i = 0; i < movieTickets.size(); i++) {
            json += movieTickets.get(i).toJSONString();

            if (i < movieTickets.size() - 1) {
                json += ",\n";
            } else {
                json += "\n";
            }
        }
        json += "  ]\n"; // End of MovieTickets array

        json += "}\n";

        //Write to json file
        try (FileWriter writer = new FileWriter(exportPath + ".json")) {
            writer.write(json);
        }
    }
}