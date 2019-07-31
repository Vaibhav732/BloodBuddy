package com.example.bloodbuddy;

public class Donor_info {
public String name;
    public String email;
    public String phone;
    public String blood_grp;
    public String age;
    public String latitude;
    public String longitude;
    public String address;





    public Donor_info(String Name, String Email, String Phone, String Blood_grp, String Age, String Latitude, String Longitude, String address) {
        this.name = Name;
        this.email = Email;
        this.phone = Phone;
        this.blood_grp = Blood_grp;
        this.age = Age;
        this.latitude = Latitude;
        this.longitude = Longitude;
        this.address = address;
    }

    public Donor_info(){}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBlood_grp() { return blood_grp; }

    public String getAge() { return age; }

    public String  getLatitude()  { return latitude; }

    public String getLongitude() { return longitude; }
    public String getAddress() { return address; }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBlood_grp(String blood_grp) {
        this.blood_grp = blood_grp;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
