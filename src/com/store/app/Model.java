package com.store.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null; 
//  assign instance to null

    public static synchronized Model getInstance() throws DataAccessException {
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
    private Model() throws DataAccessException {
//trys to make a connection to the db if not sql exception
        try {
            Connection conn = DBConnection.getInstance();
            this.storeGateway = new StoreTableGateway(conn);
            this.regionGateway = new RegionTableGateway(conn);
            
            this.stores = this.storeGateway.getStores();
            this.regions = this.regionGateway.getRegions();
        } 
        catch (ClassNotFoundException | SQLException ex) {
            throw new DataAccessException("Exception initialising Model object: " + ex.getMessage());
        }
    }

   
    public List<Store> getStores() {
        return this.stores;
    }
    
    
    public List<Store> getStoresByRegionId(int regionId) { 
        List<Store> list = new ArrayList<Store>();
        for (Store s : this.stores) {
            if (s.getregionId() == regionId) {
                list.add(s);
            }
        }
        return list;
    }
    
    
    public boolean addStore(Store s) throws DataAccessException {
        boolean result = false;
        try{
            int storeId = this.storeGateway.insertStore(
                s.getregionId(),    
                s.getaddress(),
                s.getmanager(),
                s.getphoneNumber());
            
            if(storeId != -1){
               s.setstoreId(storeId);
               this.stores.add(s);
               result = true;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding store: " + ex.getMessage());
        }
        return result;
    }

    
    public boolean removeStore(Store s) throws DataAccessException {
        boolean removed = false;
        try{
            removed = this.storeGateway.removeStore(s.getstoreId());
            if(removed){
                removed = this.stores.remove(s);
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception removing a store: " + ex.getMessage());
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

    boolean updateStore(Store s) throws DataAccessException {
        boolean updated = false;
        
        try{
            updated = this.storeGateway.updateStore(s);

        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception updating store: " + ex.getMessage());
        }
        return updated;
    }


    public List<Region> getRegions() {
        return this.regions;
    }

    public boolean addRegion(Region r) throws DataAccessException {
        boolean result = false;
        try{
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
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding region: " + ex.getMessage());
        }
        return result;
    }

    
    public boolean removeRegion(Region r) throws DataAccessException {
        boolean removed = false;
        try{
            removed = this.regionGateway.removeRegion(r.getregionId());
            if(removed){
                removed = this.regions.remove(r);
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception deleting region: " + ex.getMessage());
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

    boolean updateRegion(Region r) throws DataAccessException {
        boolean updated = false;
        
        try{
            updated = this.regionGateway.updateRegion(r);

        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception updating region: " + ex.getMessage());
        }
        return updated;
    }


}