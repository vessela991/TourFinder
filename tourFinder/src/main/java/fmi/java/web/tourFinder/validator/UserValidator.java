package fmi.java.web.tourFinder.validator;

import fmi.java.web.tourFinder.model.Role;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.response.UserCreateResponse;

import java.util.List;

public class UserValidator {

    public static Validation<List<String>, User> validate(User user) {
        var emailValidation = isValidEmail(user);
        var passwordValidation = isValidPassword(user);
        var phoneNumberValidation = isValidPhoneNumber(user);
        var usernameValidation = isValidUsername(user);
        var roleValidation = isRoleValid(user);

        List<String> errors = new java.util.ArrayList<>();
        if (emailValidation.getErrors() != null) {
            errors.add(emailValidation.getErrors());
        }
        if (passwordValidation.getErrors() != null) {
            errors.add(passwordValidation.getErrors());
        }
        if (phoneNumberValidation.getErrors() != null) {
            errors.add(phoneNumberValidation.getErrors());
        }
        if (usernameValidation.getErrors() != null) {
            errors.add(usernameValidation.getErrors());
        }
        if (roleValidation.getErrors() != null) {
            errors.add(roleValidation.getErrors());
        }

        return errors.isEmpty() ? Validation.valid(user) : Validation.invalid(errors);
    }

    public static Validation<String, User> isValidEmail(User user) {
        return user.getEmail() != null && user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
                ? Validation.valid(user)
                : Validation.invalid("Invalid email. Email format must be example@example.com");
    }

    public static Validation<String, User> isValidPassword(User user) {
        return user.getPassword() != null && user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,32}")
                ? Validation.valid(user)
                : Validation.invalid("Invalid password. Password must be between 8 and 32 characters long, contain at least one lowercase letter, one uppercase letter, one number and one special character");
    }

    public static Validation<String, User> isValidPhoneNumber(User user) {
        return user.getPhoneNumber() != null && user.getPhoneNumber().matches("^\\+?[0-9]{10}$")
                ? Validation.valid(user)
                : Validation.invalid("Invalid phone number. Phone number must be in format +XXXXXXXXXX");
    }

    public static Validation<String, User> isValidUsername(User user) {
        return user.getUsername() != null && user.getUsername().matches("^[a-zA-Z0-9_]{2,20}$")
                ? Validation.valid(user)
                : Validation.invalid("Invalid username. Username must be between 2 and 20 characters long and can contain only letters, numbers and underscores");
    }

    public static Validation<String, User> isRoleValid(User user) {
        return user.getRole() != null && (user.getRole().equals(Role.USER) || user.getRole().equals(Role.AGENCY))
                ? Validation.valid(user)
                : Validation.invalid("Invalid role. Accepted roles are: user and agency");
    }
}
