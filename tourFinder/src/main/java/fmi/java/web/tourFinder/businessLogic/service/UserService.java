package fmi.java.web.tourFinder.businessLogic.service;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ForbiddenException;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import fmi.java.web.tourFinder.businessLogic.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public Result<User> getById(String id, User loggedUser) {
        Result<User> userResult = Result.from(userRepository.findById(id), () -> String.format("User with id: %s does not exist", id));

        if (userResult.hasNoValue()) {
            return userResult;
        }

        User user = userResult.getValue();

        if (user.isAgency()) {
            return userResult;
        }

        if (loggedUser.getId().equals(id) || loggedUser.isAdmin()) {
            return userResult;
        }

        return Result.failure(ForbiddenException.instance());
    }

    public Result<User> create(User user) {
        Result<User> userResult = UserValidator.validate(user);

        if (userResult.hasNoValue()) {
            return userResult;
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Result.failure(ValidationException.create("Username already exists"));
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Result.failure(ValidationException.create("Email already exists"));
        }

        user.setBookedTours(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Result.success(userRepository.save(user));
    }

    public Result<User> update(String id, User user, User loggedUser) {
        if (!isUserEligibleToUpdate(id, loggedUser)) {
            return Result.failure(ForbiddenException.instance());
        }

        Result<User> userResult = UserValidator.validate(user);

        if (userResult.hasNoValue()) {
            return userResult;
        }

        Optional<User> userFoundResult = userRepository.findById(id);

        if (userFoundResult.isEmpty()) {
            return Result.failure(ValidationException.create(String.format("User with id %s does not exist", id)));
        }

        User dbUser = userFoundResult.get();
        dbUser.setUsername(user.getUsername());
        dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        dbUser.setEmail(user.getEmail());
        dbUser.setPhoneNumber(user.getPhoneNumber());

        return Result.success(userRepository.save(dbUser));
    }

    public Result<String> delete(String id, User loggedUser) {
        if (!isUserEligibleToUpdate(id, loggedUser)) {
            return Result.failure(ForbiddenException.instance());
        }

        try {
            userRepository.deleteById(id);
            return Result.success("");
        } catch (RuntimeException exception) {
            return Result.failure(ValidationException.create(String.format("User with id %s does not exist", id)));
        }
    }

    private boolean isUserEligibleToUpdate(String id, User loggedUser) {
        return loggedUser.getId().equals(id) || loggedUser.isAdmin();
    }
}
