package fmi.java.web.tourFinder.validator;

import fmi.java.web.tourFinder.model.Tour;
import java.util.List;

public class TourValidator {
    public static Validation<List<String>, Tour> validate(Tour tour) {

        var nameValidation = isValidName(tour);
        var descriptionValidation = isValidDescription(tour);
        var destinationValidation = isValidDestination(tour);
        var priceValidation = isValidPrice(tour);
        var startDateValidation = isValidStartDate(tour);
        var endDateValidation = isValidEndDate(tour);

        List<String> errors = new java.util.ArrayList<>();
        if (nameValidation.getErrors() != null) {
            errors.add(nameValidation.getErrors());
        }
        if (descriptionValidation.getErrors() != null) {
            errors.add(descriptionValidation.getErrors());
        }
        if (destinationValidation.getErrors() != null) {
            errors.add(destinationValidation.getErrors());
        }
        if (priceValidation.getErrors() != null) {
            errors.add(priceValidation.getErrors());
        }
        if (startDateValidation.getErrors() != null) {
            errors.add(startDateValidation.getErrors());
        }
        if (endDateValidation.getErrors() != null) {
            errors.add(endDateValidation.getErrors());
        }

        return errors.isEmpty() ? Validation.valid(tour) : Validation.invalid(errors);
    }

    public static Validation<String, Tour> isValidName(Tour tour) {
        return tour.getName() != null && tour.getName().matches("^[a-zA-Z0-9]{5,50}$")
                ? Validation.valid(tour)
                : Validation.invalid("Invalid tour name. Tour name must be between 5 and 50 characters and can contain only letters and numbers");
    }

    public static Validation<String, Tour> isValidDescription(Tour tour) {
        if (tour.getDescription() != null) {
            return tour.getDescription().length() < 2000
                    ? Validation.valid(tour)
                    : Validation.invalid("Invalid description. Description must be less than 2000 characters");
        }
        return Validation.valid(tour);
    }

    public static Validation<String, Tour> isValidDestination(Tour tour) {
        return tour.getDestination() != null
                ? Validation.valid(tour)
                : Validation.invalid("Invalid destination. Destination must be specified");
    }

    public static Validation<String, Tour> isValidPrice(Tour tour) {
        return tour.getPrice() > 0
                ? Validation.valid(tour)
                : Validation.invalid("Invalid price. Price must be greater than 0");
    }

    public static Validation<String, Tour> isValidStartDate(Tour tour) {
        return tour.getStartDate() != null && tour.getStartDate().before(tour.getEndDate())
                ? Validation.valid(tour)
                : Validation.invalid("Invalid start date. Start date must be before end date");
    }

    public static Validation<String, Tour> isValidEndDate(Tour tour) {
        return tour.getEndDate() != null && tour.getEndDate().after(tour.getStartDate())
                ? Validation.valid(tour)
                : Validation.invalid("Invalid end date. End date must be after start date");
    }

}
