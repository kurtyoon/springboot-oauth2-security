package dev.spring.security.oauth2.service;

import dev.spring.security.oauth2.dto.auth.EmailVerifyDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final String code = createCode();
    @Value("${spring.mail.username}")
    private String id;

    public Boolean verifyCode(EmailVerifyDto emailVerifyDto) {
        String requestMail = redisService.getCode(emailVerifyDto.code());
        if (requestMail.equals(emailVerifyDto.email())) {
            redisService.deleteCode(emailVerifyDto.code());
            return true;
        }
        redisService.deleteCode(emailVerifyDto.code());
        return false;
    }

    public String sendCode(String destination) throws MessagingException, UnsupportedEncodingException {
        redisService.deleteCode(code);
        String newCode =  createCode();

        redisService.setCodeExpire(code, destination, (long) 180);
        javaMailSender.send(createMessage(destination));
        return newCode;
    }

    public MimeMessage createMessage(String destination) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, destination);

        message.setSubject(destination + "님의 회원가입 인증 코드입니다.");

        String content = getContent();

        message.setText(content, "UTF-8", "html");
        message.setFrom(new InternetAddress(id, "Volunmate"));
        return message;
    }

    private String getContent() {
        String content = "";
        content += "<div style='width: 500px; height: 500px; border: 1px solid black;'>";
        content += "<h1 style='text-align: center;'>회원가입 인증 코드</h1>";
        content += "<p>";
        content += "안녕하세요. 회원가입을 위해 인증코드를 입력해주세요.<br>";
        content += "아래의 인증코드를 입력하시면 회원가입이 완료됩니다.<br>";
        content += "감사합니다.";
        content += "</p>";
        content += "<div style='text-align: center;'>";
        content += "<h3>" + code + "</h3>";
        content += "</div>";
        return content;
    }


    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append((char) (random.nextInt(10) + 48));
        }
        return code.toString();
    }

}