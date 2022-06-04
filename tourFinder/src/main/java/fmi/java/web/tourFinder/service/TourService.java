package fmi.java.web.tourFinder.service;

import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.repository.TourRepository;
import fmi.java.web.tourFinder.validator.TourValidator;
import fmi.java.web.tourFinder.validator.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    public Iterable<Tour> getAll() {
        return tourRepository.findAll();
    }

    public Tour getById(String id) {
        return tourRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tour not found"));
    }

    public Validation<List<String>, Tour> create(Tour tour) {
        var validation = TourValidator.validate(tour);
        if (!validation.isValid()) {
            return validation;
        }
        return Validation.valid(tourRepository.save(tour));
    }

    public Validation<List<String>, Tour> update(String id, Tour tour) {
        var validation = TourValidator.validate(tour);
        if (!validation.isValid()) {
            return validation;
        }
        return Validation.valid(tourRepository.save(tour));
    }

    public void delete(String id) {
        tourRepository.deleteById(id);
    }
}
