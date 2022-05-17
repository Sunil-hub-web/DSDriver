package com.in.dsdriver.driver.modelclass;

public class UpcomingBooking_ModelClass {

    String bookingType,customerName,city,time,date,shift,day,DutyHours,DropLoc,CarDetails,Remarks,Charges,
            OTHours,OTAmount,TotalAmount,address,return_date,to_city,Locality,Landmark;

    public UpcomingBooking_ModelClass(String bookingType, String customerName, String city, String time, String date, String shift,
                                      String day, String dutyHours, String dropLoc, String carDetails, String remarks, String charges,
                                      String OTHours, String OTAmount, String totalAmount,String address,String return_date,String to_city,String Locality,String Landmark) {

        this.bookingType = bookingType;
        this.customerName = customerName;
        this.city = city;
        this.time = time;
        this.date = date;
        this.shift = shift;
        this.day = day;
        this.DutyHours = dutyHours;
        this.DropLoc = dropLoc;
        this.CarDetails = carDetails;
        this.Remarks = remarks;
        this.Charges = charges;
        this.OTHours = OTHours;
        this.OTAmount = OTAmount;
        this.TotalAmount = totalAmount;
        this.address = address;
        this.return_date = return_date;
        this.to_city = to_city;
        this.Locality = Locality;
        this.Landmark = Landmark;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDutyHours() {
        return DutyHours;
    }

    public void setDutyHours(String dutyHours) {
        DutyHours = dutyHours;
    }

    public String getDropLoc() {
        return DropLoc;
    }

    public void setDropLoc(String dropLoc) {
        DropLoc = dropLoc;
    }

    public String getCarDetails() {
        return CarDetails;
    }

    public void setCarDetails(String carDetails) {
        CarDetails = carDetails;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCharges() {
        return Charges;
    }

    public void setCharges(String charges) {
        Charges = charges;
    }

    public String getOTHours() {
        return OTHours;
    }

    public void setOTHours(String OTHours) {
        this.OTHours = OTHours;
    }

    public String getOTAmount() {
        return OTAmount;
    }

    public void setOTAmount(String OTAmount) {
        this.OTAmount = OTAmount;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getTo_city() {
        return to_city;
    }

    public void setTo_city(String to_city) {
        this.to_city = to_city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
