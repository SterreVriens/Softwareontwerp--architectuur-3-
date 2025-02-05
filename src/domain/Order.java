package domain;

import java.io.FileWriter;
import java.util.ArrayList;
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
        return 0;
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