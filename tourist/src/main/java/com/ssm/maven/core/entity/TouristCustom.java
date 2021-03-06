package com.ssm.maven.core.entity;

import org.springframework.stereotype.Service;

@Service
public class TouristCustom extends Tourist {

    private String code;

    private String scenicname;

    private String address;

    private String telephone;

    private Integer max_people;

    private Integer sex;

    private Integer age;

    private String regione;

    private Tourist tourist;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScenicname() {
        return scenicname;
    }

    public void setScenicname(String scenicname) {
        this.scenicname = scenicname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getMax_people() {
        return max_people;
    }

    public void setMax_people(Integer max_people) {
        this.max_people = max_people;
    }

    @Override
    public String toString() {
        return super.toString() + "TouristCustom{" +
                "code='" + code + '\'' +
                ", scenicname='" + scenicname + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", max_people=" + max_people +
                '}';
    }
}
