package vn.microservice.dto.response;

import lombok.*;
import vn.microservice.common.EGender;

import java.io.Serializable;
import java.util.Date;

/**
 * User Response DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO implements Serializable {
    /* User id */
    private Long userId;

    /* First name */
    private String firstName;

    /* Last name */
    private String lastName;

    /* Date of birth */
    private Date dateOfBirth;

    /* Gender */
    private EGender gender;

    /* Phone number */
    private String phone;

    /* Email */
    private String email;

    /* Username */
    private String username;
}
