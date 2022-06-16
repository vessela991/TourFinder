package fmi.java.web.tourFinder.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends RuntimeException {
    private final HttpStatus statusCode = HttpStatus.FORBIDDEN;
    private final String userMessage = HttpStatus.FORBIDDEN.getReasonPhrase();

    private ForbiddenException() {
        super();
    }

    public static ForbiddenException create() {
        return new ForbiddenException();
    }
}
