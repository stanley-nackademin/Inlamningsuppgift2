package com.nackademin.app;

import java.util.List;
import java.util.Scanner;

public class Demo {

    Demo() {
        Backend b = new Backend();

        while (true) {
            System.out.println("\nSkriv in medlemmens namn eller personnummer:");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            List<Customer> foundCustomerList = null;

            if (userInput.isEmpty()) {
                break;
            }

            try {
                foundCustomerList = CustomerManager.findCustomer(userInput, b.getCustomerList());

                if (foundCustomerList.isEmpty()) {
                    System.out.println("\nKunde inte hitta medlemmen.");
                } else {
                    for (Customer c : foundCustomerList) {
                        System.out.println(c.toString());
                    }

                    if (CustomerManager.checkMembershipValidity(foundCustomerList.get(0))) {
                        System.out.println("\nRegistrera medlemmen i träningsloggen? (j / n)");
                        while (true) {
                            userInput = scanner.nextLine();
                            if (userInput.equalsIgnoreCase("j")) {

                                CustomerManager.logCustomerTraining(foundCustomerList.get(0));
                                System.out.println("\nMedlemmen har registrerats i träningsloggen!");
                                break;

                            } else if (userInput.equalsIgnoreCase("n")) {
                                System.out.println("\nMedlemmen har inte registrerats i träningsloggen.");
                                break;
                            }
                        }
                    } else {
                        System.out.println("\nMedlemskapet har gått ut!");
                    }
                }

            } catch (SearchFormatException e) {
                System.out.println("\nOgiltigt namn eller personnummer");
            }
        }
    }

    public static void main(String[] args) {
        Demo d = new Demo();
    }
}
