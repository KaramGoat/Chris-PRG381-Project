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
import DataAccessLayer.DataHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public Booking(int userID, double budget, String type, String theme, String comments, Date eventDate, String venueAddress, String venueRegion, String venuecity, int numAdults, int numKids, int bookingNum, double transportCost, double labourCost, boolean cancelled) {
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
    
    public List<Booking> GetBookingsWithSelectStatement(String selectStatement) throws SQLException
    {
        DataHandler dataHandler = new DataHandler();
        List<Booking> bookings = new ArrayList<Booking>();
        ResultSet rs = dataHandler.GetQueryResultSet(selectStatement);
        //System.out.println("Check if is before fisrt...");
        
        //rs.beforeFirst();
        if (!rs.isBeforeFirst()) {
            return bookings;
        }
        else{
            while (rs.next()) {
                bookings.add(new Booking(rs.getInt("BookingID"),rs.getInt("UserID"),rs.getDouble("Budget"),rs.getString("Type"),rs.getString("Theme"),rs.getString("Comments"),rs.getDate("EventDate"),rs.getString("VenueAddress"),rs.getString("VenueRegion"),rs.getString("VenueCity"),rs.getInt("NumAdults"),rs.getInt("NumKids"),rs.getInt("BookingNum"),rs.getDouble("TransportPrice"),rs.getDouble("LabourPrice"),rs.getBoolean("Cancelled")));
                //System.out.println("Added User");
            }
            return bookings; 
        }      
    }
    
    public List<Booking> GetBookings() throws SQLException
    {      
        return GetBookingsWithSelectStatement("SELECT * FROM Booking");
    }
    
    public List<Booking> GetBookingsByUserID(int userID) throws SQLException{
        return  GetBookingsWithSelectStatement("SELECT * FROM Booking WHERE UserID="+userID);  
    }
    
    public List<Booking> GetConfirmedBookings() throws SQLException{       
        List<Booking> allBookings = GetBookings();
        List<Booking> confirmedBookings = new ArrayList<Booking>();
        for (var booking:allBookings) {
            if (booking.IsBookingConfirmed()) {
                confirmedBookings.add(booking);
            }
        }
        return  confirmedBookings;
    }
    
    public List<Booking> GetUnConfirmedBookings() throws SQLException{       
        List<Booking> allBookings = GetBookings();
        List<Booking> nonConfirmedBookings = new ArrayList<Booking>();
        for (var booking:allBookings) {
            if (!booking.IsBookingConfirmed()) {
                nonConfirmedBookings.add(booking);
            }
        }
        return  nonConfirmedBookings;
    }
    
    public boolean IsBookingConfirmed() throws SQLException{
        double price = GetTotalPriceThisBooking();
        double payments = GetTotalPaymentsMadeForThisBooking();
        int daysUntilEvent = GetDaysBeforeThisBooking();
        if ((payments/price>=0.5 && daysUntilEvent>=15) || (cancelled==false && daysUntilEvent<15)) {    //The booking can only beconsidered confirmed if at least half of the total price is paid 
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    public void CancelBookingInDataBase(){
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String insertStatement = String.format("UPDATE Booking SET Cancelled = %b WHERE BookingID=%d)",true, bookingID);
        dataHandler.ExecuteNonQuery(insertStatement);
        
    }
    
    public int GetDaysBeforeBooking(int bookingID) throws SQLException{
        DataHandler dataHandler = new DataHandler();
        ResultSet rs = dataHandler.GetQueryResultSet("SELECT BookingID, DATEDIFF(\"d\",Date(),EventDate) AS DaysUntil FROM Booking WHERE BookingID="+bookingID);
        rs.next();               
        return rs.getInt("DaysUntil");
    }
    
    public int GetDaysBeforeThisBooking() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        ResultSet rs = dataHandler.GetQueryResultSet("SELECT BookingID, DATEDIFF(\"d\",Date(),EventDate) AS DaysUntil FROM Booking WHERE BookingID="+bookingID);
        rs.next();               
        return rs.getInt("DaysUntil");
    }
    
    public double GetTotalPriceBooking(int bookingID) throws SQLException{
        DataHandler dataHandler = new DataHandler();
        ResultSet rs = dataHandler.GetQueryResultSet("SELECT SubTotalPrice + LabourPrice + TransportPrice AS TotalPrice\n" +
            "FROM (SELECT Booking.BookingID, Sum(SellableItem.UnitPrice*BookingSellableItem.Quantity*(1-BookingSellableItem.Discount)) AS SubTotalPrice\n" +
            "FROM SellableItem INNER JOIN ((Booking INNER JOIN BookingSellableItem ON Booking.BookingID = BookingSellableItem.BookingID) INNER JOIN Payment ON Booking.BookingID = Payment.BookingID) ON SellableItem.SellableItemID = BookingSellableItem.SellableItemID\n" +
            "GROUP BY Booking.BookingID)  AS SubTotals INNER JOIN Booking ON Booking.BookingID=SubTotals.BookingID WHERE BookingID="+bookingID);
        rs.next();               
        return rs.getDouble("TotalPrice");
    }
    
    public double GetTotalPriceThisBooking() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        ResultSet rs = dataHandler.GetQueryResultSet("SELECT SubTotalPrice + LabourPrice + TransportPrice AS TotalPrice\n" +
            "FROM (SELECT Booking.BookingID, Sum(SellableItem.UnitPrice*BookingSellableItem.Quantity*(1-BookingSellableItem.Discount)) AS SubTotalPrice\n" +
            "FROM SellableItem INNER JOIN ((Booking INNER JOIN BookingSellableItem ON Booking.BookingID = BookingSellableItem.BookingID) INNER JOIN Payment ON Booking.BookingID = Payment.BookingID) ON SellableItem.SellableItemID = BookingSellableItem.SellableItemID\n" +
            "GROUP BY Booking.BookingID)  AS SubTotals INNER JOIN Booking ON Booking.BookingID=SubTotals.BookingID WHERE BookingID="+bookingID);
        rs.next();               
        return rs.getDouble("TotalPrice");
    }
    
    
    public double GetTotalPaymentsMadeForBooking(int bookingID) throws SQLException{
        DataHandler dataHandler = new DataHandler();
        ResultSet rs = dataHandler.GetQueryResultSet("SELECT SUM(Amount) AS TotalPaid\n" +
            "FROM Booking AS b INNER JOIN Payment AS p ON b.BookingID=p.BookingID\n" +
            "WHERE b.BookingID="+bookingID+
            " GROUP BY b.BookingID");
        rs.next();               
        return rs.getDouble("TotalPaid");
    }
    
    public double GetTotalPaymentsMadeForThisBooking() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        ResultSet rs = dataHandler.GetQueryResultSet("SELECT SUM(Amount) AS TotalPaid\n" +
            "FROM Booking AS b INNER JOIN Payment AS p ON b.BookingID=p.BookingID\n" +
            "WHERE b.BookingID="+bookingID+
            " GROUP BY b.BookingID");
        rs.next();               
        return rs.getDouble("TotalPaid");
    }
    
    public boolean AddToDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String insertStatement = String.format("INSERT INTO Booking (UserID, Budget, Type, Theme, Comments, EventDate, VenueAddress, VenueRegion), VenueCity, NumAdults, NumKids, BookingNum, TransportPrice, LabourPrice, Cancelled VALUES(%d, %f,'%s','%s','%s','%tF','%s','%s','%s', %d, %d, %d, %f, %b)", userID, budget, type, theme, comments, eventDate, venueAddress, venueRegion, venuecity,numAdults,numKids,bookingNum,transportCost, labourCost, cancelled);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            System.out.println("Could not perform row insertion");
            return false;
        }
    }
    
     public boolean UpdateInDataBase(){
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String insertStatement = String.format("UPDATE Booking SET UserID= %d ,Budget= %f , Type = '%s', Theme='%s', Comments='%s', EventDate='%tF',VenueAddress='%s',VenueRegion='%s',VenueCity='%s',NumAdults='%s', NumKids='%s' , BookingNum= %d, TransportPrice= %f , LabourPrice= %f , Cancelled = %b WHERE BookingID=%d)", userID, budget, type, theme, comments, eventDate, venueAddress, venueRegion, venuecity,numAdults,numKids,bookingNum,transportCost, labourCost, cancelled, bookingID);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            return false;
        }
        
    }
     
     public boolean DeleteInDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        List<Booking> usersWithUserID = GetBookingsWithSelectStatement(String.format("SELECT * FROM Booking WHERE BookingID=%d",bookingID));
        if (usersWithUserID.size()!=0) {
            String deleteStatement = String.format("DELETE FROM Booking WHERE BookingID=%d)", bookingID);
            boolean querySuccessful = dataHandler.ExecuteNonQuery(deleteStatement);
            if (querySuccessful) {
                System.out.println("Row deletion successfull");
                return true; 
            }
            else{
                System.out.println("Could not perform row deletion");
                return false;
            }
        }
        else{
            System.out.println("No rows to delete with BookingID of"+bookingID);
            return false;
        }
  
    }

    // Kyle add code what you need
    public String  ValidDate()
    {
        //???????????????????
        /*
        Payment pay;
        String Date1, Date2;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        

        //====
        //HERE
        //====
        //if ((cancelled == false) && (pay.amount() == i))
        {
            Date1 = eventDate.toString();
            Date2 = date.toString();

            System.out.println(Date2);
        }
        */
        return null;
    }
    
    
    
}
