package com.example.bloodbuddy;

public class Requester_info {
    public String name;
    public String email;
    public String phone;
    public String blood_grp;
    public String units_required;
    public String latitude;
    public String longitude;
    public String address;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBlood_grp() {
        return blood_grp;
    }

    public String getUnits_required() {
        return units_required;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }


    public Requester_info(String name, String email, String phone, String blood_grp, String units_required, String latitude, String longitude, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.blood_grp = blood_grp;
        this.units_required = units_required;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Requester_info(){}

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

    public void setUnits_required(String units_required) {
        this.units_required = units_required;
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
