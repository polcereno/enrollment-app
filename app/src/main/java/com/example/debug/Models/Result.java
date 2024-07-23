package com.example.debug.Models;

public class Result {
    private int id;
    private String level, firstName, lastName, middleName, result;

    public Result(int id, String level, String firstName, String lastName, String middleName, String result) {
        this.id = id;
        this.level = level;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.result = result;
    }

    public int getResultId() { return id; }

    public String getResultLevel() { return level; }

    public String getResult() { return result; }

    public String getApplicantFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }
}
