package com.example.debug.model;

public class Applicant {
    private int id;
    private String level, firstName, lastName, middleName;

    public Applicant(int id, String level, String firstName, String lastName, String middleName) {
        this.id = id;
        this.level = level;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public int getApplicantId() { return id; }

    public String getApplicantLevel() { return level; }

    public String getApplicantFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }
}
