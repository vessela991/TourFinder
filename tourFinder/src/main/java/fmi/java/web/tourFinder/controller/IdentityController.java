package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.businessLogic.service.IdentityService;
import fmi.java.web.tourFinder.internal.util.Routes;
import fmi.java.web.tourFinder.model.request.IdentityLoginRequest;
import fmi.java.web.tourFinder.model.response.IdentityLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentityController {
    @Autowired
    private IdentityService identityService;

    @PostMapping(Routes.LOGIN)
    public ResponseEntity<IdentityLoginResponse> login(@RequestBody IdentityLoginRequest request) {
        return identityService.login(request).toResponseEntity();
    }
}
