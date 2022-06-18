package fmi.java.web.tourFinder.businessLogic.service;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourService tourService;

    public Result<User> book(String id, User loggedUser) {
        Result<Tour> tour = tourService.findById(id);

        if (tour.hasNoValue()) {
            return Result.failure(tour.getException());
        }

        if (loggedUser.getBookedTours().contains(id)) {
            return Result.success(loggedUser);
        }

        loggedUser.getBookedTours().add(id);
        return Result.success(userRepository.save(loggedUser));
    }
}
