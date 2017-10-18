package com.nackademin.app;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerManagerTest {

    @Test
    public void membershipShouldBeValid() {
        Customer c = new Customer("Nisse", "Bouvale", "123456789",
                LocalDate.of(2016, 12, 17));
        boolean value = CustomerManager.checkMembershipValidity(c);
        Assert.assertTrue(value);
    }

    @Test(expected = SearchFormatException.class)
    public void shouldThrowSearchFormatException() throws SearchFormatException {
        List<Customer> customerList = new ArrayList<>();
        CustomerManager.findCustomer("", customerList);
    }

    @Test
    public void checkUserInputShouldBeEmpty() {
        String s = CustomerManager.checkUserInput("123456789"); // Måste vara 10 nummer annars kastas ett undantag
        Assert.assertEquals("", s);
    }
}
