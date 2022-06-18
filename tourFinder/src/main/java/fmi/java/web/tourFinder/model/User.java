package fmi.java.web.tourFinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private Role role;
    private ArrayList<String> bookedTours;

    @JsonIgnore
    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    @JsonIgnore
    public boolean isAgency() {
        return role.equals(Role.AGENCY);
    }

    @JsonIgnore
    public boolean isUser() {
        return role.equals(Role.USER);
    }
}
