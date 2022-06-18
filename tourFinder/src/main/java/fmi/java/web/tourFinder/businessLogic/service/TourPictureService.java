package fmi.java.web.tourFinder.businessLogic.service;

import fmi.java.web.tourFinder.businessLogic.Result;
import fmi.java.web.tourFinder.businessLogic.exception.ValidationException;
import fmi.java.web.tourFinder.model.Tour;
import fmi.java.web.tourFinder.model.TourPicture;
import fmi.java.web.tourFinder.repository.TourPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class TourPictureService {

    @Autowired
    private TourPictureRepository tourPictureRepository;

    public Iterable<TourPicture> findAllByTourId(String tourId) {
        return tourPictureRepository.findAllByTourId(tourId);
    }

    public void deleteAllByTourId(String tourId) {
        tourPictureRepository.deleteAllByTourId(tourId);
    }

    public Result<TourPicture> save(MultipartFile picture, Tour tour) {
        try {
            if (tour.getId() == null) {
                return Result.failure(ValidationException.create("Tour need to have id"));
            }

            var tourPicture = new TourPicture();
            tourPicture.setPicture(convertToBase64(picture));
            tourPicture.setTourId(tour.getId());
            return Result.success(tourPictureRepository.save(tourPicture));
        } catch (RuntimeException | IOException exception) {
            return Result.failure(ValidationException.create("Cannot process image, please select another one"));
        }
    }

    private String convertToBase64(MultipartFile picture) throws IOException {
        return Base64.getEncoder().encodeToString(picture.getBytes());
    }
}
