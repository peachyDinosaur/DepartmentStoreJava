/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.app;

/**
 *
 * @author Elias
 */
public class Region {

    private int regionId;
    private String region;
    private String managerName;
    private int phoneNumber;
    private String email; 
//  values of the tables in the database 

    public Region(int regionId, String region, String managerName, int phoneNumber, String email) {
//        store object with parameters
        this.regionId = regionId;
        this.region = region;
        this.managerName = managerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public Region(String region, String managerName, int phoneNumber, String email) {
        this(-1, region, managerName, phoneNumber, email );
    }     
//  every store object has the value of the collumn asigned a value
          
//  Functions used to get & set values for store objects  
    public int getregionId() {
        return regionId;
    }

    public void setregionId(int regionId) {
        this.regionId = regionId;
    }

    public String getregion() {
        return region;
    }

    public void setregion(String region) {
        this.region = region;
    }

    public String getmanagerName() {
        return managerName;
    }

    public void setmanagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }    

}
