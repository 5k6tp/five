package com.fiveshop.fiveshop.entity;

import java.sql.Timestamp;

public class Recipients {
    private String id;           // 收件人唯一标识
    private String name;      // 收件人姓名
    private String email;     // 收件人邮箱
    private String phone;     // 收件人电话（可选）
    private String address;   // 收件人地址（可选）
    private Timestamp createdAt; // 创建时间
    private Timestamp updatedAt; // 更新时间

    // Getter 和 Setter 方法

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

