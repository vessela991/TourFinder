package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.request.IdentityLoginRequest;
import fmi.java.web.tourFinder.response.IdentityLoginResponse;
import fmi.java.web.tourFinder.service.IdentityService;
import fmi.java.web.tourFinder.util.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
public class IdentityController {
    @Autowired
    private IdentityService identityService;

    @PostMapping(Routes.LOGIN)
    public ResponseEntity<IdentityLoginResponse> login(@RequestBody IdentityLoginRequest request) throws ValidationException {
        return new ResponseEntity<>(identityService.login(request), HttpStatus.OK);
    }
}
