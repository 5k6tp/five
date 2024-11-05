package com.itheima.reggie.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String toEmail, String subject, String code) {

        String body = "您好，您的驗證碼為：" + code + "，請在有效時間使驗證完畢。";
        
        // 发件人邮箱和密码
        final String fromEmail = "kolzkolz311005@gmail.com"; // 发件人的邮箱
        final String password = "xbqjpqjrtmqjcwac";     // 邮箱密码

        // 配置邮件服务器的属性
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");    // SMTP服务器地址
        props.put("mail.smtp.port", "587");                 // SMTP服务器端口号
        props.put("mail.smtp.auth", "true");                // 是否需要身份验证
        props.put("mail.smtp.starttls.enable", "true");     // 启用TLS加密

        // 创建Session对象来表示连接到邮件服务器的会话
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // 创建MimeMessage对象来表示邮件
            Message message = new MimeMessage(session);

            // 设置发件人地址
            message.setFrom(new InternetAddress(fromEmail));

            // 设置收件人地址
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // 设置邮件主题
            message.setSubject(subject);

            // 设置邮件内容
            message.setText(body);

            // 发送邮件
            Transport.send(message);

            System.out.println("郵件發送成功！");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     // 调用发送邮件的方法
    //     sendEmail("recipient@example.com", "测试邮件", "这是一封测试邮件内容。");
    // }
}
