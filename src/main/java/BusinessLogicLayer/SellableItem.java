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
 * @author Christopher
 */
public class SellableItem 
{
    private int sellableItemID;
    private String sellableItemName;
    private String type;
    private String description;
    private String intendedClientele;
    private double costPrice;
    private double unitPrice;

    public int getEventItemID() {
        return sellableItemID;
    }

    public void setEventItemID(int sellableItemID) {
        this.sellableItemID = sellableItemID;
    }

    public String getSellableItemName() {
        return sellableItemName;
    }

    public void setSellableItemName(String sellableItemName) {
        this.sellableItemName = sellableItemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntendedClientele() {
        return intendedClientele;
    }

    public void setIntendedClientele(String intendedClientele) {
        this.intendedClientele = intendedClientele;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public SellableItem(int sellableItemID, String sellableItemName, String type, String description, String intendedClientele, double costPrice, double unitPrice) {
        this.sellableItemID = sellableItemID;
        this.sellableItemName = sellableItemName;
        this.type = type;
        this.description = description;
        this.intendedClientele = intendedClientele;
        this.costPrice = costPrice;
        this.unitPrice = unitPrice;
    }
    
    public SellableItem(String sellableItemName, String type, String description, String intendedClientele, double costPrice, double unitPrice) {
        this.sellableItemName = sellableItemName;
        this.type = type;
        this.description = description;
        this.intendedClientele = intendedClientele;
        this.costPrice = costPrice;
        this.unitPrice = unitPrice;
    }

    public SellableItem() {
  
    }
    
    //Interaction with Data Access Layer starts here, can potentially create a linking or ad-hoc class for this functionality

    public List<SellableItem> GetSellableItemsWithSelectStatement(String selectStatement) throws SQLException
    {
        DataHandler dataHandler = new DataHandler();
        List<SellableItem> items = new ArrayList<SellableItem>();
        ResultSet rs = dataHandler.GetQueryResultSet(selectStatement);
        if (!rs.isBeforeFirst()) {
            return items;
        }
        else{
            while (rs.next()) {
                items.add(new SellableItem(rs.getInt("SellableItemID"),rs.getString("SellableItemName"),rs.getString("Type"),rs.getString("Description"),rs.getString("IntendedClientele"),rs.getDouble("CostPrice"),rs.getDouble("UnitPrice")));
            }
            return items; 
        }
        
    }
    
    
    public List<SellableItem> GetSellableItems() throws SQLException
    {      
        return GetSellableItemsWithSelectStatement("SELECT * FROM SellableItem");
    }
    
    
    public SellableItem GetSellableItemByID(int itemID) throws SQLException{
        List<SellableItem> usersReturned =  GetSellableItemsWithSelectStatement("SELECT * FROM SellableItem WHERE SellableItemID="+itemID);
        if (usersReturned.isEmpty()) {
            return  null; 
        }
        else{
           return usersReturned.get(0); //Getting first user that matches criteria that is expected to be unique
        } 
    }
    
    public List<SellableItem> GetSellableItemsByIntendedClientele(String intendedClientele) throws SQLException{
        List<SellableItem> usersReturned =  GetSellableItemsWithSelectStatement("SELECT * FROM SellableItem WHERE IntendedClientele="+intendedClientele);
        return usersReturned; 
    }
    
     public List<SellableItem> GetSellableItemsByType(String type) throws SQLException{
        List<SellableItem> usersReturned =  GetSellableItemsWithSelectStatement("SELECT * FROM SellableItem WHERE Type="+type);
        return usersReturned; 
    }
   
    
    public boolean AddToDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the SellableItemID
        String insertStatement = String.format("INSERT INTO SellableItem (SellableItemName,Type,Description,IntendedClientele,CostPrice,UnitPrice) VALUES('%s','%s','%s','%s', %f, %f)", sellableItemName, type, description, intendedClientele, costPrice, unitPrice);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            System.out.println("Could not perform row insertion");
            return false;
        }
    }
    
    public boolean UpdateInDataBase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String updateStatement = String.format("UPDATE SellableItem SET SellableItemName='%s',Type='%s',Description='%s',IntendedClientele='%s',CostPrice=%f,UnitPrice=%f WHERE SellableItemID=%d)", sellableItemName, type, description, intendedClientele, costPrice, unitPrice,sellableItemID);
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
        List<SellableItem> sellableItemsToDelete = GetSellableItemsWithSelectStatement(String.format("SELECT * FROM SellableItem WHERE SellableItemID=%d",sellableItemID));
        if (!sellableItemsToDelete.isEmpty()) {
            String deleteStatement = String.format("DELETE FROM SellableItem WHERE SellableItemID=%d)", sellableItemID);
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
            System.out.println("No rows to delete with SellableItemID of"+sellableItemID);
            return false;
        }
  
    }
   
   
   
}
