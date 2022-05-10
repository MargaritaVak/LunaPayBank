package com.example.bank.model;

public class ShortCode {
    public String pinCode;

    public ShortCode(String shortCode){
        this.pinCode=shortCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
