package fmi.java.web.tourFinder.businessLogic;

import fmi.java.web.tourFinder.businessLogic.exception.Exception;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

public class Result<T> {
    private final T value;
    private final Exception exception;

    private Result(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <T> Result<T> success(T value) {
        if (value == null) {
            return new Result<>(null, ValidationException.create("Value cannot be null"));
        }

        return new Result<>(value, null);
    }

    public static <T> Result<T> failure(Exception exception) {
        return new Result<>(null, exception);
    }

    public static <T> Result<T> from(Optional<T> t, Supplier<String> errorMessage) {
        return t.map(Result::success).orElseGet(() -> failure(ValidationException.create(errorMessage.get())));
    }

    public T getValue() {
        if (hasNoValue()) {
            throw new RuntimeException("Result not checked properly for value");
        }

        return value;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean hasNoValue() {
        return !hasValue();
    }

    public Exception getException() {
        if (hasValue()) {
            throw new RuntimeException("Result not checked properly for exception");
        }

        return exception;
    }

    public ResponseEntity<T> toResponseEntity() {
        return toResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<T> toResponseEntity(HttpStatus httpStatus) {
        if (hasValue()) {
            return new ResponseEntity<>(value, httpStatus);
        }

        throw new ResponseStatusException(exception.getStatusCode(), exception.getMessage());
    }
}
