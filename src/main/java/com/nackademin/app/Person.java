package com.nackademin.app;

public class Person {

    private String firstName;
    private String lastName;
    private String personnummer;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public void setPersonnummer(String personnummer) {
        this.personnummer = personnummer;
    }

    public Person(String firstName, String lastName, String personnummer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personnummer = personnummer;
    }
}
