package vn.microservice.dto.request;

import lombok.*;
import vn.microservice.dto.request.base.ConditionRequest;

/**
 * Account condition
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountConditionRequest extends ConditionRequest {

    /* user id */
    private String userId;

    /* username */
    private String username;

    /* phone number */
    private String phone;

    /* email */
    private String email;
}
