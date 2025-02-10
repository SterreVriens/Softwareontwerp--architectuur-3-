package domain;

import java.io.FileWriter;

public class ExportJson implements ExportBehavior {
    public void export(String exportPath, Order order) throws Exception {
        String json = "{\n";
        json += "  \"OrderNr\": " + order.getOrderNr() + ",\n";
        json += "  \"IsStudentOrder\": " + order.isStudentOrder() + ",\n";

        // MovieTickets array
        json += "  \"MovieTickets\": [\n";
        for (int i = 0; i < order.getMovieTickets().size(); i++) {
            json += order.getMovieTickets().get(i).toJSONString();

            if (i < order.getMovieTickets().size() - 1) {
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
