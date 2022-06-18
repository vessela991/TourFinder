package fmi.java.web.tourFinder.businessLogic.validator;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.model.request.TourCreateRequest;

import java.util.List;

public class TourValidator {
    public static Result<TourCreateRequest> validate(TourCreateRequest tour) {
        List<String> errors = new java.util.ArrayList<>();

        if (!isValidName(tour)) {
            errors.add("Invalid tour name. Tour name must be between 5 and 50 characters and can contain only letters and numbers");
        }

        if (!isValidDescription(tour)) {
            errors.add("Invalid description. Description must be less than 2000 characters");
        }

        if (!isValidDestination(tour)) {
            errors.add("Invalid destination. Destination must be specified and be less than 200 characters");
        }

        if (!isValidPrice(tour)) {
            errors.add("Invalid price. Price must be greater than 0");
        }

        if (!isValidStartDate(tour)) {
            errors.add("Invalid start date. Start date must be before end date");
        }

        if (!isValidEndDate(tour)) {
            errors.add("Invalid end date. End date must be after start date");
        }

        return errors.isEmpty() ? Result.success(tour) : Result.failure(ValidationException.create(errors));
    }

    private static boolean isValidName(TourCreateRequest tour) {
        return tour.getName() != null && tour.getName().matches("^[a-zA-Z0-9]{5,50}$");
    }

    private static boolean isValidDescription(TourCreateRequest tour) {
        if (tour.getDescription() != null) {
            return tour.getDescription().length() < 2000;
        }
        return true;
    }

    private static boolean isValidDestination(TourCreateRequest tour) {
        if (tour.getDestination() != null) {
            return tour.getDescription().length() < 200;
        }
        return true;
    }

    private static boolean isValidPrice(TourCreateRequest tour) {
        return tour.getPrice() > 0;
    }

    private static boolean isValidStartDate(TourCreateRequest tour) {
        return tour.getStartDate() != null && tour.getStartDate().before(tour.getEndDate());
    }

    private static boolean isValidEndDate(TourCreateRequest tour) {
        return tour.getEndDate() != null && tour.getEndDate().after(tour.getStartDate());
    }
}
