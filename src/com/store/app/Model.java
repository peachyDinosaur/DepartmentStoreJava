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
    StoreTableGateway storeGateway;
    private List<Region> regions;
    RegionTableGateway regionGateway;    
    private Model() {
//trys to make a connection to the db if not sql exception
        try {
            Connection conn = DBConnection.getInstance();
            this.storeGateway = new StoreTableGateway(conn);
            this.regionGateway = new RegionTableGateway(conn);
            
            this.stores = this.storeGateway.getStores();
            this.regions = this.regionGateway.getRegions();
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

            int storeId = this.storeGateway.insertStore(
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
            removed = this.storeGateway.removeStore(s.getstoreId());
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
            updated = this.storeGateway.updateStore(s);

        }
         catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }


    public List<Region> getRegions() {
        return this.regions;
    }

    public boolean addRegion(Region r) throws SQLException {
        boolean result = false;

            int regionId = this.regionGateway.insertRegion(
                r.getregion(),                    
                r.getmanagerName(),
                r.getphoneNumber(),
                r.getemail());
            
            if(regionId != -1){
               r.setregionId(regionId);
               this.regions.add(r);
               result = true;
            }


        return result;
    }

    
    public boolean removeRegion(Region r) {
        boolean removed = false;
        try{
            removed = this.regionGateway.removeRegion(r.getregionId());
            if(removed){
                removed = this.regions.remove(r);
            }
        }
         catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }    
       

    Region findRegionById(int regionId) {
        Region r = null;
        int i = 0;
        boolean found = false;
        while (i < this.regions.size() && !found) {
            r = this.regions.get(i);
            if (r.getregionId() == (regionId)) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            r = null;
        }
        return r;
    }

    boolean updateRegion(Region r) {
        boolean updated = false;
        
        try{
            updated = this.regionGateway.updateRegion(r);

        }
         catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }


}