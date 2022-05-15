package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") String id) {
        var test = userService.getById(id);
        if (test != null) {
            return new ResponseEntity<>(test, HttpStatus.OK);
        }
        return new ResponseEntity<>(String.format("Could not find entity with id %s", id), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user) {
        var test = userService.create(user);
        if (test.getValue() != null) {
            return new ResponseEntity<>(test, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(test, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody User user, @RequestParam("id") String id) {

        return new ResponseEntity<>("fdscs", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        userService.delete(id);
        return new ResponseEntity("", HttpStatus.NO_CONTENT);
    }
}
