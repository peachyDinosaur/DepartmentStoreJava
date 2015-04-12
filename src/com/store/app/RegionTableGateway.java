/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegionTableGateway {
    private static final String TABLE_NAME = "region";
    private static final String COLUMN_REGIONID = "regionId";
    private static final String COLUMN_REGION = "region";
    private static final String COLUMN_MANAGERNAME = "managerName";
    private static final String COLUMN_PHONENUMBER = "phoneNumber";
    private static final String COLUMN_EMAIL = "email";
//  final veriables of columns which have 1 isntance in class
    private Connection mConnection;

    public RegionTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    public int insertRegion(String region, String managerName, int phoneNumber, String email) throws SQLException {
        int regionId = -1;


        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        String query;       // the SQL query to execute
        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_REGION + ", " +
                COLUMN_MANAGERNAME + ", " +
                COLUMN_PHONENUMBER + ", " +
                COLUMN_EMAIL +                
                ") VALUES (?, ?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, region);
        stmt.setString(2, managerName);
        stmt.setInt(3, phoneNumber);
        stmt.setString(4, email);


        //  execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1){
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            regionId = keys.getInt(1);
        }
         return regionId;
    }   
    
    
    
    
    
    public List<Region> getRegions() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
                            // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of
                            // SQL query 
        List<Region> regions;   // the java.util.List containing the Region objects
                            // created for each row in the result of the query
        int regionId;             // the id of a store
        String region, managerName, email;
        int phoneNumber;
        Region r;       // a Region object created from a row in the result of
                            // the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Region object, which is inserted into an initially
        // empty ArrayList
        regions = new ArrayList<Region>();
        while (rs.next()) {
            regionId = rs.getInt(COLUMN_REGIONID);
            region = rs.getString(COLUMN_REGION);  
            managerName = rs.getString(COLUMN_MANAGERNAME);            
            phoneNumber = rs.getInt(COLUMN_PHONENUMBER);
            email = rs.getString(COLUMN_EMAIL);

            r = new Region(regionId, region, managerName, phoneNumber, email);
            regions.add(r);
        }

        return regions;
    }
     public boolean removeRegion(int regionId)throws SQLException{
         
        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "DELETE FROM " + TABLE_NAME +" WHERE " + COLUMN_REGIONID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, regionId);
        
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected ==1);
     }    

    boolean updateRegion(Region r) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_REGION           + " =?, " +
                COLUMN_MANAGERNAME      + " =?, " +
                COLUMN_PHONENUMBER      +  " =?, " +
                COLUMN_EMAIL            +  " =? " +
                " WHERE " + COLUMN_REGIONID + " =?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setString  (1, r.getregion());
        stmt.setString  (2, r.getmanagerName());
        stmt.setInt     (3, r.getphoneNumber());
        stmt.setString  (4, r.getemail());
        stmt.setInt     (5, r.getregionId());
        
        numRowsAffected = stmt.executeUpdate();        

        return (numRowsAffected ==1);         
    }
}