/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;
/**
 *
 * @author Christopher
 */
public class DataHandler {
    private String connectionString = "jdbc:ucanaccess://DeliciousCatering.accdb";
    private Connection conn;

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    

    public DataHandler(String connectionString) {
        this.connectionString=connectionString;
    }
    
    public DataHandler() {
    }
       
    private boolean CreateConnection(){
        try {
            conn =DriverManager.getConnection(connectionString);
            System.out.println("Connection Successfull");
            return true;
           
        } catch (SQLException ex) {
            System.out.println("Connection Error:\n"+ex.getMessage());
            return false;
        }
    }
    
    private boolean CloseConnection() {
        try {
            conn.close();
            System.out.println("Connection Close Successfull");
            return true;
        } catch (SQLException ex) {
            System.out.println("Connection Close Error:\n"+ex.getMessage());
            return false;
        }
    }
    
    //---------------------------------
    //Data handler output
    //---------------------------------
    
    
    public ResultSet GetQueryResultSet(String selectStatement){
        
        boolean connectionCreated = CreateConnection();
        ResultSet result =null;
        if (connectionCreated==true) {
            try {
                Statement statement = conn.createStatement();
                result = statement.executeQuery(selectStatement);
            } catch (SQLException ex) {
                System.out.println("Error: "+ex.getMessage());
            }
            CloseConnection();
        }
        return result;             
    }
       
    //---------------------------------
    //Data handler input
    //---------------------------------
    
    //Use this method for Insert, Update or Delete SQL statements
    public boolean ExecuteNonQuery(String insertUpdateDeleteStatement){
        boolean connectionCreated = CreateConnection();
        if (connectionCreated==true) {
            Statement statement;
            try {
                statement = conn.createStatement();
                statement.executeUpdate(insertUpdateDeleteStatement);   
                System.out.println("Operation successful");
                return true;
            } catch (SQLException ex) {
                System.out.println("Error: "+ex.getMessage());
                CloseConnection();
                return false;
            }        
        }
        else{
            return false;
        }       
    }
    
    
    //---------------------------------
    //Data handler processes
    //---------------------------------   
    
    //updates
    //deletes
    //==========
    //...
    
    
        
 
    
    
    
}
