package com.nackademin.app;

import java.time.LocalDate;
import java.time.ZoneId;

public class Customer extends Person {

    private LocalDate memberDate;

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public Customer(String firstName, String lastName, String personnummer) {
        super(firstName, lastName, personnummer);
        memberDate = null;
    }

    public Customer(String firstName, String lastName, String personnummer, LocalDate memberDate) {
        super(firstName, lastName, personnummer);
        this.memberDate = memberDate;
    }

    public void assignCurrentDateToMember() {
        memberDate = LocalDate.now(ZoneId.systemDefault());
    }

    public void deleteMemberDate() {
        memberDate = null;
    }

    @Override
    public String toString() {
        return "\nPersonnummer: " + getPersonnummer() +
                "\nFörnamn: " + getFirstName() +
                "\nEfternamn: " + getLastName() +
                "\nMedlemsdatum: " + getMemberDate() +
                "\nMedlemskapet går ut: " + getMemberDate().plusYears(1);
    }
}
