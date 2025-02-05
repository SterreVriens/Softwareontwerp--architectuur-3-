package domain;

import java.util.ArrayList;

public class Order{
    private int orderNr;
    private boolean isStudentOrder;
    private ArrayList<MovieTicket> movieTickets = new ArrayList<MovieTicket>();

    public Order(int orderNr, boolean isStudentOrder){
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;
    }

    public int getOrderNr(){
        return orderNr;
    }
    public void addSeatReservation(MovieTicket movieTicket){
        movieTickets.add(movieTicket);
    }
    public double calculatePrice(){
        return 0;
    }
    public void export(TicketExportFormat exportFormat){

    }
}