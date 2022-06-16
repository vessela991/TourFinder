package fmi.java.web.tourFinder.response;

import fmi.java.web.tourFinder.model.Role;
import fmi.java.web.tourFinder.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateResponse {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;

    public static UserCreateResponse fromUser(User user) {
        UserCreateResponse userResponse = new UserCreateResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());
        return userResponse;
    }
}
