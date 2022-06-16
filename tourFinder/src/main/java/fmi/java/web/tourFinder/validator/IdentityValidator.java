package fmi.java.web.tourFinder.validator;

import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.request.IdentityLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class IdentityValidator {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Validation<String, String> validateRequest(IdentityLoginRequest identityLoginRequest) {
        if (identityLoginRequest.getUsername() == null || identityLoginRequest.getUsername().isBlank()
                || identityLoginRequest.getPassword() == null || identityLoginRequest.getPassword().isBlank()) {
            return Validation.invalid("Bad credentials");
        }

        return Validation.valid("User found");
    }

    public Validation<String, String> validateUser(IdentityLoginRequest identityLoginRequest, User user) {
        if (user == null || !passwordEncoder.matches(identityLoginRequest.getPassword(),user.getPassword())) {
            return Validation.invalid("Bad credentials");
        }

        return Validation.valid("User found");
    }
}
