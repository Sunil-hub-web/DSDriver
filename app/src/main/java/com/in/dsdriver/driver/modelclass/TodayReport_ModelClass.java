package com.in.dsdriver.driver.modelclass;

public class TodayReport_ModelClass {

    String booking_id,Locality,Landmark,date,time,dutyHour,noofDays,duty_type,shift,drop_locality,
            car_details,remark,return_date,drop_city,to_city,driver_type_name,driver_type,booking;

    public TodayReport_ModelClass(String booking_id, String locality, String landmark, String date, String time, String dutyHour,
                                  String noofDays, String duty_type, String shift, String drop_locality,
                                  String car_details, String remark,String return_date,String drop_city,
                                  String to_city,String driver_type_name,String driver_type,String booking) {
        this.booking_id = booking_id;
        this.Locality = locality;
        this.Landmark = landmark;
        this.date = date;
        this.time = time;
        this.dutyHour = dutyHour;
        this.noofDays = noofDays;
        this.duty_type = duty_type;
        this.shift = shift;
        this.drop_locality = drop_locality;
        this.car_details = car_details;
        this.remark = remark;
        this.return_date = return_date;
        this.drop_city = drop_city;
        this.to_city = to_city;
        this.driver_type_name = driver_type_name;
        this.driver_type = driver_type;
        this.booking = booking;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDutyHour() {
        return dutyHour;
    }

    public void setDutyHour(String dutyHour) {
        this.dutyHour = dutyHour;
    }

    public String getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(String noofDays) {
        this.noofDays = noofDays;
    }

    public String getDuty_type() {
        return duty_type;
    }

    public void setDuty_type(String duty_type) {
        this.duty_type = duty_type;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDrop_locality() {
        return drop_locality;
    }

    public void setDrop_locality(String drop_locality) {
        this.drop_locality = drop_locality;
    }

    public String getCar_details() {
        return car_details;
    }

    public void setCar_details(String car_details) {
        this.car_details = car_details;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getDrop_city() {
        return drop_city;
    }

    public void setDrop_city(String drop_city) {
        this.drop_city = drop_city;
    }

    public String getTo_city() {
        return to_city;
    }

    public void setTo_city(String to_city) {
        this.to_city = to_city;
    }

    public String getDriver_type_name() {
        return driver_type_name;
    }

    public void setDriver_type_name(String driver_type_name) {
        this.driver_type_name = driver_type_name;
    }

    public String getDriver_type() {
        return driver_type;
    }

    public void setDriver_type(String driver_type) {
        this.driver_type = driver_type;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}
