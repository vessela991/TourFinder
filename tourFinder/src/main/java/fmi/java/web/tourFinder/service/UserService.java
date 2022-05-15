package fmi.java.web.tourFinder.service;

import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import fmi.java.web.tourFinder.validator.UserValidator;
import fmi.java.web.tourFinder.validator.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Validation<List<String>, User> create(User user) {
        var validation = UserValidator.validate(user);
        if (validation.isValid()) {

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
            userRepository.save(user);
        }
        return validation;
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
        return userRepository.findById(id).orElse(null);
    }

    public void update(User user, String id) {
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
