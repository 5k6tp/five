package com.fiveshop.fiveshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("member") // 数据库中对应的表名
public class Member implements Serializable {
    
    @TableId // 这里标识为主键
    private Long memberId;              // 对应数据库的 member_id，使用 BIGINT 类型

    @TableField("name")
    private String name;                 // 对应数据库的 name

    @TableField("email")
    private String email;                // 对应数据库的 email

    @TableField("phone_number")
    private String phoneNumber;          // 对应数据库的 phone_number

    @TableField("address")
    private String address;              // 对应数据库的 address

    @TableField("registration_date")
    private Date registrationDate;       // 对应数据库的 registration_date

    @TableField("membership_level")
    private String membershipLevel;      // 对应数据库的 membership_level

    @TableField("birthday")
    private Date birthday;               // 对应数据库的 birthday

    @TableField("status")
    private String status;               // 对应数据库的 status

    @TableField("last_login_time")
    private Date lastLoginTime;          // 对应数据库的 last_login_time
}
