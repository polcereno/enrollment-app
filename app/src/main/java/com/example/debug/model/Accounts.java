package com.example.debug.model;

public class Accounts {
    private int id;
    private String username;
    private String role;

    public Accounts(int id, String username, String role){
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public int getAccountId() { return id; }

    public String getAccountUsername() { return username; }
    public String getAccountRole() { return role; }
}
