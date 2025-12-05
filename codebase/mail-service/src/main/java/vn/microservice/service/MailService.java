package vn.microservice.service;

public interface MailService {
    /**
     * Send email
     * @param to to email
     * @param subject subject
     * @param body body
     */
    String sendEmail(String to, String subject, String body) throws InterruptedException;
}
