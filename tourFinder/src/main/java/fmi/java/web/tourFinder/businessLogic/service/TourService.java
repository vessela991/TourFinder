package fmi.java.web.tourFinder.businessLogic.service;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ForbiddenException;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.businessLogic.validator.TourValidator;
import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.model.User;
import fmi.java.web.tourFinder.model.request.TourCreateRequest;
import fmi.java.web.tourFinder.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourPictureService tourPictureService;

    public Iterable<Tour> getAll() {
        return tourRepository.findAll();
    }

    public Result<Tour> findById(String id) {
        return Result.from(tourRepository.findById(id), () -> String.format("Tour with id: %s does not exist", id));
    }

    @Transactional
    public Result<Tour> create(TourCreateRequest tourCreateRequest, User loggedUser) {
        Result<TourCreateRequest> tourCreateRequestResult = TourValidator.validate(tourCreateRequest);

        if (tourCreateRequestResult.hasNoValue()) {
            return Result.failure(tourCreateRequestResult.getException());
        }

        Tour tour = new Tour();
        return saveTour(tourCreateRequest, loggedUser, tour);
    }

    @Transactional
    public Result<Tour> update(String id, TourCreateRequest tourCreateRequest, User loggedUser) {
        Result<TourCreateRequest> tourCreateRequestResult = TourValidator.validate(tourCreateRequest);

        if (tourCreateRequestResult.hasNoValue()) {
            return Result.failure(tourCreateRequestResult.getException());
        }

        Result<Tour> tourResult = findById(id);

        if (tourResult.hasNoValue()) {
            return tourResult;
        }

        Tour tour = tourResult.getValue();

        if (!isAgencyEligibleToUpdateTour(tour, loggedUser))
        {
            return Result.failure(ForbiddenException.instance());
        }

        return saveTour(tourCreateRequest, loggedUser, tour);
    }

    @Transactional
    public Result<String> delete(String id, User loggedUser) {
        Result<Tour> tourResult = findById(id);

        if (tourResult.hasNoValue()) {
            return Result.failure(ValidationException.create(tourResult.getException().getMessage()));
        }

        Tour tour = tourResult.getValue();

        if (!isAgencyEligibleToUpdateTour(tour, loggedUser))
        {
            return Result.failure(ForbiddenException.instance());
        }

        tourPictureService.deleteAllByTourId(tour.getId());
        tourRepository.delete(tour);
        return Result.success("");
    }

    private boolean isAgencyEligibleToUpdateTour(Tour tour, User loggedUser) {
        return tour.getAgencyName().equals(loggedUser.getUsername()) || loggedUser.isAdmin();
    }

    private Result<Tour> saveTour(TourCreateRequest tourCreateRequest, User loggedUser, Tour tour) {
        tour.setName(tourCreateRequest.getName());
        tour.setDestination(tourCreateRequest.getDestination());
        tour.setDescription(tourCreateRequest.getDescription());
        tour.setPrice(tourCreateRequest.getPrice());
        tour.setStartDate(tourCreateRequest.getStartDate());
        tour.setEndDate(tourCreateRequest.getEndDate());
        tour.setAgencyName(loggedUser.getUsername());

        var savedTour = tourRepository.save(tour);

        for (MultipartFile picture : tourCreateRequest.getPictures()) {
            var tourPictureResult = tourPictureService.save(picture, savedTour);
            if (tourPictureResult.hasNoValue()) {
                return Result.failure(tourPictureResult.getException());
            }
        }

        return Result.success(savedTour);
    }
}
