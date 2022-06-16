package fmi.java.web.tourFinder.service;

import fmi.java.web.tourFinder.exception.UnauthorizedException;
import fmi.java.web.tourFinder.model.Role;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import fmi.java.web.tourFinder.response.UserCreateResponse;
import fmi.java.web.tourFinder.validator.UserValidator;
import fmi.java.web.tourFinder.validator.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Validation<List<String>, User> create(User user) {
        var validation = UserValidator.validate(user);
        if (!validation.isValid()) {
            return validation;
        }
        var existingUsername = checkForExistingUsername(user);

        var existingEmail = checkForExistingEmail(user);

        if (!existingUsername.isValid() || !existingEmail.isValid()) {
            var errors = new ArrayList<String>();

            if (existingUsername.getErrors() != null) {
                errors.add(existingUsername.getErrors());
            }
            if (existingEmail.getErrors() != null) {
                errors.add(existingEmail.getErrors());
            }
            return Validation.invalid(errors);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Validation.valid(userRepository.save(user));
    }

    private Validation<String, User> checkForExistingUsername(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Validation.invalid("Username already exists");
        }
        return Validation.valid(user);
    }

    public Validation<String, User> checkForExistingEmail(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Validation.invalid("Email already exists");
        }
        return Validation.valid(user);
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
    }

    public Validation<List<String>, User> update(User user, String id) {
        var validation = UserValidator.validate(user);
        if (!validation.isValid()) {
            return validation;
        }

        var existingUsername = checkForExistingUsername(user);

        var existingEmail = checkForExistingEmail(user);

        if (!existingUsername.isValid() || !existingEmail.isValid()) {
            var errors = new ArrayList<String>();

            if (existingUsername.getErrors() != null) {
                errors.add(existingUsername.getErrors());
            }
            if (existingEmail.getErrors() != null) {
                errors.add(existingEmail.getErrors());
            }
            return Validation.invalid(errors);
        }

        if (userRepository.findById(id).isPresent()) {
            return Validation.valid(userRepository.save(user));
        } else {
            var errors = new ArrayList<String>();
            errors.add("User with id " + id + " does not exist");
            return Validation.invalid(errors);
        }
    }

    public void delete(String id, User loggedUser) {
        if (loggedUser.getId().equals(id) || loggedUser.getRole().equals(Role.ADMIN)) {
            if (userRepository.findById(id).isPresent()) {
                userRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("User with id " + id + " does not exist");
            }
        } else {
            throw new RuntimeException("You are not authorized to perform this action");
        }
    }
}
