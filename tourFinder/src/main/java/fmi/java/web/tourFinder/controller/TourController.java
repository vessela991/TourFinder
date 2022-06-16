package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.request.TourCreateRequest;
import fmi.java.web.tourFinder.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @GetMapping()
    public Iterable<Tour> getAll() {
        return tourService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getById(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(tourService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Could not find entity with id %s", id));
        }
    }

    @PostMapping()
    public ResponseEntity<Tour> create(@ModelAttribute TourCreateRequest tour) {
        var createdTour = tourService.create(tour);
        if (createdTour.getValue() != null) {
            return new ResponseEntity<>(createdTour.getValue(), HttpStatus.CREATED);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join("\n", createdTour.getErrors()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> update(@PathVariable("id") String id, @RequestBody TourCreateRequest tour) {
        return new ResponseEntity<>(tourService.update(id, tour).getValue(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        try {
            tourService.delete(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Could not find entity with id %s", id));
        }
    }
}
