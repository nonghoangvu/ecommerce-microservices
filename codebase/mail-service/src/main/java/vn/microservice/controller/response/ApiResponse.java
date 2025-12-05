package vn.microservice.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Standard API response wrapper.
 * Contains status code, message, and optional data payload.
 */
@Builder
@Getter
@Setter
public class ApiResponse implements Serializable {

    /**
     * HTTP status code of the response.
     */
    private int status;

    /**
     * Response message, e.g., "Success" or error details.
     */
    private String message;

    /**
     * Optional response data. Will be excluded from JSON if null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    /**
     * Custom serialization method.
     */
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * Custom deserialization method.
     */
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}
