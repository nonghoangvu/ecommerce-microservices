package vn.microservice.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.microservice.common.EUserStatus;
import vn.microservice.config.MessageUtils;
import vn.microservice.dto.request.AccountConditionRequest;
import vn.microservice.dto.request.AccountRequest;
import vn.microservice.dto.response.UserResponseDTO;
import vn.microservice.dto.response.base.PageResponse;
import vn.microservice.exception.ResourceNotFoundException;
import vn.microservice.model.User;
import vn.microservice.repository.UserRepository;
import vn.microservice.service.AccountService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Account Service implement
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "ACCOUNT-SERVICE")
public class AccountServiceImpl implements AccountService {

    /**
     * User repository
     */
    private final UserRepository userRepository;

    /**
     * I18N
     */
    private final MessageUtils message;

    /**
     * Password Encoder
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * kafka template
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Topic
     */
    @Value("${spring.kafka.topic}")
    private String sendEmailTopic;

    /**
     * Get all account
     *
     * @param condition condition
     * @return List Account
     */
    @Override
    @Cacheable(value = "account-list", key = "#condition.keyword != null ? 'keyword_' + #condition.keyword : '' + 'page_' + #condition.page + 'size_' + #condition.size")
    public PageResponse<?> getAllAccount(AccountConditionRequest condition) {
        // Pageable
        PageRequest pageable = PageRequest.of(condition.getPage(), condition.getSize());

        // Data
        Page<User> data;

        // Search when keyword has length
        if (StringUtils.hasLength(condition.getKeyword())) {
            log.info("Get list account by keyword {}", condition.getKeyword());
            Specification<User> specification = userRepository.findAllUserByCondition(condition);
            data = userRepository.findAll(specification, pageable);
        } else {
            // Default
            log.info("Get list account");
            data = userRepository.findAll(pageable);
        }

        // Convert to PageResponse
        return PageResponse.<UserResponseDTO>builder()
                .pageNumber(data.getNumber())
                .pageSize(data.getSize())
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .data(data.getContent().stream().map(this::convertToDto).toList())
                .build();
    }

    /**
     * Get account by id
     *
     * @param userId userId
     * @throws ResourceNotFoundException when user not found
     * @return UserResponseDTO
     */
    @Override
    public UserResponseDTO findAccountById(Long userId) {
        return convertToDto(getUserById(userId));
    }

    /**
     * Save or Update Account
     *
     * @param request request body
     * @return userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account-list", allEntries = true)
    public Long saveAccount(AccountRequest request) {
        // Default
        User user = new User();

        if (request.getUserId() != null) {
            // Get user when userId not null
            user = getUserById(request.getUserId());
        }

        // Set field
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setType(request.getType());
        user.setStatus(EUserStatus.INACTIVE);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Map<String, Object> message = new LinkedHashMap<>();
        message.put("userId", user.getId());
        message.put("email", user.getEmail());
        message.put("secretCode", "123");

        String json = new Gson().toJson(message);
        kafkaTemplate.send(sendEmailTopic, json);
        log.info("Send email confirm message {}", json);

        // Save and return userId
        log.info("User has been saved");
        return userRepository.save(user).getId();
    }

    /**
     * Inactive all account
     * Make sure the account used
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkpointAccount() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            user.setStatus(EUserStatus.INACTIVE);
            users.add(user);
        });
        userRepository.saveAll(users);
    }

    /**
     * Get user by id
     *
     * @param userId user id
     * @throws ResourceNotFoundException when user not found
     * @return user
     */
    private User getUserById(Long userId) {
        log.info("Get user by user id {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(message.getMessage("message.error.dataNotFound")));
    }

    /**
     * Convert User to User DTO
     *
     * @param user entity
     * @return dto
     */
    private UserResponseDTO convertToDto(User user) {
        return UserResponseDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
