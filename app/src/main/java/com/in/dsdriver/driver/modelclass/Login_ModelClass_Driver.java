package com.in.dsdriver.driver.modelclass;

public class Login_ModelClass_Driver {

    String driverID,name,email,mobileno,statues,password,driver_type,shift_type;

    public Login_ModelClass_Driver(String driverID, String name, String email, String mobileno,
                                   String statues, String password,String driver_type,String shift_type) {
        this.driverID = driverID;
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.statues = statues;
        this.password = password;
        this.driver_type = driver_type;
        this.shift_type = shift_type;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
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

    public String getDriver_type() {
        return driver_type;
    }

    public void setDriver_type(String driver_type) {
        this.driver_type = driver_type;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }
}
