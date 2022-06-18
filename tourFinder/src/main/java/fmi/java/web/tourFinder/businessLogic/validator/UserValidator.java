package fmi.java.web.tourFinder.businessLogic.validator;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.model.Role;
import fmi.java.web.tourFinder.model.User;

import java.util.List;

public class UserValidator {
    public static Result<User> validate(User user) {
        List<String> errors = new java.util.ArrayList<>();

        if (!isValidUsername(user)) {
            errors.add("Invalid username. Username must be between 2 and 20 characters long and can contain only letters, numbers and underscores");
        }

        if (!isValidEmail(user)) {
            errors.add("Invalid email. Email format must be example@example.com");
        }

        if (!isValidPassword(user)) {
            errors.add("Invalid password. Password must be between 8 and 32 characters long, contain at least one lowercase letter, one uppercase letter, one number and one special character");
        }

        if (!isValidPhoneNumber(user)) {
            errors.add("Invalid phone number. Phone number must be in format +XXXXXXXXXX");
        }

        if (!isRoleValid(user)) {
            errors.add("Invalid role. Accepted roles are: user and agency");
        }

        return errors.isEmpty() ? Result.success(user) : Result.failure(ValidationException.create(errors));
    }

    private static boolean isValidEmail(User user) {
        return user.getEmail() != null && user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    private static boolean isValidPassword(User user) {
        return user.getPassword() != null && user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,32}");
    }

    private static boolean isValidPhoneNumber(User user) {
        return user.getPhoneNumber() != null && user.getPhoneNumber().matches("^\\+?[0-9]{10}$");
    }

    private static boolean isValidUsername(User user) {
        return user.getUsername() != null && user.getUsername().matches("^[a-zA-Z0-9_]{2,20}$");
    }

    private static boolean isRoleValid(User user) {
        return user.getRole() != null && !user.isAdmin();
    }
}
