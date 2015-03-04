package com.store.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null; 
//  assign instance to null

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
//if the instance is null create a new model if its not null return the value 
    
    
//creating a list of the stores based on the databases values     
    private List<Store> stores;
    StoreTableGateway gateway;
    private Model() {
//trys to make a connection to the db if not sql exception
        try {
            Connection conn = DBConnection.getInstance();
            this.gateway = new StoreTableGateway(conn);
            
            this.stores = this.gateway.getStores();
        } 
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public List<Store> getStores() {
        return this.stores;
    }

    public boolean addStore(Store s) throws SQLException {
        boolean result = false;

            int storeId = this.gateway.insertStore(
                s.getaddress(),
                s.getmanager(),
                s.getphoneNumber());
            
            if(storeId != -1){
               s.setstoreId(storeId);
               this.stores.add(s);
               result = true;
            }


        return result;
    }

    
    public boolean removeStore(Store s) {
        boolean removed = false;
        try{
            removed = this.gateway.removeStore(s.getstoreId());
            if(removed){
                removed = this.stores.remove(s);
            }
        }
         catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }    
       

    Store findStoreById(int storeId) {
        Store s = null;
        int i = 0;
        boolean found = false;
        while (i < this.stores.size() && !found) {
            s = this.stores.get(i);
            if (s.getstoreId() == (storeId)) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            s = null;
        }
        return s;
    }

    boolean updateStore(Store s) {
        boolean updated = false;
        
        try{
            updated = this.gateway.updateStore(s);

        }
         catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }



}