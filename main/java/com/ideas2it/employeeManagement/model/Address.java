//                                                                             ;
package com.ideas2it.employeeManagement.model;

import com.ideas2it.employeeManagement.model.Employee;

/**
 * Pojo Class Containing Address Details
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class Address {

    private int id;
    private Employee employee;
    private String doorNumber;
    private String street;
    private String city;
    private String pincode;
    private String state;
    private String country;
    private String type;
    private boolean isDeleted;

    public Address() { }

    public Address(String doorNumber, String street, String city,
            String pincode, String state, String country, String type,
            boolean isDeleted) {
        this.doorNumber = doorNumber;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.country = country;
        this.type = type;
        this.isDeleted = isDeleted;
    }


    public String toString() {    
        return (doorNumber + ", " + street + ", " +city + " - " + pincode
                 + ", " + state + ", " + country + " (" + type + " address)");
    }

    public int getId() {    
        return id;
    }

    public void setId(int id) {    
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {    
        this.employee = employee;
    }

    public String getDoorNumber() {    
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {    
        this.doorNumber = doorNumber;
    }

    public String getStreet() {    
        return street;
    }

    public void setStreet(String street) {    
        this.street = street;
    }

     public String getCity() {    
        return city;
    }

    public void setCity(String city) {    
        this.city = city;
    }

    public String getPincode() {    
        return pincode;
    }

    public void setPincode(String pincode) {    
        this.pincode = pincode;
    }

    public String getState() {    
        return state;
    }

    public void setState(String state) {    
        this.state = state;
    }

    public String getCountry() {    
        return country;
    }

    public void setCountry(String country) {    
        this.country = country;
    }
    
    public String getType() {    
        return type;
    }

    public void setType(String type) {    
        this.type = type;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}