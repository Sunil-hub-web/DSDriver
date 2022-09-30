package com.in.dsdriver.driver.modelclass;

public class Transaction_Model {

    String invoice_No, date, amount, action,tr_type,remark;

    public Transaction_Model(String invoice_No, String date, String amount, String action,String tr_type,String remark) {
        this.invoice_No = invoice_No;
        this.date = date;
        this.amount = amount;
        this.action = action;
        this.tr_type = tr_type;
        this.remark = remark;
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

    public String getTr_type() {
        return tr_type;
    }

    public void setTr_type(String tr_type) {
        this.tr_type = tr_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
