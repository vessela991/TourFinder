package fmi.java.web.tourFinder.businessLogic.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException implements Exception {
    private static final UnauthorizedException UNAUTHORIZED_EXCEPTION = new UnauthorizedException();

    private UnauthorizedException() {
        super();
    }

    public static UnauthorizedException instance() {
        return UNAUTHORIZED_EXCEPTION;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }
}
