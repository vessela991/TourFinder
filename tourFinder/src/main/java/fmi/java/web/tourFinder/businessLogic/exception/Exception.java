package fmi.java.web.tourFinder.businessLogic.exception;

import org.springframework.http.HttpStatus;

public interface Exception {
    HttpStatus getStatusCode();
    String getMessage();
}
