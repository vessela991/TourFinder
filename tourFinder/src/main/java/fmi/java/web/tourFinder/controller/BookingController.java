package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.businessLogic.service.BookingService;
import fmi.java.web.tourFinder.businessLogic.service.JwtService;
import fmi.java.web.tourFinder.internal.util.Constants;
import fmi.java.web.tourFinder.internal.util.Routes;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.BOOKING)
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}")
    public ResponseEntity<User> book(@PathVariable("id") String id, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return bookingService.book(id, loggedUser).toResponseEntity();
    }
}
