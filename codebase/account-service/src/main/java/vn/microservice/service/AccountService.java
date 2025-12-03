package vn.microservice.service;

import vn.microservice.dto.request.AccountConditionRequest;
import vn.microservice.dto.request.AccountRequest;
import vn.microservice.dto.response.UserResponseDTO;
import vn.microservice.dto.response.base.PageResponse;

public interface AccountService {

    /**
     * Get All Account By Condition
     *
     * @param condition condition
     * @return List Account
     */
    PageResponse<?> getAllAccount(AccountConditionRequest condition);

    /**
     * Find account by id
     *
     * @param userId userId
     * @return UserResponseDTO
     */
    UserResponseDTO findAccountById(Long userId);

    /**
     * Save or Update Account
     *
     * @param request request body
     * @return userId
     */
    Long saveAccount(AccountRequest request);

    /**
     * Inactive all account
     * Make sure the account used
     */
    void checkpointAccount();
}
