package fmi.java.web.tourFinder.repository;

import fmi.java.web.tourFinder.model.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<Tour,String> {
}
