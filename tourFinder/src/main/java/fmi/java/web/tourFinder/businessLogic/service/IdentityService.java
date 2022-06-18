package fmi.java.web.tourFinder.businessLogic.service;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.businessLogic.validator.IdentityValidator;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.model.request.IdentityLoginRequest;
import fmi.java.web.tourFinder.model.response.IdentityLoginResponse;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IdentityValidator identityValidator;

    public Result<IdentityLoginResponse> login(IdentityLoginRequest identityLoginRequest) {
        Result<IdentityLoginRequest> identityLoginRequestResult = identityValidator.validateRequest(identityLoginRequest);

        if (identityLoginRequestResult.hasNoValue()) {
            return Result.failure(identityLoginRequestResult.getException());
        }

        Optional<User> optionalUser = userRepository.findByUsername(identityLoginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            return Result.failure(ValidationException.create(String.format("User with username: %s not found", identityLoginRequest.getUsername())));
        }

        Result<User> userResult = identityValidator.validateUser(identityLoginRequest, optionalUser.get());

        if (userResult.hasNoValue()) {
            return Result.failure(userResult.getException());
        }

        return Result.success(new IdentityLoginResponse(jwtService.generateToken(userResult.getValue())));
    }
}