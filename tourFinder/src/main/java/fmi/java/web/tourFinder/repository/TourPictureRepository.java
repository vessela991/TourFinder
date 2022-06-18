package fmi.java.web.tourFinder.repository;

import fmi.java.web.tourFinder.model.TourPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourPictureRepository extends CrudRepository<TourPicture,String> {
    Iterable<TourPicture> findAllByTourId(String id);
    void deleteAllByTourId(String id);
}