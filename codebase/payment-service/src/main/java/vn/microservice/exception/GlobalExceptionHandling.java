package vn.microservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.microservice.controller.response.ErrorResponse;

import java.util.Date;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * GlobalException
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandling {
    /**
     * Handle exception when the request not found data
     *
     * @param e       Exception
     * @param request WebRequest
     * @return error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(NOT_FOUND.value());
        errorResponse.setError(NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());

        return errorResponse;
    }
}
