package com.example.debug.model;

public class AccountStudent {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String level;
    private String studentID;

    public AccountStudent(int id, String firstName, String lastName, String middleName, String level, String studentID){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.level = level;
        this.studentID = studentID;
    }

    public String getAccountName() {
        // Concatenate first, middle, and last names
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }

    public String getAccountLevel() { return level; }

    public String getAccountStudentID() { return studentID; }
}

