package fmi.java.web.tourFinder.service;

import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import fmi.java.web.tourFinder.request.IdentityLoginRequest;
import fmi.java.web.tourFinder.response.IdentityLoginResponse;
import fmi.java.web.tourFinder.validator.IdentityValidator;
import fmi.java.web.tourFinder.validator.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;


@Service
public class IdentityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IdentityValidator identityValidator;

    public IdentityLoginResponse login(IdentityLoginRequest identityLoginRequest) throws ValidationException {
        Validation<String, String> validationResult = identityValidator.validateRequest(identityLoginRequest);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        User user = userRepository.findByUsername(identityLoginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        validationResult = identityValidator.validateUser(identityLoginRequest, user);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        return new IdentityLoginResponse(jwtService.generateToken(user));
    }
}