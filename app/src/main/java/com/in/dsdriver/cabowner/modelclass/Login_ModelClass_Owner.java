package com.in.dsdriver.cabowner.modelclass;

public class Login_ModelClass_Owner {

    String cabOwnerId,name,email,mobileno,statues,password;

    public Login_ModelClass_Owner(String driverID, String name, String email, String mobileno, String statues, String password) {
        this.cabOwnerId = driverID;
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.statues = statues;
        this.password = password;
    }

    public String getCabOwnerId() {
        return cabOwnerId;
    }

    public void setCabOwnerId(String cabOwnerId) {
        this.cabOwnerId = cabOwnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
