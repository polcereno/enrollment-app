package com.example.debug;

public class StudentAccount {
    private String fname;
    private String mname;
    private String lname;
    private String sex;
    private String email;
    private String phone;
    private String purok;
    private String birthdate;
    private String province;
    private String municipality;
    private String barangay;
    private String lrn;
    private String level;
    private String username;
    private String password;

    // Constructor
    public StudentAccount(String fname, String mname, String lname, String sex, String email, String phone, String purok,
                          String birthdate, String province, String municipality, String barangay, String lrn, String level,
                          String username, String password) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.purok = purok;
        this.birthdate = birthdate;
        this.province = province;
        this.municipality = municipality;
        this.barangay = barangay;
        this.lrn = lrn;
        this.level = level;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPurok() {
        return purok;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getProvince() {
        return province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getBarangay() {
        return barangay;
    }

    public String getLrn() {
        return lrn;
    }

    public String getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
