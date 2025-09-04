package com.smartacqua;

public class AquaristDTO {
    private String name;
    private String email;
    private String phone;
    private String code;

    public AquaristDTO(String name, String email, String phone, String code) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
