package vn.microservice.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import vn.microservice.common.EGender;
import vn.microservice.common.EUserType;

import java.io.Serializable;
import java.util.Date;

/**
 * Account Request
 */
@Getter
public class AccountRequest implements Serializable {
    /* user id */
    private Long userId;

    /* first name */
    private String firstName;

    /* last name */
    private String lastName;

    /* date of birth */
    private Date dateOfBirth;

    /* gender */
    private EGender gender;

    /* phone number */
    private String phone;

    /* email */
    @Email(message = "Email invalid format")
    private String email;

    /* username */
    private String username;

    /* password */
    private String password;

    /* user type */
    private EUserType type;
}
