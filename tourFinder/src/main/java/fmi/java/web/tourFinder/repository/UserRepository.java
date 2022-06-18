package fmi.java.web.tourFinder.repository;

import fmi.java.web.tourFinder.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
