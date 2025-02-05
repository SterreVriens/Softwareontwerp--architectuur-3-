package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.io.FileWriter;

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
    public double calculatePrice(){
        // isStudentOrder True = 2 ticket free, on every day of the week
        // isStudentOrder False and its monday,tuesday,wednesday,thursday = 2 ticket free
        // In weekends non students pay 100% exept if you order 6+ tickets, then you get 10% discount
        if (movieTickets.isEmpty()) return 0;

        double totalPrice = 0;
        // Check if order is for an weekend
        boolean isWeekend = isWeekend(movieTickets.get(0));

        //for loop to loop trhough all tickets
        for (int i = 0; i < movieTickets.size(); i++){
            MovieTicket ticket = movieTickets.get(i);
            double basePrice = ticket.getprice();
            //isStudentOrder True = premium price 2 else 3
            double premiumExtra = isStudentOrder ? 2 : 3;

            // If i can be divided by 2 to equal 1 and isStudentOrder is true or its not weekend, ticket is free
            if (i % 2 == 1 && (isStudentOrder || !isWeekend)){
                basePrice = 0;
                premiumExtra = 0;
            } 
            // Is ticket premium, add premiumExtra to basePrice else add none extra
            totalPrice += basePrice + (ticket.isPremium() ? premiumExtra : 0);
        }
        //If is not student and movieticket size is larger then 6 and its weekend, 10% discount
        if (!isStudentOrder && movieTickets.size() >= 6 && isWeekend(movieTickets.get(0))){
            totalPrice *= 0.9; // 10% discount
        }
        return totalPrice;
    }
    private boolean isWeekend(MovieTicket movieTicket){
        DayOfWeek day = movieTickets.get(0).getMovieScreening().getDateTime().getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || day == DayOfWeek.FRIDAY;
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