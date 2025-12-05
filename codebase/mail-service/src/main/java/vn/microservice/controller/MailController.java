package vn.microservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.microservice.service.MailService;

/**
 * Mail controller
 */
@RestController
@RequiredArgsConstructor
@Slf4j(topic = "MAIL-CONTROLLER")
public class MailController {

    /* Mail Service */
    private final MailService mailService;

    /**
     * Send email
     * @param to to email
     * @param subject subject
     * @param body body
     * @return status
     */
    @Operation(summary = "Send simple email", description = "Send email with plain text")
    @GetMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) throws InterruptedException {
        return mailService.sendEmail(to, subject, body);
    }
}
