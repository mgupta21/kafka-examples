package com.java.kafka.datamodel;

/**
 * Created by mgupta on 6/24/16.
 */
public class Customer {

    private String customerName;
    private int    customerID;

    public Customer(int id, String name) {
        this.customerID = id;
        this.customerName = name;
    }

    public String getName() {
        return customerName;
    }

    public int getID() {
        return customerID;
    }

}
