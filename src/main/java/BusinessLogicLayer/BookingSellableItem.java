/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer;

import DataAccessLayer.DataHandler;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Kyle Opperman
 */
public class BookingSellableItem 
{
    private int bookingID;
    private int sellableItemID;
    private int quantity;
    private double discount;

    public int getBookingID() {
        return bookingID;
    }

    

    public int getSellableItemID() {
        return sellableItemID;
    }

    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BookingSellableItem(int bookingID, int sellableItemID, int quantity, double discount) {
        this.bookingID = bookingID;
        this.sellableItemID = sellableItemID;
        this.quantity = quantity;
        this.discount = discount;
    }
    

    public BookingSellableItem() {
    }
    
    public List<BookingSellableItem> GetBookingSellableItemsWithSelectStatement(String selectStatement) throws SQLException
    {
        DataHandler dataHandler = new DataHandler();
        List<BookingSellableItem> items = new ArrayList<BookingSellableItem>();
        ResultSet rs = dataHandler.GetQueryResultSet(selectStatement);
        if (rs.isBeforeFirst()) {
            return items;
        }
        else{
            while (rs.next()) {
                items.add(new BookingSellableItem(rs.getInt("BookingID"),rs.getInt("SellableItemID"),rs.getInt("Quantity"),rs.getDouble("Discount")));
            }
            return items; 
        }
        
    }
    
    
    public List<BookingSellableItem> GetBookingSellableItems() throws SQLException
    {      
        return GetBookingSellableItemsWithSelectStatement("SELECT * FROM BookingSellableItem");
    }
    
    public List<BookingSellableItem> GetBookingSellableItemsByBookingID(String bookingID) throws SQLException
    {      
        return GetBookingSellableItemsWithSelectStatement("SELECT * FROM BookingSellableItem WHERE BookingID="+bookingID);
    }
    
    public List<BookingSellableItem> GetBookingSellableItemsBySellableItemID(String sellableItemID) throws SQLException
    {      
        return GetBookingSellableItemsWithSelectStatement("SELECT * FROM BookingSellableItem WHERE SellableItemID="+sellableItemID);
    }
    
    public boolean AddBookingItemLinkToDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the SellableItemID
        String insertStatement = String.format("INSERT INTO BookingSellableItem (BookingID,SellableItemID,Quantity,Discount) VALUES(%d, %d, %d, %f)", bookingID, sellableItemID, quantity, discount);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            System.out.println("Could not perform row insertion");
            return false;
        }
    }
    
    public boolean UpdateBookingItemLinkInDataBase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String updateStatement = String.format("UPDATE BookingSellableItem SET Quantity=%d, Discount=%d WHERE BookingID=%d AND SellableItemID=%d)", quantity, discount, bookingID, sellableItemID);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(updateStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            System.out.println("Could not perform row update");
            return false;
        }
        
    }
    
    public boolean DeleteInDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        List<BookingSellableItem> sellableItemsToDelete = GetBookingSellableItemsWithSelectStatement(String.format("SELECT * FROM BookingSellableItem WHERE BookingID=%d AND SellableItemID=%d",bookingID,sellableItemID));
        if (!sellableItemsToDelete.isEmpty()) {
            String deleteStatement = String.format("DELETE FROM BookingSellableItem WHERE BookingID=%d AND SellableItemID=%d)", bookingID, sellableItemID);
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
            System.out.printf("No rows to delete with BookingID of %s and SellableItemID of %s", bookingID, sellableItemID);
            return false;
        }
    
    
    } 
    
    
}
