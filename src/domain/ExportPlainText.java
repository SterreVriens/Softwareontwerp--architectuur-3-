package domain;

import java.io.FileWriter;
import java.io.IOException;

public class ExportPlainText  implements ExportBehavior {
    public void export(String exportPath, Order order) throws IOException {
        String text = "";
        text += "Order nr: " + order.getOrderNr() + "\n";
        text += "Is student order: " + order.isStudentOrder() + "\n";
        for (MovieTicket movieTicket : order.getMovieTickets()) {
            text += movieTicket.toString() + "\n";
        }

        // Write to txt file
        try (FileWriter writer = new FileWriter(exportPath + ".txt")) {
            writer.write(text);
        }
    }
    
}
