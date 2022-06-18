package fmi.java.web.tourFinder.businessLogic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException implements Exception {
    private final List<String> errors;

    private ValidationException(List<String> errors) {
        super();
        this.errors = errors;
    }

    public static ValidationException create(List<String> errors) {
        return new ValidationException(errors);
    }

    public static ValidationException create(String error) {
        return new ValidationException(Collections.singletonList(error));
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return String.join(", ", errors);
    }
}