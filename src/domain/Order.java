package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

import behaviours.*;
import interfaces.IFreeTicketBehaviour;
import interfaces.IGroupDiscountBehaviour;
import interfaces.IPremiumBehaviour;

import java.io.FileWriter;
import java.io.IOException;

public class Order {
    private int orderNr;
    private boolean isStudentOrder;
    private ArrayList<MovieTicket> movieTickets = new ArrayList<MovieTicket>();
    private ExportBehavior exportBehavior;
    private IFreeTicketBehaviour freeTicketBehaviour;
    private IGroupDiscountBehaviour groupDiscountBehaviour;
    private IPremiumBehaviour premiumBehaviour;

    private String exportPath = "exports/order" + orderNr;

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        if (isStudentOrder) {
            freeTicketBehaviour = isStudentOrder ? new StudentFree() : new NonStudentFree();
            groupDiscountBehaviour = new NoGroupDiscount();
            premiumBehaviour = new StudentPremium();
        } else {
            freeTicketBehaviour = new NonStudentFree();
            groupDiscountBehaviour = new SmallGroupDiscount();
            premiumBehaviour = new NonStudentPremium();
        }
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
        int totalPrice = 0;
        DayOfWeek day = movieTickets.get(0).getMovieScreening().getDateTime().getDayOfWeek();
        for (int i = 0; i < movieTickets.size(); i++) {
            // if free continue
            if (freeTicketBehaviour.IsFree(i, day)) {
                continue;
            }
            double basePrice = movieTickets.get(i).getprice();
            double premiumExtra = premiumBehaviour.CalculateExtraPrice(movieTickets.get(i).isPremium());
            totalPrice += (basePrice + premiumExtra)
                    * groupDiscountBehaviour.CalculateGroupDiscountMultiplier(movieTickets.size());
        }
        return totalPrice;
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