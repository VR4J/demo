package com.example.demo;

public class Person {
    
    String forename;
    String lastname;
    
    public Person(String forename, String lastname) {
        this.forename = forename;
        this.lastname = lastname;
    }

    public String getForename() {
        return forename;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String toString() {
        return String.format("[forename=%s, lastname=%s]", this.forename, this.lastname);
    }
}