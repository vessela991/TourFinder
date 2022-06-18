package fmi.java.web.tourFinder.businessLogic.validator;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.model.request.IdentityLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class IdentityValidator {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Result<IdentityLoginRequest> validateRequest(IdentityLoginRequest identityLoginRequest) {
        if (identityLoginRequest.getUsername() == null
                || identityLoginRequest.getUsername().isBlank()
                || identityLoginRequest.getPassword() == null
                || identityLoginRequest.getPassword().isBlank()) {
            return Result.failure(ValidationException.create("Bad credentials"));
        }

        return Result.success(identityLoginRequest);
    }

    public Result<User> validateUser(IdentityLoginRequest identityLoginRequest, User user) {
        if (user == null || !passwordEncoder.matches(identityLoginRequest.getPassword(), user.getPassword())) {
            return Result.failure(ValidationException.create("Bad credentials"));
        }

        return Result.success(user);
    }
}
