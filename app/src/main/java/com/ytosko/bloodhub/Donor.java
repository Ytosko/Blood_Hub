package com.ytosko.bloodhub;

public class Donor {
    private String name;
    private String email;
    private String phone;
    private String blood;
    private String age;
    private String location;
    private String status;
    private String par;
    private String varified;
    private String key;
    private String type;

    public Donor() {
    }

    public Donor(String name, String email, String phone, String blood, String age, String location, String status, String par, String varified, String type) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.blood = blood;
        this.age = age;
        this.location = location;
        this.status = status;
        this.par = par;
        this.varified = varified;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }

    public String getVarified() {
        return varified;
    }

    public void setVarified(String varified) {
        this.varified = varified;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
