package fmi.java.web.tourFinder.businessLogic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends RuntimeException implements Exception {
    private static final ForbiddenException FORBIDDEN_EXCEPTION = new ForbiddenException();

    private ForbiddenException() {
        super();
    }

    public static ForbiddenException instance() {
        return FORBIDDEN_EXCEPTION;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getMessage() {
        return HttpStatus.FORBIDDEN.getReasonPhrase();
    }
}
