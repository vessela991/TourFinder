package fmi.java.web.tourFinder.internal;

import fmi.java.web.tourFinder.model.Role;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ApplicationInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        Optional<User> admin = userRepository.findByUsername("Admin");

        if (admin.isPresent()) {
            return;
        }

        User user = new User();
        user.setUsername("Admin");
        user.setPassword(passwordEncoder.encode("Admin1234!"));
        user.setEmail("admin@gmail.com");
        user.setPhoneNumber("0000000000");
        user.setRole(Role.ADMIN);
        user.setBookedTours(new ArrayList<>());
        userRepository.save(user);
    }
}
