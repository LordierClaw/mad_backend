package org.example.mobile.service.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${mail.smtp.host}")
    private String MAIL_HOST;

    @Value("${mail.smtp.port}")
    private String MAIL_PORT;

    @Value("${mail.username}")
    private String MAIL_USERNAME;

    @Value("${mail.password}")
    private String MAIL_PASSWORD;

    @Override
    public void sendResetPasswordLink(String email, int otpCode) {
        try {
            Properties properties = new Properties() {{
                put("mail.smtp.auth", "true");
                put("mail.smtp.starttls.enable", "true");
                put("mail.smtp.host", MAIL_HOST);
                put("mail.smtp.port", MAIL_PORT);
            }};
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MAIL_USERNAME, MAIL_PASSWORD);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL_USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Mã OTP thay đổi mật khẩu");
            String htmlContent = """
                    <html>
                        <body>
                            <h1>Mã OTP thay đổi mật khẩu</h1>
                            <p>Bạn đã gửi yêu cầu thay đổi mật khẩu và dưới đây là mã của bạn.</p>
                            <p>Lưu ý đoạn mã này sẽ chỉ sử dụng được trong thời gian 5 phút tính từ lúc gửi.</p>
                            <h3>${otp}</h3>
                        </body>
                    </html>
                    """
                    .replace("${otp}", String.valueOf(otpCode));

            message.setContent(htmlContent, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
