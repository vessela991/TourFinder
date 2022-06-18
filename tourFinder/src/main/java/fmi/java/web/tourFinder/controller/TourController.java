package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.businessLogic.service.JwtService;
import fmi.java.web.tourFinder.businessLogic.service.TourPictureService;
import fmi.java.web.tourFinder.businessLogic.service.TourService;
import fmi.java.web.tourFinder.businessLogic.service.UserService;
import fmi.java.web.tourFinder.internal.util.Constants;
import fmi.java.web.tourFinder.internal.util.Routes;
import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.model.TourPicture;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.model.request.TourCreateRequest;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.TOURS)
public class TourController {
    @Autowired
    private TourService tourService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourPictureService tourPictureService;

    @GetMapping()
    public ResponseEntity<Iterable<Tour>> getAll() {
        return new ResponseEntity<>(tourService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getById(@PathVariable("id") String id) {
        return tourService.findById(id).toResponseEntity();
    }

    @GetMapping("/{id}/pictures")
    public ResponseEntity<Iterable<TourPicture>> getTourPictures(@PathVariable("id") String id) {
        return new ResponseEntity<>(tourPictureService.findAllByTourId(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Tour> create(@ModelAttribute TourCreateRequest tourCreateRequest, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return tourService.create(tourCreateRequest, loggedUser).toResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> update(@PathVariable("id") String id, @ModelAttribute TourCreateRequest tourCreateRequest, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return tourService.update(id, tourCreateRequest, loggedUser).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id, @RequestAttribute(Constants.LOGGED_USER) User loggedUser) {
        return tourService.delete(id, loggedUser).toResponseEntity(HttpStatus.NO_CONTENT);
    }
}
