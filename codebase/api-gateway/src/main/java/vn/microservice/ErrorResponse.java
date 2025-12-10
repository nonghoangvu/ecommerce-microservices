package vn.microservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private Date timestamp;

    private String path;

    private Integer status;

    private String error;

    private String message;
}
