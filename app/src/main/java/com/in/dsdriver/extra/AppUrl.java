package com.in.dsdriver.extra;

public class AppUrl {
    
    public static final String baseUrl="https://driversuvidha.COM/CRM/api" ;
  //  public static final String baseUrl="https://driversuvidha.COM/uat/CRM/api" ;
    

    public static final String userLogin = baseUrl+"/Auth/driver_login";

    public static final String forgetpassword = baseUrl+"/Driver/forgetpassword";

    public static final String verifyOtp = baseUrl+"/Driver/verifyOtp";

    public static final String resetpassword = baseUrl+"/Driver/resetpassword";

    public static final String availableBooking = baseUrl+"/Driver/availableBooking";

    public static final String acceptBooking = baseUrl+"/Driver/acceptBooking";

    public static final String allAssignedBooking = baseUrl+"/Driver/allAssignedBooking";

    public static final String getDriverProfile = baseUrl+"/Driver/getDriverProfile";

    public static final String updateDriverProfile = baseUrl+"/Driver/updateDriverProfile";

    public static final String allCompletedBooking = baseUrl+"/Driver/allCompletedBooking";

    public static final String availableBooking_zoewise = baseUrl+"/Driver/availableBooking_zoewise";

    public static final String invoice_report = baseUrl+"/Driver/invoice_report";

    public static final String guestBooking = baseUrl+"/Driver/guestBooking";

    public static final String endBooking = baseUrl+"/Driver/endBooking";

    public static final String profileImageUpload = baseUrl+"/Driver/profileImageUpload";


    // CAB Owner Api

    public static final String cab_Login = baseUrl+"/cab_login";

    public static final String change_password = baseUrl+"/cab/change_password";

    public static final String home_page = baseUrl+"/cab/home_page";




}
