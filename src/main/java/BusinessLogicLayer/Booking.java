/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer;
import java.util.*;
import java.time.*;    
import java.time.format.DateTimeFormatter; 
import BusinessLogicLayer.*;
import java.text.SimpleDateFormat;
/**
 *
 * @author Christopher
 */
public class Booking 
{
    private int bookingID;
    private int userID;
    private double budget;
    private String type;
    private String theme;
    private String comments;
    private Date eventDate;
    private String venueAddress;
    private String venueRegion;
    private String venuecity;
    private int numAdults;
    private int numKids;
    private int bookingNum;
    private double transportCost;
    private double labourCost;
    private boolean cancelled;
    
    //alt + insert

    public int getBookingID() {
        return bookingID;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueRegion() {
        return venueRegion;
    }

    public void setVenueRegion(String venueRegion) {
        this.venueRegion = venueRegion;
    }

    public String getVenuecity() {
        return venuecity;
    }

    public void setVenuecity(String venuecity) {
        this.venuecity = venuecity;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public int getNumKids() {
        return numKids;
    }

    public void setNumKids(int numKids) {
        this.numKids = numKids;
    }

    public int getBookingNum() {
        return bookingNum;
    }

    public void setBookingNum(int bookingNum) {
        this.bookingNum = bookingNum;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(double labourCost) {
        this.labourCost = labourCost;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    //ctrl + spacebar

    public Booking(int bookingID, int userID, double budget, String type, String theme, String comments, Date eventDate, String venueAddress, String venueRegion, String venuecity, int numAdults, int numKids, int bookingNum, double transportCost, double labourCost, boolean cancelled) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.budget = budget;
        this.type = type;
        this.theme = theme;
        this.comments = comments;
        this.eventDate = eventDate;
        this.venueAddress = venueAddress;
        this.venueRegion = venueRegion;
        this.venuecity = venuecity;
        this.numAdults = numAdults;
        this.numKids = numKids;
        this.bookingNum = bookingNum;
        this.transportCost = transportCost;
        this.labourCost = labourCost;
        this.cancelled = cancelled;
    }

    public Booking() {
    }
    
    public String  ValidDate()
    {
        //???????????????????
        Payment pay;
        String Date1, Date2;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());


        //====
        //HERE
        //====
     /*   if ((cancelled == false) && (pay.amount() == i))
        {
            Date1 = eventDate.toString();
            Date2 = date.toString();

            System.out.println(Date2);
        }
        */
        return null;
     
    }
    
    
    
}
