package com.java.kafka.datamodel;

/**
 * Created by mgupta on 6/24/16.
 */
public class Customer {

    private String customerName;
    private int    customerId;

    public Customer(int id, String name) {
        this.customerId = id;
        this.customerName = name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

}
