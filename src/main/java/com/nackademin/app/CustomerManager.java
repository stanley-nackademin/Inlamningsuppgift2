package com.nackademin.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerManager {

    /**
     * Read all customers from a text file and returns a List of said customers
     * @param path Path to the text file containing customer information
     * @return Returns a list of customers
     */
    public static List<Customer> readCustomersFile(String path) {
        Path p = Paths.get(path);
        List<Customer> customerList = new ArrayList<>();

        try (Scanner inFile = new Scanner(Files.newBufferedReader(p, StandardCharsets.UTF_8))) {
            String tempLine;
            String tempLine2 = "";

            while (inFile.hasNextLine()) {
                tempLine = inFile.nextLine();
                if (!tempLine.isEmpty()) {
                    if (inFile.hasNextLine()) {
                        tempLine2 = inFile.nextLine();
                    }
                    customerList.add(convertToCustomer(tempLine, tempLine2));
                }
            }
        } catch (IOException e) {
            System.out.println("Det går inte att läsa filen");
        }
        return customerList;
    }

    /**
     * Converts String information to their appropriate types and create a customer object
     * with it
     * @param customerInfo A single customer object
     * @param date Convert date from String to LocalDate in this pattern "yyyy-MM-dd"
     * @return Returns a customer object
     */
    protected static Customer convertToCustomer(String customerInfo, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Locale swedishLocale = new Locale("sv", "SE");
        formatter = formatter.withLocale(swedishLocale);
        LocalDate memberDate = LocalDate.parse(date, formatter);

        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        String[] parsedInfo = pattern.split(customerInfo);
        Customer customer = new Customer(parsedInfo[1], parsedInfo[2], parsedInfo[0], memberDate);

        return customer;
    }

    /**
     * Log customers' visit to the gym to a text file. Each log gets appended to the file
     * @param customer Customer to log
     */
    public static void logCustomerTraining(Customer customer) {
        Path p = Paths.get("customers_training_log.txt");
        OpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND};

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(p, StandardCharsets.UTF_8, options),
                true)) {
            writer.println(LocalDate.now(ZoneId.systemDefault()) + ", " +
            customer.getPersonnummer() + ", " +
            customer.getFirstName() + " " + customer.getLastName());

        } catch (IOException e) {
            System.out.println("Det går inte att skriva till filen");
        }
    }

    /**
     * Parse and check the user input is correct
     * @param s User input, either customer name or personnummer
     */
    protected static String checkUserInput(String s) {
        String userInput = s.trim();

        if (userInput.matches("[0-9]+")) {
            if (userInput.matches("\\d{10}")) {
                return userInput;
            }
            return "";
        } else if (userInput.matches("\\p{L}+")) {
            return userInput;
        } else if (userInput.matches("\\p{L}+\\s+\\p{L}+")) {
            userInput = userInput.replaceAll("\\s+", " ");
            return userInput;
        } else {
            return "";
        }
    }

    /**
     * Finds all customers matching the name or personnummer
     * @param customerString Search string is either the name or personnummer
     * @param customerList
     * @return Returns a list of customer matching the search criteria
     * @throws SearchFormatException
     */
    public static List<Customer> findCustomer(String customerString, List<Customer> customerList)
            throws SearchFormatException {
        String parsedString = checkUserInput(customerString);
        List<Customer> foundCustomerList = new ArrayList<>();

        if (parsedString.isEmpty()) {
            throw new SearchFormatException();
        }

        for (Customer c : customerList) {
            String temp = c.getFirstName().toUpperCase() + " " + c.getLastName().toUpperCase();
            if (temp.contains(parsedString.toUpperCase())) {
                foundCustomerList.add(c);
            }

            if (foundCustomerList.isEmpty()) {
                temp = c.getPersonnummer();
                if (temp.equalsIgnoreCase(parsedString)) {
                    foundCustomerList.add(c);
                }
            }
        }

        return foundCustomerList;
    }


    public static Customer getSpecificCustomerFromList(List<Customer> customerList, int index) {
        if (customerList.size() < index) {
            return customerList.get(index);
        }
        return null;
    }

    /**
     * Check if a customer's membership is valid. A membership is valid for
     * 1 year counting from their member date
     * @param customer The customer to check
     * @return true if membership is valid, else false
     */
    public static boolean checkMembershipValidity(Customer customer) {
        if (!(customer.getMemberDate().plusYears(1).isBefore(LocalDate.now(ZoneId.systemDefault())))) {
            return true;
        } else {
            return false;
        }
    }
}
