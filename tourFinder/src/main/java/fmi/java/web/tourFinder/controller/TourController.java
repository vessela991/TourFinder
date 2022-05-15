package fmi.java.web.tourFinder.controller;

import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @GetMapping()
    public Iterable<Tour> getAll() {
        return tourService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") String id) {
        var tour = tourService.getById(id);
        if (tour != null) {
            return new ResponseEntity(tour, HttpStatus.OK);
        }
        return new ResponseEntity<>(String.format("Could not find entity with id %s", id), HttpStatus.BAD_REQUEST);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody Tour tour) {
        var test = tourService.create(tour);
        if (test.getValue() != null) {
            return new ResponseEntity<>(test, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(test, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody Tour tour) {
        return new ResponseEntity(tourService.update(id, tour), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        tourService.delete(id);
        return new ResponseEntity("", HttpStatus.NO_CONTENT);
    }
}
