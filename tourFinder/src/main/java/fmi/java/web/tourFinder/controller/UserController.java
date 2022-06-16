package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.response.UserCreateResponse;
import fmi.java.web.tourFinder.service.UserService;
import fmi.java.web.tourFinder.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Iterable<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") String id) {
        try {
            var user = userService.getById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Could not find entity with id %s", id));
        }
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> create(@RequestBody User user) {
        var createdUser = userService.create(user);
        if (createdUser.getValue() != null) {
            return new ResponseEntity<>(UserCreateResponse.fromUser(createdUser.getValue()), HttpStatus.CREATED);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join("; ", createdUser.getErrors()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @RequestParam("id") String id) {
        return new ResponseEntity<>(userService.update(user, id).getValue(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        try {
            userService.delete(id, loggedUser);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Could not delete entity with id %s", id));
        }
    }
}
