package com.nackademin.app;

import java.util.List;

public class Backend {

    private List<Customer> customerList;

    public List<Customer> getCustomerList() {
        return customerList;
    }

    Backend() {
        customerList = CustomerManager.readCustomersFile("customers.txt");
    }
}
