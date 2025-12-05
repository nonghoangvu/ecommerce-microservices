package vn.microservice.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.microservice.service.MailService;

/**
 * Mail service
 */
@Service
@Slf4j(topic = "MAIL-SERVICE")
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    /**
     * Send email
     *
     * @param to      to email
     * @param subject subject
     * @param body    body
     * @return status
     */
    @Override
    public String sendEmail(String to, String subject, String body) throws InterruptedException {
        log.info("Sent email to {} subject {} body {}", to, subject, body);
        Thread.sleep(1000);
        return "message.success";
    }

    /**
     * Kafka listener event send email
     *
     * @param message message
     */
    @KafkaListener(topics = "confirm-account-topic", groupId = "confirm-account-group")
    public void sendConfirmEmail(String message) {
        log.info("Sent message {}", message);

        MessageDto messageDto = new Gson().fromJson(message, MessageDto.class);

        log.info("Sent to {}", messageDto.getEmail());
    }

    /**
     * MessageDto
     */
    @Getter
    @Setter
    private static class MessageDto {
        private long userId;
        private String email;
        private String secretCode;
    }
}
