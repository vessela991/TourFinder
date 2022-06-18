package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.businessLogic.service.JwtService;
import fmi.java.web.tourFinder.businessLogic.service.UserService;
import fmi.java.web.tourFinder.internal.util.Constants;
import fmi.java.web.tourFinder.internal.util.Routes;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.USERS)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Iterable<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String id, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return userService.getById(id, loggedUser).toResponseEntity();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return userService.create(user).toResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User user, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return userService.update(id, user, loggedUser).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return userService.delete(id, loggedUser).toResponseEntity(HttpStatus.NO_CONTENT);
    }
}
