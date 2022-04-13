package com.in.dsdriver.modelclass;

public class Transaction_Model {

    String invoice_No, date, amount, action;

    public Transaction_Model(String invoice_No, String date, String amount, String action) {
        this.invoice_No = invoice_No;
        this.date = date;
        this.amount = amount;
        this.action = action;
    }

    public String getInvoice_No() {
        return invoice_No;
    }

    public void setInvoice_No(String invoice_No) {
        this.invoice_No = invoice_No;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
