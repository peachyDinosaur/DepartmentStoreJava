/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Elias
 */
public class MainApp {
//  main metheod start     
    public static void main (String[] args){
    Scanner keyboard = new Scanner(System.in);
    Model model;
        int opt =9;
//      opt int used for switch value
//      do loop which run while opt does not equal 5
        do {
            try{
                model = Model.getInstance();
                System.out.println("1. Create new Store");
                System.out.println("2. Delete existing Store");
                System.out.println("3. View all Store");
                System.out.println("4. View a single Store");
                System.out.println("5. Edit Store");
                System.out.println();            
                System.out.println("6. Create new Region");
                System.out.println("7. Delete existing Region");
                System.out.println("8. View all Region");
                System.out.println("9. View a single Region");
                System.out.println("10. Edit Region");
                System.out.println();            
                System.out.println("11. Exit");
                System.out.println();

                opt = getInt(keyboard, "Enter option: ", 11);

                System.out.println("You chose option " + opt);

                switch (opt) {
                    case 1: { 
                        System.out.println("Creating Store");
                        createStore(keyboard, model);
                        break;
                    }
                    case 2: {
                        System.out.println("Deleting Store");
                        deleteStore(keyboard, model);
                     break;
                    }
                    case 3: {
                        System.out.println("Viewing Stores");
                        viewStores(model);
                        break;
                    }
                    case 4: {
                        System.out.println("Viewing Store");
                        viewStore(keyboard, model);
                        break;
                    }                    
                    case 5: {
                        System.out.println("Edit Stores");
                        editStores(keyboard, model);
                        break;
                    }  
                    case 6: { 
                        System.out.println("Creating Region");
                        createRegion(keyboard, model);
                        break;
                    }
                    case 7: {
                        System.out.println("Deleting Region");
                        deleteRegion(keyboard, model);
                     break;
                    }
                    case 8: {
                        System.out.println("Viewing Regions");
                        viewRegions(model);
                        break;
                    }
                    case 9: {
                        System.out.println("Viewing Region");
                        viewRegion(keyboard, model);
                        break;
                    }                    
                    case 10: {
                        System.out.println("Edit Regions");
                        editRegion(keyboard, model);
                        break;
                    }                 
                }
            }
            catch (DataAccessException e) {
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }            
        }
        while (opt != 11);
        System.out.println("Goodbye");
    } 
    
//start of create store method
    private static void createStore(Scanner keyboard, Model model) throws DataAccessException  {   
        Store s = readStore(keyboard);
        if (model.addStore(s)){
            System.out.println();
            System.out.println("Store added to database");
            System.out.println();
        }
        else{
            System.out.println();
            System.out.println("Store not added to database");
            System.out.println();
        }    

    }  
//end of create store method      

//start of insert store method     
    private static Store readStore(Scanner keyb) {
        String address, manager;
        int phoneNumber, regionId;

        regionId = getInt(keyb, "Enter Region of store (-1 for no region): ", -1);
        address = getString(keyb, "Enter Address of store: ");
        manager = getString(keyb, "Enter Store Manager: ");
        phoneNumber = getInt(keyb, "Enter phone number: ", 0);


        Store s = new Store(regionId, address, manager, phoneNumber);
        
        return s;
    }
//end of insert store  
    
//view stores metheod
    private static void viewStores(Model mdl) {
       List<Store> stores = mdl.getStores();
       System.out.println();
       if(stores.isEmpty()){
           System.out.println("There are no Stores in the database");
       }
       else{
            dispayStores(stores, mdl);
       }
                    System.out.println();
    }
//end of view stores method
    
//Start Of Display Stores    
    private static void dispayStores (List <Store> stores, Model  mdl){
           System.out.printf("%5s %5s %40s %40s %10s\n", "Id", "Region","Address", "Manager", "Phone Number" );
           for(Store st :stores){
               Region r = mdl.findRegionById(st.getregionId());
               System.out.printf("%5d %20s %40s %40s %10d\n",
                       st.getstoreId(),
                       (r != null) ? r.getregion() : "",
                       st.getaddress(),
                       st.getmanager(),
                       st.getphoneNumber());
           }        
}   
//End of Display Stores    
//start of view store method
    private static void viewStore(Scanner keyboard, Model model) throws DataAccessException {
        int storeId = getInt (keyboard, "Enter the store to view:", -1);
        Store s;
//      takes in the value for storeId then uses it for the method 

        s = model.findStoreById(storeId);
        if (s != null) {
            Region r = model.findRegionById(s.getregionId());
            System.out.println();
            System.out.println("Store Id        " + s.getstoreId());
            System.out.println("Region Id       " + ((r != null) ? r.getregion() : ""));
            System.out.println("Address         " + s.getaddress());
            System.out.println("Manager Name    " + s.getmanager());
            System.out.println("Phone Number    " + s.getphoneNumber());
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Store not found");
            System.out.println();
        }
    }
//end of view store method        
    
//start of delete store method
    private static void deleteStore(Scanner keyboard, Model model) throws DataAccessException {
        int storeId = getInt (keyboard, "Enter the store to delete:", -1);
        Store s;
//      takes in the value for S_Id then uses it for the method 

        s = model.findStoreById(storeId);
        if (s != null) {
            if (model.removeStore(s)) {
                System.out.println();
                System.out.println("Store deleted");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Store not deleted");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Store not found");
            System.out.println();
        }
    }    
 

//start of edit store method      
    private static void editStores(Scanner kb, Model m) throws DataAccessException {
        int storeId = getInt (kb, "Enter the store to edit:", -1);

        Store s;
//      takes in the value for S_Id then uses it for the metheod        

        s = m.findStoreById(storeId);
        if (s != null) {
//      if stores result doesnn't equal 0 
            editStoreDetails(kb, s);
            if (m.updateStore(s)) {
//          update the store using s as a value to be updated 
                System.out.println();
                System.out.println("Store updated");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Store not updated");
                System.out.println();
//                if for some reason there is an error 
            }
        } else {
            System.out.println();
            System.out.println("Store not found");
            System.out.println();
//          if store is equal to 0 store does not exist
        }

    }
//end of edit store method     
    

//start of edit details store method  
    private static void editStoreDetails(Scanner keyb, Store s) {
        String address, manager;
        int regionId, phoneNumber;

        regionId = getInt(keyb, "Enter region number ["+ s.getregionId() + "]: ", 0);
        address = getString(keyb, "Enter Address of store [" + s.getaddress() + "]: ");
//      gets the users input using the keyboard value using getString and shows the current value for address
        manager = getString(keyb, "Enter Store Manager ["+ s.getmanager() + "]: ");
        phoneNumber = getInt(keyb, "Enter phone number ["+ s.getphoneNumber() + "]: ", 0);

        if (regionId != 0){
            s.setregionId (regionId);
        }        
        
        if (address.length() != 0){
            s.setaddress(address);
//      if the user enters a value that value will be the new set value
//      this is thanks to the setaddress fuction in the store class    
        }
        
        if (manager.length() != 0){
            s.setmanager(manager);
        }   
        
        if (phoneNumber != 0){
            s.setphoneNumber(phoneNumber);
        }        
    }
//end of edit store details  method  

    
//start of create region method
    private static void createRegion(Scanner keyboard, Model model) throws DataAccessException  {   
        Region r = readRegion(keyboard);
        if (model.addRegion(r)){
            System.out.println();
            System.out.println("Region added to database");
            System.out.println();
        }
        else{
            System.out.println();
            System.out.println("Region not added to database");
            System.out.println();
        }    

    }  
//end of create region method      

//start of insert region method     
    private static Region readRegion(Scanner keyb) {
        String region, managerName, email;
        int phoneNumber;

        region = getString(keyb, "Enter name of Region: ");
        managerName = getString(keyb, "Enter Region Managers Name: ");
        phoneNumber = getInt(keyb, "Enter phone number: ", 0);
        email = getString(keyb, "Enter email of Manager: ");

        Region r = new Region(region, managerName, phoneNumber, email);
        
        return r;
    }
//end of insert region  
    
//view regions metheod
    private static void viewRegions(Model mdl) {
       List<Region> regions = mdl.getRegions();
       System.out.println();
       if(regions.isEmpty()){
           System.out.println();
           System.out.println("There are no Regions in the database");
           System.out.println();
       }
       else{
           System.out.printf("%5s %20s %40s %15s %20s\n", "regionId", "Region","Manager", "Phone Number", "email" );
           for(Region st :regions){
               System.out.printf("%5d %20s %40s %15d %20s\n",
                       st.getregionId(),
                       st.getregion(),                       
                       st.getmanagerName(),
                       st.getphoneNumber(),
                       st.getemail());
           }
       }
                    System.out.println();
    }
//end of view regions method
    
    
    
//start of view region method
    private static void viewRegion(Scanner keyboard, Model model) throws DataAccessException {
        int regionId = getInt (keyboard, "Enter the region to view:", -1);
        Region r;
//      takes in the value for regionId then uses it for the method 

        r = model.findRegionById(regionId);
        if (r != null) {
                System.out.println();
                System.out.println("Region Id        " + r.getregionId());
                System.out.println("Manager Name     " + r.getmanagerName());
                System.out.println("Phone Number     " + r.getphoneNumber());
                System.out.println("Email            " + r.getemail());
                System.out.println();
            
            List<Store> storesList = model.getStoresByRegionId(r.getregionId());
            if (storesList.isEmpty()){
                System.out.println("This Region Has Region has no Stores Yet");    
            }
            else{
                System.out.println("These Stores are in this Region"); 
                System.out.println();
                dispayStores(storesList, model);
                System.out.println();
            }
            
        } else {
                System.out.println();
                System.out.println("Region not found");
                System.out.println();
        }
    }
//end of view region method  
    

    
//start of delete region method
    private static void deleteRegion(Scanner keyboard, Model model) throws DataAccessException {
        int regionId = getInt (keyboard, "Enter the region to delete:", -1);
        Region r;
//      takes in the value for S_Id then uses it for the method 

        r = model.findRegionById(regionId);
        if (r != null) {
            if (model.removeRegion(r)) {
                System.out.println();
                System.out.println("Region deleted");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Region not deleted");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Region not found");
            System.out.println();
        }
    }
//end of delete region method     

//start of edit region method      
    private static void editRegion(Scanner kb, Model m) throws DataAccessException {
        int regionId = getInt (kb, "Enter the region to edit:", -1);
        Region r;
//      takes in the value for S_Id then uses it for the metheod        

        r = m.findRegionById(regionId);
        if (r != null) {
//      if regions result doesnn't equal 0 
            editRegionDetails(kb, r);
            if (m.updateRegion(r)) {
//          update the region using s as a value to be updated
                System.out.println(); 
                System.out.println("Region updated");
                System.out.println(); 

            } else {
                System.out.println(); 
                System.out.println("Region not updated");
                System.out.println(); 
//                if for some reason there is an error 
            }
        } else {
            System.out.println(); 
            System.out.println("Region not found");
            System.out.println(); 
//          if region is equal to 0 region does not exist
        }

    }
//end of edit region method     
    

//start of edit details region method  
    private static void editRegionDetails(Scanner keyb, Region r) {
        String region, managerName, email;
        int phoneNumber;

        region = getString(keyb, "Enter name of Region [" + r.getregion() + "]: ");
//      gets the users input using the keyboard value using getString and shows the current value for address
        managerName = getString(keyb, "Enter Region Managers name ["+ r.getmanagerName() + "]: ");
        phoneNumber = getInt(keyb, "Enter phone number ["+ r.getphoneNumber() + "]: ", 0);
        email = getString(keyb, "Enter email ["+ r.getemail() + "]: ");        
        
        if (region.length() != 0){
            r.setregion(region);
//      if the user enters a value that value will be the new set value
//      this is thanks to the setaddress fuction in the region class    
        }
        
        if (managerName.length() != 0){
            r.setmanagerName(managerName);
        }   
        
        if (phoneNumber!= 0){
            r.setphoneNumber(phoneNumber);
        }
        if (email.length() != 0){
            r.setemail(email);
        }         
    }
//end of edit region details  method  


    
//start of get string method      
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }
//end of get string method    
    

    private static int getInt(Scanner keyb, String prompt, int defaultValue) {
        int opt = defaultValue;
        boolean finished = false;

        do {
            try {
                System.out.print(prompt);
                String line = keyb.nextLine();
                if (line.length() > 0) {
                    opt = Integer.parseInt(line);
                }
                finished = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        while (!finished);

        return opt;
    }

    
}




