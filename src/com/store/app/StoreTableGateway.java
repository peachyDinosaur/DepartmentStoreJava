package com.store.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StoreTableGateway {
    private static final String TABLE_NAME = "stores";
    private static final String COLUMN_STOREID = "storeId";
    private static final String COLUMN_REGIONID = "regionId";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_MANAGER = "manager";
    private static final String COLUMN_PHONENUMBER = "phoneNumber";
//  final veriables of columns which have 1 isntance in class
    private Connection mConnection;

    public StoreTableGateway(Connection connection) {
        mConnection = connection;
    }
    
    public int insertStore(String a, String m, int pN) throws SQLException {
        int storeId = -1;


        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        String query;       // the SQL query to execute
        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_ADDRESS + ", " +
                COLUMN_MANAGER + ", " +
                COLUMN_PHONENUMBER +                
                ") VALUES (?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, a);
        stmt.setString(2, m);
        stmt.setInt(3, pN);


        //  execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1){
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            storeId = keys.getInt(1);
        }
         return storeId;
    }   
    
    
    
    
    
    public List<Store> getStores() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
                            // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of
                            // SQL query 
        List<Store> stores;   // the java.util.List containing the Store objects
                            // created for each row in the result of the query
        int storeId, regionId;             // the id of a store
        String address, manager;
        int phoneNumber;
        Store s;       // a Store object created from a row in the result of
                            // the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Store object, which is inserted into an initially
        // empty ArrayList
        stores = new ArrayList<Store>();
        while (rs.next()) {
            storeId = rs.getInt(COLUMN_STOREID);
            regionId = rs.getInt(COLUMN_REGIONID);
            address = rs.getString(COLUMN_ADDRESS);
            manager = rs.getString(COLUMN_MANAGER);            
            phoneNumber = rs.getInt(COLUMN_PHONENUMBER);

            s = new Store(storeId, regionId, address, manager, phoneNumber);
            stores.add(s);
        }

        return stores;
    }
     public boolean removeStore(int storeId)throws SQLException{
         
        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "DELETE FROM " + TABLE_NAME +" WHERE " + COLUMN_STOREID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, storeId);
        
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected ==1);
     }    

    boolean updateStore(Store s) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_ADDRESS      + " =?, " +
                COLUMN_MANAGER    + " =?, " +
                COLUMN_PHONENUMBER     +  " =? " +
                " WHERE " + COLUMN_STOREID + " =?";
        
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, s.getaddress());
        stmt.setString(2, s.getmanager());
        stmt.setInt(3, s.getphoneNumber());
        stmt.setInt(4, s.getstoreId());
        
        numRowsAffected = stmt.executeUpdate();        

        return (numRowsAffected ==1);        
    }
}