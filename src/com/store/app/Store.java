/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.app;


public class Store {

    private int storeId;
    private int regionId;
    private String address;
    private String manager;
    private int phoneNumber; 
//  values of the tables in the database 

    public Store(int storeId, int regionId, String a, String sM, int pN) {
//        store object with parameters
        this.storeId = storeId;
        this.regionId = regionId;
        this.address = a;
        this.manager = sM;
        this.phoneNumber = pN;
    }
//  every store object has the value of the collumn asigned a value

    public Store(String a, String sM, int pN) {
        this(-1, 1, a, sM, pN );
    }    
//  second store object which has S_Id as -1 & value of 1 for R_Id other values used from other store object 
    
    
//  Functions used to get & set values for store objects  
    public int getstoreId() {
        return storeId;
    }

    public void setstoreId(int s_id) {
        this.storeId = s_id;
    }

    public int getregionId() {
        return regionId;
    }

    public void setregionId(int rid) {
        this.regionId = rid;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String a) {
        this.address = a;
    }

    public String getmanager() {
        return manager;
    }

    public void setmanager(String sM) {
        this.manager = sM;
    }

    public int getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(int pN) {
        this.phoneNumber = pN;
    }

}