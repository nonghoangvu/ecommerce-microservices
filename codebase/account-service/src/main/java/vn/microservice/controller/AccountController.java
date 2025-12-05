package vn.microservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.microservice.config.MessageUtils;
import vn.microservice.dto.request.AccountConditionRequest;
import vn.microservice.dto.request.AccountRequest;
import vn.microservice.dto.response.base.ApiResponse;
import vn.microservice.service.AccountService;

/**
 * API Account Service
 */
@RestController
@Slf4j(topic = "ACCOUNT-SERVICE")
@RequiredArgsConstructor
@Valid
public class AccountController {

    /* Account service */
    private final AccountService accountService;

    /* I18N */
    private final MessageUtils messageUtils;

    /**
     * Get List Account
     *
     * @param condition condition
     * @return ApiResponse
     */
    @GetMapping("/list")
    public ApiResponse getListAccount(AccountConditionRequest condition) {
        log.info("Get list account");
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(messageUtils.getMessage("message.success"))
                .data(accountService.getAllAccount(condition))
                .build();
    }

    /**
     * Find by user id
     *
     * @param userId userId
     * @return ApiResponse
     */
    @GetMapping("/{userId}")
    public ApiResponse getAccountById(
            @Min(value = 1) @PathVariable Long userId
    ) {
        log.info("Get account by id {}", userId);
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(messageUtils.getMessage("message.success"))
                .data(accountService.findAccountById(userId))
                .build();
    }

    /**
     * Save account
     *
     * @param request request body
     * @return ApiResponse
     */
    @PostMapping("/save")
    public ApiResponse saveAccount(@Validated @RequestBody AccountRequest request) {
        log.info("Save account");
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message(messageUtils.getMessage("message.success"))
                .data(accountService.saveAccount(request))
                .build();
    }
}
