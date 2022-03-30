package com.in.dsdriver.modelclass;

public class UpcomingBooking_ModelClass {

    String reportDate,ReportTime,city,location,duty_type,duty_hours,car_detail,customer_id,address_id;

    public UpcomingBooking_ModelClass(String reportDate, String reportTime, String city, String location,
                                      String duty_type,String duty_hours,String car_detail,String customer_id,String address_id ) {
        this.reportDate = reportDate;
        ReportTime = reportTime;
        this.city = city;
        this.location = location;
        this.duty_type = duty_type;
        this.duty_hours = duty_hours;
        this.car_detail = car_detail;
        this.customer_id = customer_id;
        this.address_id = address_id;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return ReportTime;
    }

    public void setReportTime(String reportTime) {
        ReportTime = reportTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuty_type() {
        return duty_type;
    }

    public void setDuty_type(String duty_type) {
        this.duty_type = duty_type;
    }

    public String getDuty_hours() {
        return duty_hours;
    }

    public void setDuty_hours(String duty_hours) {
        this.duty_hours = duty_hours;
    }

    public String getCar_detail() {
        return car_detail;
    }

    public void setCar_detail(String car_detail) {
        this.car_detail = car_detail;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    @Override
    public String toString() {
        return "UpcomingBooking_ModelClass{" +
                "reportDate='" + reportDate + '\'' +
                ", ReportTime='" + ReportTime + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
