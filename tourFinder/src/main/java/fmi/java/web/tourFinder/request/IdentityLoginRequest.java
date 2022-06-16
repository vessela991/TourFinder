package fmi.java.web.tourFinder.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdentityLoginRequest {
    private String username;
    private String password;
}
