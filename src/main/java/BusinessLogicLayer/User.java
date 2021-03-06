/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.*;


import DataAccessLayer.DataHandler;



/**
 *
 * @author Christopher
 */
public class User 
{
    //private fields
    private int userID;
    private String userName;
    private String password;
    private boolean admin;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String cellNum;
    private String email;
    private String nationalID;
    
    //Properties
    public int getUserID() {
        return userID;
    }
    
    //UserID cannot be set, at it will be determined by the autonumber function in the database

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    
    public String getFirstName(){
        return firstName;
    }  
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCellNum() {
        return cellNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }
    
    //Constructor with all client fields as paramenters, mainly to be used to receive values from the database
    public User(int userID, String userName, String password, boolean admin, String firstName, String lastName, Date birthDate, String cellNum, String email, String nationalID) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.admin = admin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.cellNum = cellNum;
        this.email = email;
        this.nationalID = nationalID;
    }
    
    
    
    //Constructor with paramenters, but without userID
    public User(String userName, String password, boolean admin, String firstName, String lastName, Date birthDate, String cellNum, String email, String nationalID) {
        this.userName = userName;
        this.password = password;
        this.admin = admin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.cellNum = cellNum;
        this.email = email;
        this.nationalID = nationalID;
    }
    
    //This overload allows the creation of the object using LocalDate, as input parameter instead of Date. LocalDate is more reliable and easier to work with than date
    public User(String userName, String password, boolean admin, String firstName, String lastName, LocalDate birthDate, String cellNum, String email, String nationalID) {
        this.userName = userName;
        this.password = password;
        this.admin = admin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.cellNum = cellNum;
        this.email = email;
        this.nationalID = nationalID;
    }
    
    
    //Constructor without parameters
    public User() {
    }
    
    
    //Interaction with Data Access Layer starts here, can potentially create a linking or ad-hoc class for this functionality

    public List<User> GetUsersWithSelectStatement(String selectStatement) throws SQLException
    {
        DataHandler dataHandler = new DataHandler();
        List<User> users = new ArrayList<User>();
        ResultSet rs = dataHandler.GetQueryResultSet(selectStatement);
        if (!rs.isBeforeFirst()) {
            return users;
        }
        else{
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"),rs.getString("UserName"),rs.getString("Password"),rs.getBoolean("BirthDate"),rs.getString("FirstName"),rs.getString("LastName"),rs.getDate("BirthDate"),rs.getString("CellNum"),rs.getString("Email"),rs.getString("NationalID")));
            }
            return users; 
        }
        
    }
    
    public List<User> GetUsers() throws SQLException
    {      
        return GetUsersWithSelectStatement("SELECT * FROM User");
    }
    
    
    public User GetUserByID(int userID) throws SQLException{
        List<User> usersReturned =  GetUsersWithSelectStatement("SELECT * FROM User WHERE UserID="+userID);
        if (usersReturned.isEmpty()) {
            return  null; 
        }
        else{
           return usersReturned.get(0); //Getting first user that matches criteria that is expected to be unique
        } 
    }
    
    public User GetUserByUserNameAndPassword(String userName, String password) throws SQLException{
        List<User> usersReturned =  GetUsersWithSelectStatement(String.format("SELECT * FROM User WHERE UserName=%s AND Password=%s",userName,password));
        if (usersReturned.isEmpty()) {
            return  null; 
        }
        else{
           return usersReturned.get(0); //Getting first user that matches criteria that is expected to be unique
        } 
    }
    
    
    //if added successfully to database, true will be returned
    public boolean AddToDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String insertStatement = String.format("INSERT INTO User (UserName,Password,Admin,FirstName,LastName,BirthDate,CellNum,Email,NationalID) VALUES('%s','%s',%b,'%s','%s','%tF','%s','%s','%s')", userName, password, admin, firstName, lastName, birthDate, cellNum, email, nationalID);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            System.out.println("Could not perform row insertion");
            return false;
        }
    }
    
    
    public boolean TryAddToDatabase() throws SQLException{
        if (IsUserNamePasswordUnique()) {
            return AddToDatabase();
        }
        else{
            System.out.println("Could not perform row insertion");
            return false;
        }
    }
    
    
    private boolean UpdateInDataBase(){
        DataHandler dataHandler = new DataHandler();
        //Start from UserName since the database automatically increments the ClientID
        String insertStatement = String.format("UPDATE User SET UserName='%s',Password='%s',Admin=%b,FirstName='%s',LastName='%s',BirthDate='%tF',CellNum='%s',Email='%s',NationalID='%s' WHERE UserID=%d)", userName, password, admin, firstName, lastName, birthDate, cellNum, email, nationalID, userID);
        boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
        if (querySuccessful) {
           return true; 
        }
        else{
            return false;
        }
        
    }
    
    public boolean TryUpdateInDatabase() throws SQLException{
        List<User> usersWithPasswordAndUserName = GetUsersWithSelectStatement(String.format("SELECT * FROM User WHERE UserName='%s' AND Password='%s'",userName,password));
        if (usersWithPasswordAndUserName.size()==0){
            return UpdateInDataBase();
        }
        else if(usersWithPasswordAndUserName.size()==1){
            if (usersWithPasswordAndUserName.get(0).getUserID()==userID) {
                return UpdateInDataBase();
            }
            else{
                System.out.println("Could not perform update on row");
                return false;
            }
        }
        else{
            System.out.println("Could not perform update on row");
            return false;
        }
    }
    
    
    
    public boolean DeleteInDatabase() throws SQLException{
        DataHandler dataHandler = new DataHandler();
        List<User> usersWithUserID = GetUsersWithSelectStatement(String.format("SELECT * FROM User WHERE UserID=%d",userID));
        if (usersWithUserID.size()!=0) {
            String insertStatement = String.format("DELETE FROM User WHERE UserID=%d)", userID);
            boolean querySuccessful = dataHandler.ExecuteNonQuery(insertStatement);
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
            System.out.println("No rows to delete with UserID of"+userID);
            return false;
        }
  
    }
    
    public boolean IsUserNamePasswordUnique() throws SQLException{
        DataHandler dataHandler = new DataHandler();     
        ResultSet rs = dataHandler.GetQueryResultSet(String.format("SELECT * FROM User WHERE UserName='%s' AND Password='%s'",userName,password));
        if (!rs.isBeforeFirst()) { //isbefore first returns false if there are no rows in the resultset
            
            return true;
        }
        else{
            System.out.println("User name and password combination is not unique in the database");
            return false; 
        }   
    }
  
}
  
