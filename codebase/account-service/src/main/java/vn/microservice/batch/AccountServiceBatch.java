package vn.microservice.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.microservice.service.AccountService;

import java.time.LocalDateTime;

/**
 * Account service batch
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "ACCOUNT-SERVICE-BATCH")
public class AccountServiceBatch {
    /**
     * Account service
     */
    private final AccountService accountService;

    /**
     * Inactive all account
     * 00h every month 0 0 0 1 * *
     */
    @Async("taskExecutor")
    @Scheduled(cron = "0 * * * * *")
    public void inactiveAllAccount() {
        log.info("INACTIVE ALL ACCOUNT {}", LocalDateTime.now());
        accountService.checkpointAccount();
    }
}
