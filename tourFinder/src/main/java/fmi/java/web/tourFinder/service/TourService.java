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
        return tourRepository.findById(id).orElse(null);
    }

    public Validation<List<String>, Tour> create(Tour tour) {
        var validation = TourValidator.validate(tour);
        if (validation.isValid()) {
            tourRepository.save(tour);
        }
        return validation;
    }

    public Validation<List<String>, Tour> update(String id, Tour tour) {
        //TODO: Implement update
        var validation = TourValidator.validate(tour);
        return validation;
    }

    public void delete(String id) {
        tourRepository.deleteById(id);
    }
}
