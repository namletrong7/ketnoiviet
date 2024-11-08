package com.hien.ketnoiviet.model;

import java.io.Serializable;

public class User implements Serializable {
    private int Idusers;
    private String Nameuser;
    private String Birthday;
    private String Gender;
    private String Imageuser;
    private String Email;
    private String Phonenumber;
    private String Status;
    private String Hometown;
    private int Money;
    private String Datecreate;
    private String Password;
    private String cover;


    public User() {
    }

    public User(int idusers, String nameuser, String birthday, String gender, String imageuser, String email, String phonenumber, String status, String hometown, int money, String datecreate, String password, String cover) {
        Idusers = idusers;
        Nameuser = nameuser;
        Birthday = birthday;
        Gender = gender;
        Imageuser = imageuser;
        Email = email;
        Phonenumber = phonenumber;
        Status = status;
        Hometown = hometown;
        Money = money;
        Datecreate = datecreate;
        Password = password;
        this.cover = cover;
    }

    public int getIdusers() {
        return Idusers;
    }

    public void setIdusers(int idusers) {
        Idusers = idusers;
    }

    public String getNameuser() {
        return Nameuser;
    }

    public void setNameuser(String nameuser) {
        Nameuser = nameuser;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImageuser() {
        return Imageuser;
    }

    public void setImageuser(String imageuser) {
        Imageuser = imageuser;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getHometown() {
        return Hometown;
    }

    public void setHometown(String hometown) {
        Hometown = hometown;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getDatecreate() {
        return Datecreate;
    }

    public void setDatecreate(String datecreate) {
        Datecreate = datecreate;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
