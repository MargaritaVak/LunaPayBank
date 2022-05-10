package com.example.bank.model;

public class Registration {
    public String firstName;
    public String lastName;
    public String patronymic;
    public String password;
    public String email;
    public String phone;
    public String birthday;

    public Registration(String firstName,String lastName,String patronymic,String password, String email,
                        String phone, String birthday){
        this.firstName= firstName;
        this.lastName=lastName;
        this.patronymic =patronymic;
        this.password= password;
        this.email= email;
        this.phone=phone;
        this.birthday=birthday;

    }

    public Registration() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
