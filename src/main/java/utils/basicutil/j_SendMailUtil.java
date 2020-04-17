package utils.basicutil;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * @author everywherewego
 * @date 2020-04-15 14:16
 */

public class j_SendMailUtil {
    public static void smtpSSLSend(String context) {
        try {
            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(setEmail());
            // 设置发件人
            message.setFrom(new InternetAddress("chong.wang@percent.cn"));
            // 设置邮件主题
            message.setSubject("抓取异常");
            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("chong.wang@percent.cn"));
            // 设置发送时间
            message.setSentDate(new Date());
            // 设置纯文本内容为邮件正文
            message.setText(context);
            //回执
//            message.setHeader("Disposition-Notification-To", "test@ext.com");
            //紧急
//            message.setHeader("X-Priority", "1");
            // 保存并生成最终的邮件内容
            message.saveChanges();
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Session setEmail() throws GeneralSecurityException {
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        Properties properties = new Properties();
        // 邮件发送协议
        properties.setProperty("mail.transport.protocol", "smtp");
        // SMTP邮件服务器
        properties.setProperty("mail.smtp.host", "newmail.percent.cn");
        // SMTP邮件服务器默认端口
        properties.put("mail.smtp.socketFactory.port", 465);//发信端口
        // 是否要求身份认证
        properties.setProperty("mail.smtp.auth", "true");
        // 是否启用调试模式
//            properties.setProperty("mail.debug", "true");//设置debug模式
        properties.put("mail.smtp.ssl.enable", "true");//是否开启ssl
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 发送邮件协议
        properties.setProperty("mail.smtp.auth", "true");// 需要验证


        final String username = "chong.wang@percent.cn";
        final String password = "1122334.";
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,
                        password);
            }
        });
        return session;
    }


    public static void main(String[] args) {
        smtpSSLSend("自行监测平台");
    }

}
